/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.integration.marketdata;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import com.opengamma.component.ComponentRepository;
import com.opengamma.component.tool.AbstractTool;
import com.opengamma.livedata.LiveDataClient;

/**
 * 
 */
public class PeriodicLiveDataTimeSeriesStorageTool extends AbstractTool {
  /** File name option flag */
  public static final String FILE_NAME_OPT = "f";
  /** Time series data source option flag*/
  public static final String TIME_SERIES_DATASOURCE_OPT = "s";
  /** Time series data provider option flag*/
  public static final String TIME_SERIES_DATAPROVIDER_OPT = "p";
  /** User name for Live Data entitlements. */
  public static final String USER_NAME_OPT = "u";


  /**
   * Initiates the system.
   */
  @Override 
  protected void doRun() {
    String fileName = getCommandLine().getOptionValue(FILE_NAME_OPT);
    String userName = getCommandLine().getOptionValue(USER_NAME_OPT);
    LiveDataClient ldc = ComponentRepository.getThreadLocal().getInstance(LiveDataClient.class, "main");
    PeriodicLiveDataTimeSeriesStorageServer server = new PeriodicLiveDataTimeSeriesStorageServer(
        userName,
        ldc,
        getToolContext().getHistoricalTimeSeriesMaster(),
        getCommandLine().getOptionValue(TIME_SERIES_DATASOURCE_OPT),
        getCommandLine().getOptionValue(TIME_SERIES_DATAPROVIDER_OPT));
    if (fileName != null) {
      server.setInitializationFileName(fileName);
    }
    server.start();
    
    // This is ridiculous and I'm clearly doing the wrong thing. But this is the only way
    // to keep the thing running after init.
    // I think AbstractTool isn't the right thing here.
    
    while (true) {
      try {
        Thread.sleep(10000L);
      } catch (InterruptedException ex) {
        // TODO Auto-generated catch block
        ex.printStackTrace();
      }
    }
  }

  @Override
  protected  Options createOptions(boolean contextProvided) {
    
    Options options = super.createOptions(contextProvided);
    
    Option filenameOption = new Option(
        FILE_NAME_OPT, "filename", true, "The path to the file containing data to import (CSV or ZIP)");
    filenameOption.setRequired(false);
    options.addOption(filenameOption);
    
    Option timeSeriesDataSourceOption = new Option(
        TIME_SERIES_DATASOURCE_OPT, "source", true, "The name of the time series data source");
    timeSeriesDataSourceOption.setRequired(true);
    options.addOption(timeSeriesDataSourceOption);
    
    Option timeSeriesDataProviderOption = new Option(
        TIME_SERIES_DATAPROVIDER_OPT, "provider", true, "The name of the time series data provider");
    timeSeriesDataProviderOption.setRequired(true);
    options.addOption(timeSeriesDataProviderOption);
    
    Option userNameOption = new Option(
        USER_NAME_OPT, "user", true, "The name of the user for entitlement checking");
    userNameOption.setRequired(false);
    options.addOption(userNameOption);
    
    return options;
  }
  
  /**
   * Main method to run the tool.
   * 
   * @param args  the arguments, not null
   */
  public static void main(String[] args) throws Exception { //CSIGNORE
    PeriodicLiveDataTimeSeriesStorageTool tool = new PeriodicLiveDataTimeSeriesStorageTool();
    tool.initAndRun(args);
    //System.exit(0);
  }

}
