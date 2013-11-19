/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.marketdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.LocalDate;

import com.opengamma.core.historicaltimeseries.HistoricalTimeSeries;
import com.opengamma.core.historicaltimeseries.HistoricalTimeSeriesSource;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.util.ArgumentChecker;

/**
 *
 */
public class HistoricalRawMarketDataSource implements RawMarketDataSource {

  private static final Logger s_logger = LoggerFactory.getLogger(HistoricalRawMarketDataSource.class);

  private final HistoricalTimeSeriesSource _timeSeriesSource;
  private final LocalDate _snapshotDate;
  private final String _dataSource;
  private final String _dataProvider;

  public HistoricalRawMarketDataSource(HistoricalTimeSeriesSource timeSeriesSource,
                                       LocalDate snapshotDate,
                                       String dataSource,
                                       String dataProvider) {
    _timeSeriesSource = ArgumentChecker.notNull(timeSeriesSource, "timeSeriesSource");
    _snapshotDate = ArgumentChecker.notNull(snapshotDate, "snapshotDate");
    _dataSource = ArgumentChecker.notEmpty(dataSource, "dataSource");
    _dataProvider = ArgumentChecker.notEmpty(dataProvider, "dataProvider");
  }

  @Override
  public MarketDataValue<?> get(ExternalIdBundle idBundle, String dataField) {
    HistoricalTimeSeries hts = _timeSeriesSource.getHistoricalTimeSeries(idBundle, _dataSource, _dataProvider, dataField,
                                                                         _snapshotDate, true, _snapshotDate, true);
    if (hts == null || hts.getTimeSeries().isEmpty()) {
      s_logger.info("No time-series for {}", idBundle);
      return null;
    }
    Double value = hts.getTimeSeries().getValue(_snapshotDate);
    // TODO handle null better
    if (value == null) {
      return null;
    } else {
      return new SingleMarketDataValue(value);
    }
  }
}
