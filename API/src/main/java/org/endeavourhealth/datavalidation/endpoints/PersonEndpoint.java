package org.endeavourhealth.datavalidation.endpoints;
import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.endeavourhealth.datavalidation.logic.PersonPatientLogic;
import org.endeavourhealth.datavalidation.helpers.Security;
import org.endeavourhealth.datavalidation.models.Patient;
import org.endeavourhealth.datavalidation.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/person")
@Metrics(registry = "dataValidationMetricRegistry")
@Api(description = "Api for all calls relating to persons")
public class PersonEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(PersonEndpoint.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="DataValidation.PersonEndpoint.Get")
    @Path("/")
    @ApiOperation(value = "Returns a list of matching persons")
    public Response get(@Context SecurityContext sc,
                        @ApiParam(value = "Mandatory Search terms") @QueryParam("searchTerms") String searchTerms
    ) throws Exception {
        LOG.debug("Get Called");

        List<Person> matches = new PersonPatientLogic().findPersonsInOrganisations(new Security().getUserAllowedOrganisationIdsFromSecurityContext(sc), searchTerms);

        return Response
            .ok()
            .entity(matches)
            .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="DataValidation.PersonEndpoint.GetPatients")
    @Path("/patients")
    @ApiOperation(value = "Returns a list patients for a given person")
    public Response getPatients(@Context SecurityContext sc,
                        @ApiParam(value = "Mandatory Person Id") @QueryParam("personId") String personId
    ) throws Exception {
        LOG.debug("GetPatients Called");

        List<Patient> patients = new PersonPatientLogic().getPatientsForPerson(new Security().getUserAllowedOrganisationIdsFromSecurityContext(sc), personId);

        return Response
            .ok()
            .entity(patients)
            .build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="DataValidation.PersonEndpoint.GetPatient")
    @Path("/patient")
    @ApiOperation(value = "Returns a patient for given service/system/patient identifiers")
    public Response getPatients(@Context SecurityContext sc,
                                @ApiParam(value = "Mandatory Service Id") @QueryParam("serviceId") String serviceId,
                                @ApiParam(value = "Mandatory System Id") @QueryParam("systemId") String systemId,
                                @ApiParam(value = "Mandatory Patient Id") @QueryParam("patientId") String patientId
    ) throws Exception {
        LOG.debug("GetPatient Called");

        Patient patient = new PersonPatientLogic().getPatient(new Security().getUserAllowedOrganisationIdsFromSecurityContext(sc), serviceId, systemId, patientId);

        return Response
            .ok()
            .entity(patient)
            .build();
    }
}
