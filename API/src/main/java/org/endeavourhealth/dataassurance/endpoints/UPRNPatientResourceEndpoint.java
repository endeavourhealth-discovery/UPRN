package org.endeavourhealth.dataassurance.endpoints;

import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.endeavourhealth.coreui.endpoints.AbstractEndpoint;
import org.endeavourhealth.dataassurance.logic.UPRNPatientResourceLogic;
import org.endeavourhealth.dataassurance.models.UPRNPatientResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/uprnpatientresource")
@Metrics(registry = "dataAssuranceMetricRegistry")
@Api(description = "Api for all calls relating to UPRN patient resources")
public final class UPRNPatientResourceEndpoint extends AbstractEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(UPRNPatientResourceEndpoint.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="DataAssurance.UPRNPatientResourceEndpoint.GetUPRNPatientResourcesAddr1")
    @Path("/getpatientsaddr1")
    @ApiOperation(value = "Returns a list of matched Patient Addresses by Address 1 filter")
    public Response getUPRNPatientResourcesAddr1(@Context SecurityContext sc,
                                     @QueryParam("filter") String filter
    ) throws Exception {
        LOG.debug("getUPRNPatientResourcesAddr1 Called");
        super.setLogbackMarkers(sc);

        List<UPRNPatientResource> patients = new UPRNPatientResourceLogic().searchByPatientAddr1(filter);

        clearLogbackMarkers();

        return Response
                .ok()
                .entity(patients)
                .build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="DataAssurance.UPRNPatientResourceEndpoint.GetUPRNPatientResourcesPostcode")
    @Path("/getpatientspostcode")
    @ApiOperation(value = "Returns a list of matched Patient Addresses by Postcode filter")
    public Response getUPRNPatientResourcesPostcode(@Context SecurityContext sc,
                                                 @QueryParam("filter") String filter
    ) throws Exception {
        LOG.debug("getUPRNPatientResourcesPostcode Called");
        super.setLogbackMarkers(sc);

        List<UPRNPatientResource> patients = new UPRNPatientResourceLogic().searchByPatientPostcode(filter);

        clearLogbackMarkers();

        return Response
                .ok()
                .entity(patients)
                .build();
    }
}
