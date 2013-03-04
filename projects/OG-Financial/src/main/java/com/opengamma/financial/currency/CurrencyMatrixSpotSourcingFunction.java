/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.currency;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opengamma.core.historicaltimeseries.HistoricalTimeSeries;
import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.ComputationTargetSpecification;
import com.opengamma.engine.function.FunctionCompilationContext;
import com.opengamma.engine.function.FunctionExecutionContext;
import com.opengamma.engine.function.FunctionInputs;
import com.opengamma.engine.target.ComputationTargetType;
import com.opengamma.engine.value.ComputedValue;
import com.opengamma.engine.value.ValueProperties;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueRequirementNames;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.financial.currency.CurrencyMatrixValue.CurrencyMatrixCross;
import com.opengamma.financial.currency.CurrencyMatrixValue.CurrencyMatrixFixed;
import com.opengamma.financial.currency.CurrencyMatrixValue.CurrencyMatrixValueRequirement;
import com.opengamma.id.UniqueId;
import com.opengamma.util.money.Currency;
import com.opengamma.util.timeseries.DoubleTimeSeries;
import com.opengamma.util.tuple.Pair;

/**
 * Injects a value from a {@link CurrencyMatrix} into a dependency graph to satisfy the currency requirements generated by {@link CurrencyConversionFunction}.
 */
public class CurrencyMatrixSpotSourcingFunction extends AbstractCurrencyMatrixSourcingFunction {

  private static final Logger s_logger = LoggerFactory.getLogger(CurrencyMatrixSpotSourcingFunction.class);

  public CurrencyMatrixSpotSourcingFunction(final String currencyMatrixName) {
    super(currencyMatrixName);
  }

  public CurrencyMatrixSpotSourcingFunction(final String[] params) {
    super(params);
  }

  @Override
  public Set<ValueSpecification> getResults(final FunctionCompilationContext context, final ComputationTarget target) {
    final ComputationTargetSpecification targetSpec = target.toSpecification();
    final ValueProperties properties = createValueProperties().get();
    final Set<ValueSpecification> results = new HashSet<ValueSpecification>();
    results.add(new ValueSpecification(ValueRequirementNames.SPOT_RATE, targetSpec, properties));
    return results;
  }

  private boolean getValueConversionRequirements(final Set<ValueRequirement> requirements, final Set<Pair<Currency, Currency>> visited, final Pair<Currency, Currency> currencies) {
    if (!visited.add(currencies)) {
      // Gone round in a loop if we've already seen this pair
      throw new IllegalStateException();
    }
    final CurrencyMatrixValue value = getCurrencyMatrix().getConversion(currencies.getFirst(), currencies.getSecond());
    if (value != null) {
      return value.accept(new CurrencyMatrixValueVisitor<Boolean>() {

        @Override
        public Boolean visitCross(final CurrencyMatrixCross cross) {
          return getValueConversionRequirements(requirements, visited, Pair.of(currencies.getFirst(), cross.getCrossCurrency()))
              && getValueConversionRequirements(requirements, visited, Pair.of(cross.getCrossCurrency(), currencies.getSecond()));
        }

        @Override
        public Boolean visitFixed(final CurrencyMatrixFixed fixedValue) {
          // Literal value - nothing required
          return Boolean.TRUE;
        }

        @Override
        public Boolean visitValueRequirement(final CurrencyMatrixValueRequirement valueRequirement) {
          requirements.add(valueRequirement.getValueRequirement());
          return Boolean.TRUE;
        }

      });
    } else {
      return false;
    }
  }

  @Override
  public Set<ValueRequirement> getRequirements(final FunctionCompilationContext context, final ComputationTarget target, final ValueRequirement desiredValue) {
    final Pair<Currency, Currency> currencies = parse(target.getUniqueId());
    final Set<ValueRequirement> requirements = new HashSet<ValueRequirement>();
    if (!getValueConversionRequirements(requirements, new HashSet<Pair<Currency, Currency>>(), currencies)) {
      return null;
    }
    return requirements;
  }

  private Object getValueConversionRate(final FunctionInputs inputs, final Currency source, final Currency target) {
    final CurrencyMatrixValue value = getCurrencyMatrix().getConversion(source, target);
    final Object rate = value.accept(new CurrencyMatrixValueVisitor<Object>() {

      @Override
      public Object visitCross(final CurrencyMatrixCross cross) {
        final Object r1 = getValueConversionRate(inputs, source, cross.getCrossCurrency());
        final Object r2 = getValueConversionRate(inputs, cross.getCrossCurrency(), target);
        return createCrossRate(r1, r2);
      }

      @Override
      public Object visitFixed(final CurrencyMatrixFixed fixedValue) {
        return fixedValue.getFixedValue();
      }

      @Override
      public Object visitValueRequirement(final CurrencyMatrixValueRequirement valueRequirement) {
        final Object marketValue = inputs.getValue(valueRequirement.getValueRequirement());
        if (marketValue instanceof Number) {
          double rate = ((Number) marketValue).doubleValue();
          if (valueRequirement.isReciprocal()) {
            rate = 1.0 / rate;
          }
          return rate;
        } else if (marketValue instanceof DoubleTimeSeries) {
          DoubleTimeSeries<?> rate = (DoubleTimeSeries<?>) marketValue;
          if (valueRequirement.isReciprocal()) {
            rate = rate.reciprocal();
          }
          return rate;
        } else if (marketValue instanceof HistoricalTimeSeries) {
          DoubleTimeSeries<?> rate = ((HistoricalTimeSeries) marketValue).getTimeSeries();
          if (valueRequirement.isReciprocal()) {
            rate = rate.reciprocal();
          }
          return rate;
        } else {
          throw new IllegalArgumentException(valueRequirement.toString());
        }
      }

    });
    s_logger.debug("{} to {} = {}", new Object[] {source, target, rate });
    return rate;
  }

  @Override
  public Set<ComputedValue> execute(final FunctionExecutionContext executionContext, final FunctionInputs inputs, final ComputationTarget target, final Set<ValueRequirement> desiredValues) {
    final Pair<Currency, Currency> currencies = parse(target.getUniqueId());
    final ComputationTargetSpecification targetSpec = target.toSpecification();
    final ValueRequirement desiredValue = desiredValues.iterator().next();
    return Collections.singleton(new ComputedValue(new ValueSpecification(desiredValue.getValueName(), targetSpec, desiredValue.getConstraints()),
        getValueConversionRate(inputs, currencies.getFirst(), currencies.getSecond())));
  }

  /**
   * Creates a requirement that will supply a value which gives the number of units of the source currency for each unit of the target currency.
   *
   * @param source the source currency to convert from
   * @param target the target currency to convert to
   * @return the requirement, not null
   */
  public static ValueRequirement getConversionRequirement(final Currency source, final Currency target) {
    return getConversionRequirement(source.getCode(), target.getCode());
  }

  /**
   * Creates a requirement that will supply a value which gives the number of units of the source currency for each unit of the target currency.
   *
   * @param source the source currency to convert from
   * @param target the target currency to convert to
   * @return the requirement, not null
   */
  public static ValueRequirement getConversionRequirement(final String source, final String target) {
    return new ValueRequirement(ValueRequirementNames.SPOT_RATE, ComputationTargetType.PRIMITIVE, UniqueId.of(TARGET_IDENTIFIER_SCHEME, source + target));
  }

}

