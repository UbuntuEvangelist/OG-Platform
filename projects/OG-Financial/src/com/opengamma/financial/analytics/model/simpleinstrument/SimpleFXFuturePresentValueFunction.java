/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.model.simpleinstrument;

import java.util.Collections;
import java.util.Set;

import javax.time.calendar.Clock;
import javax.time.calendar.ZonedDateTime;

import org.apache.commons.lang.Validate;

import com.google.common.collect.Sets;
import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.analytics.financial.model.interestrate.curve.YieldAndDiscountCurve;
import com.opengamma.analytics.financial.simpleinstruments.derivative.SimpleInstrument;
import com.opengamma.analytics.financial.simpleinstruments.pricing.SimpleFXFutureDataBundle;
import com.opengamma.analytics.financial.simpleinstruments.pricing.SimpleFXFuturePresentValueCalculator;
import com.opengamma.core.config.ConfigSource;
import com.opengamma.core.id.ExternalSchemes;
import com.opengamma.core.security.Security;
import com.opengamma.core.value.MarketDataRequirementNames;
import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.ComputationTargetType;
import com.opengamma.engine.function.AbstractFunction;
import com.opengamma.engine.function.FunctionCompilationContext;
import com.opengamma.engine.function.FunctionExecutionContext;
import com.opengamma.engine.function.FunctionInputs;
import com.opengamma.engine.value.ComputedValue;
import com.opengamma.engine.value.ValueProperties;
import com.opengamma.engine.value.ValuePropertyNames;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueRequirementNames;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.financial.OpenGammaCompilationContext;
import com.opengamma.financial.OpenGammaExecutionContext;
import com.opengamma.financial.analytics.conversion.SimpleFutureConverter;
import com.opengamma.financial.analytics.ircurve.YieldCurveFunction;
import com.opengamma.financial.currency.ConfigDBCurrencyPairsSource;
import com.opengamma.financial.currency.CurrencyPair;
import com.opengamma.financial.currency.CurrencyPairs;
import com.opengamma.financial.security.future.FXFutureSecurity;
import com.opengamma.id.ExternalId;
import com.opengamma.util.money.Currency;
import com.opengamma.util.money.CurrencyAmount;

/**
 *
 */
public class SimpleFXFuturePresentValueFunction extends AbstractFunction.NonCompiledInvoker {
  private static final SimpleFutureConverter CONVERTER = new SimpleFutureConverter();
  private static final SimpleFXFuturePresentValueCalculator CALCULATOR = new SimpleFXFuturePresentValueCalculator();
  private final String _payCurveName;
  private final String _receiveCurveName;

  public SimpleFXFuturePresentValueFunction(final String payCurveName, final String receiveCurveName) {
    Validate.notNull(payCurveName, "pay curve name");
    Validate.notNull(receiveCurveName, "receive curve name");
    _payCurveName = payCurveName;
    _receiveCurveName = receiveCurveName;
  }

  @Override
  public Set<ComputedValue> execute(final FunctionExecutionContext executionContext, final FunctionInputs inputs, final ComputationTarget target, final Set<ValueRequirement> desiredValues) {
    final FXFutureSecurity security = (FXFutureSecurity) target.getSecurity();
    final Clock snapshotClock = executionContext.getValuationClock();
    final ZonedDateTime now = snapshotClock.zonedDateTime();
    final Currency payCurrency = security.getNumerator();
    final Object payCurveObject = inputs.getValue(YieldCurveFunction.getCurveRequirement(payCurrency, _payCurveName, null, null));
    if (payCurveObject == null) {
      throw new OpenGammaRuntimeException("Could not get " + _payCurveName + " curve");
    }
    final Currency receiveCurrency = security.getDenominator();
    final Object receiveCurveObject = inputs.getValue(YieldCurveFunction.getCurveRequirement(receiveCurrency, _receiveCurveName, null, null));
    if (receiveCurveObject == null) {
      throw new OpenGammaRuntimeException("Could not get " + _receiveCurveName + " curve");
    }
    final ConfigSource configSource = OpenGammaExecutionContext.getConfigSource(executionContext);
    final ConfigDBCurrencyPairsSource currencyPairsSource = new ConfigDBCurrencyPairsSource(configSource);
    final CurrencyPairs currencyPairs = currencyPairsSource.getCurrencyPairs(CurrencyPairs.DEFAULT_CURRENCY_PAIRS);
    final CurrencyPair currencyPair = currencyPairs.getCurrencyPair(payCurrency, receiveCurrency);
    final Currency currencyBase = currencyPair.getBase();
    final ExternalId underlyingIdentifier = getSpotIdentifier(security, currencyPair);
    final Object spotObject = inputs.getValue(new ValueRequirement(MarketDataRequirementNames.MARKET_VALUE, underlyingIdentifier));
    if (spotObject == null) {
      throw new OpenGammaRuntimeException("Could not get market data for " + underlyingIdentifier);
    }
    double spot = (Double) spotObject;
    if (!receiveCurrency.equals(currencyBase) && receiveCurrency.equals(security.getCurrency())) {
      spot = 1. / spot;
    }
    final YieldAndDiscountCurve payCurve = (YieldAndDiscountCurve) payCurveObject;
    final YieldAndDiscountCurve receiveCurve = (YieldAndDiscountCurve) receiveCurveObject;
    final SimpleFXFutureDataBundle data = new SimpleFXFutureDataBundle(payCurve, receiveCurve, spot);
    final SimpleInstrument instrument = security.accept(CONVERTER).toDerivative(now);
    final CurrencyAmount pv = instrument.accept(CALCULATOR, data);
    final ValueProperties properties = createValueProperties()
        .with(ValuePropertyNames.PAY_CURVE, _payCurveName)
        .with(ValuePropertyNames.RECEIVE_CURVE, _receiveCurveName)
        .with(ValuePropertyNames.CURRENCY, pv.getCurrency().getCode()).get();
    final ValueSpecification spec = new ValueSpecification(ValueRequirementNames.PRESENT_VALUE, target.toSpecification(), properties);
    return Collections.singleton(new ComputedValue(spec, pv.getAmount()));
  }

  @Override
  public ComputationTargetType getTargetType() {
    return ComputationTargetType.SECURITY;
  }

  @Override
  public boolean canApplyTo(final FunctionCompilationContext context, final ComputationTarget target) {
    if (target.getType() != ComputationTargetType.SECURITY) {
      return false;
    }
    final Security security = target.getSecurity();
    return security instanceof FXFutureSecurity;
  }

  @Override
  public Set<ValueSpecification> getResults(final FunctionCompilationContext context, final ComputationTarget target) {
    final ValueProperties properties = createValueProperties()
        .with(ValuePropertyNames.PAY_CURVE, _payCurveName)
        .with(ValuePropertyNames.RECEIVE_CURVE, _receiveCurveName)
        .with(ValuePropertyNames.CURRENCY, ((FXFutureSecurity) target.getSecurity()).getDenominator().getCode()).get();
    return Collections.singleton(new ValueSpecification(ValueRequirementNames.PRESENT_VALUE, target.toSpecification(), properties));
  }

  @Override
  public Set<ValueRequirement> getRequirements(final FunctionCompilationContext context, final ComputationTarget target, final ValueRequirement desiredValue) {
    final FXFutureSecurity future = (FXFutureSecurity) target.getSecurity();
    final ConfigSource configSource = OpenGammaCompilationContext.getConfigSource(context);
    final ConfigDBCurrencyPairsSource currencyPairsSource = new ConfigDBCurrencyPairsSource(configSource);
    final CurrencyPairs currencyPairs = currencyPairsSource.getCurrencyPairs(CurrencyPairs.DEFAULT_CURRENCY_PAIRS);
    final CurrencyPair currencyPair = currencyPairs.getCurrencyPair(future.getNumerator(), future.getDenominator());
    final ExternalId underlyingIdentifier = getSpotIdentifier(future, currencyPair);
    final ValueRequirement payYieldCurve = YieldCurveFunction.getCurveRequirement(future.getNumerator(), _payCurveName, null, null);
    final ValueRequirement receiveYieldCurve = YieldCurveFunction.getCurveRequirement(future.getDenominator(), _receiveCurveName, null, null);
    final ValueRequirement spot = new ValueRequirement(MarketDataRequirementNames.MARKET_VALUE, underlyingIdentifier);
    return Sets.newHashSet(payYieldCurve, receiveYieldCurve, spot);
  }

  private ExternalId getSpotIdentifier(final FXFutureSecurity future, final CurrencyPair currencyPair) {
    ExternalId bloombergId;
    final Currency payCurrency = future.getNumerator();
    final Currency receiveCurrency = future.getDenominator();
    if (payCurrency.equals(currencyPair.getBase())) {
      bloombergId = ExternalSchemes.bloombergTickerSecurityId(payCurrency.getCode() + receiveCurrency.getCode() + " Curncy");
    } else {
      bloombergId = ExternalSchemes.bloombergTickerSecurityId(receiveCurrency.getCode() + payCurrency.getCode() + " Curncy");
    }
    return bloombergId;
  }

}
