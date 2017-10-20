package org.endeavourhealth.datavalidation.endpoints;
import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.endeavourhealth.common.cache.ObjectMapperPool;
import org.endeavourhealth.datavalidation.logic.ResourceLogic;
import org.endeavourhealth.datavalidation.helpers.Security;
import org.endeavourhealth.datavalidation.models.ResourceRequest;
import org.endeavourhealth.datavalidation.models.ResourceType;
import org.endeavourhealth.datavalidation.models.PatientResource;
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

        List<ResourceType> resourceTypes = new ResourceLogic().getTypes();

        return Response
            .ok()
            .entity(resourceTypes)
            .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name = "DataValidation.ResourceEndpoint.Get")
    @Path("/")
    @ApiOperation(value = "Returns a list of all resources of the given types for the given service patients")
    public Response getForPatient(@Context SecurityContext sc,
                                  @ApiParam(value = "Mandatory Resource Request") String resourceRequestJson
    ) throws Exception {
        LOG.debug("getForPatient called");

        ResourceRequest resourceRequest = ObjectMapperPool.getInstance().readValue(resourceRequestJson, ResourceRequest.class);

        List<PatientResource> resources = new ResourceLogic().getPatientResources(
            new Security().getUserAllowedOrganisationIdsFromSecurityContext(sc),
            resourceRequest.getPatients(),
            resourceRequest.getResourceTypes());

        return Response
            .ok()
            .entity(resources)
            .build();
    }
}
