/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.engine;

import java.util.EnumSet;

/**
 * TODO is this a sensible name?
 * TODO should this be extensible rather than an enum so users can add their own? probably
 * or should users just be able to pass in decorators that the engine can compose with its own?
 */
public enum FunctionService {

  /**
   * The caching service.
   */
  CACHING,
  /**
   * The metrics service.
   */
  METRICS,
  /**
   * The tracing service providing timings of all methods together with
   * the arguments passed and the return values. Exactly what is
   * captured is determined by the CycleArguments.
   */
  TRACING;

  /**
   * Default services provided by the engine - memoization based caching of calculated values.
   */
  public static final EnumSet<FunctionService> DEFAULT_SERVICES = EnumSet.of(CACHING);

  /**
   * Tells the engine to build the functions with no services.
   * Useful for debugging as there are no proxies between functions and every method call is direct.
   */
  public static final EnumSet<FunctionService> NONE = EnumSet.noneOf(FunctionService.class);

}
