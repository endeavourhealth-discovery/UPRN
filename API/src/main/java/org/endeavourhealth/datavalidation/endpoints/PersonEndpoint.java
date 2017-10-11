package org.endeavourhealth.datavalidation.endpoints;
import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.endeavourhealth.datavalidation.models.Patient;
import org.endeavourhealth.datavalidation.models.Person;
import org.endeavourhealth.datavalidation.models.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/person")
@Metrics(registry = "dataValidationMetricRegistry")
@Api(description = "Api for all calls relating to persons")
public class PersonEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="DataValidation.PersonEndpoint.Get")
    @Path("/")
    @ApiOperation(value = "Returns a list of matching persons")
    public Response get(@Context SecurityContext sc,
                        @ApiParam(value = "Mandatory Search terms") @QueryParam("searchTerms") String searchTerms
    ) throws Exception {
        System.out.println("Get Called");

        List<Person> matches = new ArrayList<>();

        matches.add(
            new Person()
                .setNhsNumber("123456789")
                .setName("Fred")
                .setPatientCount(3)
        );

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
        System.out.println("GetPatients Called");

        List<Patient> patients = new ArrayList<>();

        patients.add(
            new Patient()
                .setId(UUID.randomUUID())
                .setName("Fred")
                .setService(
                    new Service()
                        .setId(UUID.randomUUID())
                        .setName("Test Service")
                        .setType("GP")
                )
        );

        return Response
            .ok()
            .entity(patients)
            .build();
    }
}
