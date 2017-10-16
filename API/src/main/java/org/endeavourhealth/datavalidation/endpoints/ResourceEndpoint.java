package org.endeavourhealth.datavalidation.endpoints;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.JsonNode;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.endeavourhealth.datavalidation.logic.Resource;
import org.endeavourhealth.datavalidation.logic.Security;
import org.endeavourhealth.datavalidation.models.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/resource")
@Metrics(registry = "dataValidationMetricRegistry")
@Api(description = "API for all calls relating to admin")
public class ResourceEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceEndpoint.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name = "DataValidation.ResourceEndpoint.Types")
    @Path("/type")
    @ApiOperation(value = "Returns a list of all resource types")
    public Response get(@Context SecurityContext sc) throws Exception {
        LOG.debug("Get Types Called");

        List<ResourceType> resourceTypes = Resource.getTypes();

        return Response
            .ok()
            .entity(resourceTypes)
            .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name = "DataValidation.ResourceEndpoint.ForPatients")
    @Path("/patient")
    @ApiOperation(value = "Returns a list of all resources of the given types for the given patients")
    public Response getForPatient(@Context SecurityContext sc,
                                  @ApiParam(value = "Mandatory Patient Id") @QueryParam("patientId") String patientId,
                                  @ApiParam(value = "Mandatory Service Id") @QueryParam("serviceId") String serviceId,
                                  @ApiParam(value = "Mandatory Resource type list") @QueryParam("resourceTypes"
                                  ) List<String> resourceTypes) throws Exception {
        LOG.debug("getForPatients called");

        List<JsonNode> resources = Resource.getPatientResources(
            Security.getUserAllowedOrganisationIdsFromSecurityContext(sc),
            patientId, serviceId, resourceTypes);

        return Response
            .ok()
            .entity(resources)
            .build();
    }
}
