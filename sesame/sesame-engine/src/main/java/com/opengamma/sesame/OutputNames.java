/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame;

/**
 * Names of outputs available within the system.
 * <p>
 * These are used as map keys, thus they must be unique.
 */
public final class OutputNames {
  // In an ideal world, these would not be strings, however the Java programming
  // language offers few choices for annotation values
  // Alternative options were examined (SSM-130) but found to be worse

  /**
   * Output name when providing a description.
   */
  public static final String DESCRIPTION = "Description";
  /**
   * Output name when providing Present Value. The present value is a MultipleCurrencyAmount.
   */
  public static final String PRESENT_VALUE = "Present Value";
  /**
   * Output name when providing Present Value.
   */
  public static final String PRESENT_VALUE_CURVES = "Present Value From Curves";
  /**
   * Output name when providing Present Value.
   */
  public static final String PRESENT_VALUE_CLEAN_PRICE = "Present Value From Market Clean Price";
  /**
   * Output name when providing Present Value.
   */
  public static final String PRESENT_VALUE_YIELD = "Present Value From Market Yield";
  /**
   * Output name when providing FX Present Value.
   */
  public static final String FX_PRESENT_VALUE = "FX Present Value";
  /**
   * Output name when providing Discounting Multicurve Bundle.
   */
  public static final String DISCOUNTING_MULTICURVE_BUNDLE = "Discounting Multicurve Bundle";
  /**
   * Output name when providing Issuer Provider Bundle.
   */
  public static final String ISSUER_PROVIDER_BUNDLE = "Issuer Provider Bundle";
  /**
   * Isda credit curve output name.
   */
  public static final String ISDA_CREDIT_CURVE = "Isda Credit Curve";
  /**
   * Isda yield curve output name.
   */
  public static final String ISDA_YIELD_CURVE = "Isda Yield Curve";
  /**
   * Output name when providing P&L Series.
   */
  public static final String PNL_SERIES = "P&L Series";
  /**
   * Output name when providing YCNS P&L Series.
   */
  public static final String YCNS_PNL_SERIES = "YCNS P&L Series";
  /**
   * Output name when providing Yield Curve Node Sensitivities.
   */
  public static final String YIELD_CURVE_NODE_SENSITIVITIES = "Yield Curve Node Sensitivities";
  /**
   * Output name when providing Par Rate.
   */
  public static final String PAR_RATE = "Par Rate";
  /**
   * Output name when providing Par Spread.
   */
  public static final String PAR_SPREAD = "Par Spread";
  /**
   * The PV01 of a cash-flow based fixed-income instrument.
   */
  public static final String PV01 = "PV01";
  /**
   * The implied volatility of an option contract.
   */
  public static final String IMPLIED_VOLATILITY = "Implied Volatility";
  /**
   * The bucketed PV01
   */
  public static final String BUCKETED_PV01 = "Bucketed PV01";
  /**
   * The bucketed Gamma
   */
  public static final String BUCKETED_CROSS_GAMMA = "Bucketed Cross Gamma";
  /**
   * The bucketed sensitivity with respect to zero rates.
   */
  public static final String BUCKETED_ZERO_DELTA = "Bucketed Zero Delta";
  /**
   * Bucketed SABR risk
   */
  public static final String BUCKETED_SABR_RISK = "Bucketed SABR Risk";
  /**
   * The market price of the security.
   */
  public static final String SECURITY_MARKET_PRICE = "Security Market Price";
  /**
   * The model price of the security.
   */
  public static final String SECURITY_MODEL_PRICE = "Security Model Price";
  /**
   * The delta.
   */
  public static final String DELTA = "Delta";
  /**
   * The gamma.
   */
  public static final String GAMMA = "Gamma";
  /**
   * The vega.
   */
  public static final String VEGA = "Vega";
  /**
   * The theta.
   */
  public static final String THETA = "Theta";
  /**
   * The Market Clean Price. Returns the market quote directly without computation.
   */
  public static final String CLEAN_PRICE_MARKET = "Market Clean Price";
  /**
   * The Clean Price  computed from the issuer curves.
   */
  public static final String CLEAN_PRICE_CURVES = "Clean Price From Curve";
  /**
   * The Clean Price computed from the market yield.
   */
  public static final String CLEAN_PRICE_YIELD = "Clean Price from Market Yield";
  /**
   * The Z Spread.
   */
  public static final String Z_SPREAD = "Z Spread";
  /**
   * The Yield To Maturity computed from the market clean price.
   */
  public static final String YIELD_TO_MATURITY_CLEAN_PRICE = "Yield To Maturity From Market Clean Price";
  /**
   * The Yield To Maturity computed from the issuer curves.
   */
  public static final String YIELD_TO_MATURITY_CURVES = "Yield To Maturity From Curve";
  /**
   * The Yield To Maturity. Returns the market quote directly without computation.
   */
  public static final String YIELD_TO_MATURITY_MARKET = "Market Yield To Maturity";
  /**
   * The cash flows of the swap pay leg.
   */
  public static final String PAY_LEG_CASH_FLOWS = "Pay Leg Cash Flow Details";
  /**
   * The cash flows of the swap receive leg.
   */
  public static final String RECEIVE_LEG_CASH_FLOWS = "Receive Leg Cash Flow Details";
  /**
   * The full cash flows of the swap pay leg, including past cash flows.
   */
  public static final String FULL_PAY_LEG_CASH_FLOWS = "Full Pay Leg Cash Flow Details";
  /**
   * The full cash flows of the swap receive leg, including past cash flows.
   */
  public static final String FULL_RECEIVE_LEG_CASH_FLOWS = "Full Receive Leg Cash Flow Details";
  /**
   * The present value of the swap pay leg.
   */
  public static final String PAY_LEG_PRESENT_VALUE = "Pay Leg Present Value";
  /**
   * The present value of the swap receive leg.
   */
  public static final String RECEIVE_LEG_PRESENT_VALUE = "Receive Leg Present Value";
  /**
   *  The FX matrix 
   */
  public static final String FX_MATRIX = "FX Matrix";
  /**
   *  The FX matrix 
   */
  public static final String AVAILABLE_FX_RATES = "Available FX Rates";
  /**
   *  The CS01 of a credit default swap
   */
  public static final String CS01 = "CS01";
  /**
   *  The Bucketed CS01 of a credit default swap
   */
  public static final String BUCKETED_CS01 = "Bucketed CS01";
  /**
   * The bucketed Gamma projected on curve pillars, without cross values
   */
  public static final String BUCKETED_GAMMA = "Bucketed Gamma";
  /**
   * The foreign exchange rates
   */
  public static final String FX_RATES = "FX Rates";

  /**
   * Restricted constructor.
   */
  private OutputNames() {
  }

}
