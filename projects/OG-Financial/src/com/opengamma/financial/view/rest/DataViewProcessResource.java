/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.view.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.opengamma.engine.view.ViewProcess;
import com.opengamma.financial.livedata.rest.LiveDataInjectorResource;
import com.opengamma.util.ArgumentChecker;

/**
 * RESTful resource for a {@link ViewProcess}.
 */
@Path("/data/viewProcessors/{viewProcessorId}/processes/{viewProcessId}")
public class DataViewProcessResource {

  private final ViewProcess _viewProcess;
  
  //CSOFF: just constants
  public static final String PATH_UNIQUE_ID = "id";
  public static final String PATH_DEFINITION_NAME = "definitionName";
  public static final String PATH_DEFINITION = "definition";
  public static final String PATH_STATE = "state";
  public static final String PATH_LIVE_DATA_OVERRIDE_INJECTOR = "liveDataOverrideInjector";
  //CSON: just constants
  
  /**
   * Creates the resource.
   * 
   * @param viewProcess  the underlying view process
   */
  public DataViewProcessResource(ViewProcess viewProcess) {
    ArgumentChecker.notNull(viewProcess, "viewProcess");
    _viewProcess = viewProcess;
  }
  
  //-------------------------------------------------------------------------
  @GET
  @Path(PATH_UNIQUE_ID)
  public Response getUniqueId() {
    return Response.ok(_viewProcess.getUniqueId()).build();
  }
  
  @GET
  @Path(PATH_DEFINITION_NAME)
  public Response getDefinitionName() {
    return Response.ok(_viewProcess.getDefinitionName()).type(MediaType.TEXT_PLAIN).build();
  }
  
  @GET
  @Path(PATH_DEFINITION)
  public Response getLatestViewDefinition() {
    return Response.ok(_viewProcess.getLatestViewDefinition()).build();
  }
  
  @GET
  @Path(PATH_STATE)
  public Response getState() {
    return Response.ok(_viewProcess.getState()).build();
  }
  
  @Path(PATH_LIVE_DATA_OVERRIDE_INJECTOR)
  public LiveDataInjectorResource getLiveDataOverrideInjector() {
    return new LiveDataInjectorResource(_viewProcess.getLiveDataOverrideInjector());
  }
  
  @DELETE
  public void shutdown() {
    _viewProcess.shutdown();
  }
  
}
