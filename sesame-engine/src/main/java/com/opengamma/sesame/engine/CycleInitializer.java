/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.engine;

import com.google.common.cache.Cache;
import com.opengamma.service.ServiceContext;
import com.opengamma.sesame.graph.Graph;
import com.opengamma.sesame.marketdata.CycleMarketDataFactory;

/**
 * Initializes a set of classes to be used when running a cycle.
 */
interface CycleInitializer {

  /**
   * Get the service context to be used for this cycle.
   *
   * @return the service context
   */
  ServiceContext getServiceContext();

  /**
   * Get the graph to be used for this cycle.
   *
   * @return the graph
   */
  Graph getGraph();

  /**
   * Indicates that the cycle has completed with the supplied
   * results and that all resources used should cleaned up.
   *
   * @param results the results of the cycle run
   * @return the complete set of results
   */
  Results complete(Results results);

  /**
   * Get the market data factory to be used for this cycle.
   *
   * @return the cycle market data factory
   */
  CycleMarketDataFactory getCycleMarketDataFactory();

  /**
   * Get the cache that should be used by functions during the cycle.
   *
   * @return the cache that should be used by functions during the cycle
   */
  Cache<Object, Object> getCache();
}
