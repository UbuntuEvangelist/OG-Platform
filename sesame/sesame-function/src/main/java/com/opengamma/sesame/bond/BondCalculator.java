/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.bond;

import com.opengamma.analytics.util.amount.ReferenceAmount;
import com.opengamma.financial.analytics.model.fixedincome.BucketedCurveSensitivities;
import com.opengamma.util.money.Currency;
import com.opengamma.util.money.MultipleCurrencyAmount;
import com.opengamma.util.result.Result;
import com.opengamma.util.tuple.Pair;

/**
 * Bond calculator.
 *
 * @deprecated use {@link DiscountingBondFn} instead of {@link DefaultBondFn}, the calculator and factory
 */
@Deprecated
public interface BondCalculator {

  /**
   * Calculates the present value for the trade from the curves
   *
   * @return result containing the PV if successfully created, a failure result otherwise
   */
  Result<MultipleCurrencyAmount> calculatePresentValueFromCurves();

  /**
   * Calculates the present value for the trade from the clean price
   *
   * @return result containing the PV if successfully created, a failure result otherwise
   */
  Result<MultipleCurrencyAmount> calculatePresentValueFromClean();

  /**
   * Calculates the present value for the trade from the yield
   *
   * @return result containing the PV if successfully created, a failure result otherwise
   */
  Result<MultipleCurrencyAmount> calculatePresentValueFromYield();

  /**
   * Calculates the Market Clean Price for the trade
   *
   * @return result containing the Market Clean Price if successfully created, a failure result otherwise
   */
  Result<Double> calculateCleanPriceMarket();

  /**
   * Calculates the Clean Price from the curves for the trade
   *
   * @return result containing the Market Clean Price if successfully created, a failure result otherwise
   */
  Result<Double> calculateCleanPriceFromCurves();

  /**
   * Calculates the Clean Price from the market yield for the trade
   *
   * @return result containing the Market Clean Price if successfully created, a failure result otherwise
   */
  Result<Double> calculateCleanPriceFromYield();

  /**
   * Calculates the Yield To Maturity from the market clean price for the trade
   *
   * @return result containing the Yield To Maturity if successfully created, a failure result otherwise
   */
  Result<Double> calculateYieldToMaturityFromCleanPrice();

  /**
   * Calculates the Yield To Maturity from the curves for the trade
   *
   * @return result containing the Yield To Maturity if successfully created, a failure result otherwise
   */
  Result<Double> calculateYieldToMaturityFromCurves();

  /**
   * Calculates the Market Yield To Maturity for the trade. 
   *
   * @return result containing the Yield To Maturity if successfully created, a failure result otherwise
   */
  Result<Double> calculateYieldToMaturityMarket();

  /**
   * Calculates the bucketed PV01 for the trade
   *
   * @return result containing the bucketed PV01 if successfully created, a failure result otherwise
   */
  Result<BucketedCurveSensitivities> calculateBucketedPV01();

  /**
   * Calculates the PV01 for the trade
   *
   * @return result containing the PV01 if successfully created, a failure result otherwise
   */
  Result<ReferenceAmount<Pair<String, Currency>>> calculatePV01();

  /**
   * Calculates the Z Spread for the trade
   *
   * @return result containing the PV01 if successfully created, a failure result otherwise
   */
  Result<Double> calculateZSpread();
  
}
