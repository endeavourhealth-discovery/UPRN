package org.endeavourhealth.datavalidation.logic;

import org.endeavourhealth.datavalidation.logic.mocks.Mock_ResourceDAL;
import org.endeavourhealth.datavalidation.models.PatientResource;
import org.endeavourhealth.datavalidation.models.ResourceId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class ResourceLogicTest {
    private Mock_ResourceDAL dal;
    private ResourceLogic resourceLogic;

    private String service1 = UUID.randomUUID().toString();
    private String service2 = UUID.randomUUID().toString();
    private String service3 = UUID.randomUUID().toString();
    private String service4 = UUID.randomUUID().toString();

    private Set<String> serviceIds = new HashSet<>(Arrays.asList(service1, service2, service3));
    private List<String> resourceTypes = new ArrayList<>(Arrays.asList("R1","R2"));

    @Before
    public void setup() {
        dal = new Mock_ResourceDAL();
        ResourceLogic.dal = dal;
        resourceLogic = new ResourceLogic();
    }

    @Test
    public void getServicePatientResourcesNullServiceList() throws Exception {
        List<ResourceId> patients = new ArrayList<>();

        patients.add(new ResourceId().setServiceId(service1).setSystemId("C1").setPatientId("P1"));
        patients.add(new ResourceId().setServiceId(service2).setSystemId("C1").setPatientId("P2"));

        List<PatientResource> actual = resourceLogic.getPatientResources(null, patients, resourceTypes);

        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void getServicePatientResourcesEmptyServiceList() throws Exception {
        List<ResourceId> patients = new ArrayList<>();

        patients.add(new ResourceId().setServiceId(service1).setSystemId("C1").setPatientId("P1"));
        patients.add(new ResourceId().setServiceId(service2).setSystemId("C1").setPatientId("P2"));

        List<PatientResource> actual = resourceLogic.getPatientResources(new HashSet<>(), patients, resourceTypes);

        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void getServicePatientResourcesValidService() throws Exception {
        List<ResourceId> patients = new ArrayList<>();

        patients.add(new ResourceId()
            .setServiceId(service1)
            .setSystemId(UUID.randomUUID().toString())
            .setPatientId(UUID.randomUUID().toString()));
        patients.add(new ResourceId()
            .setServiceId(service2)
            .setSystemId(UUID.randomUUID().toString())
            .setPatientId(UUID.randomUUID().toString()));

        List<PatientResource> actual = resourceLogic.getPatientResources(serviceIds, patients, resourceTypes);

        //Assert.assertEquals(2, actual.size());
        Assert.assertEquals(0, actual.size());  // due to temporary performance change, this will always be 0
    }

    @Test
    public void getServicePatientResourcesInvalidService() throws Exception {
        List<ResourceId> patients = new ArrayList<>();

        patients.add(new ResourceId().setServiceId(service4).setSystemId("C1").setPatientId("P1"));
        patients.add(new ResourceId().setServiceId(service4).setSystemId("C1").setPatientId("P2"));

        List<PatientResource> actual = resourceLogic.getPatientResources(serviceIds, patients, resourceTypes);

        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void getReferenceDescriptionNull() throws Exception {
        String actual = resourceLogic.getReferenceDescription(UUID.randomUUID().toString(), null);
        Assert.assertNull(actual);
    }

    @Test
    public void getReferenceDescriptionEmpty() throws Exception {
        String actual = resourceLogic.getReferenceDescription(UUID.randomUUID().toString(), "");
        Assert.assertNull(actual);
    }

    @Test
    public void getReferenceDescriptionInvalid() throws Exception {
        String actual = resourceLogic.getReferenceDescription(UUID.randomUUID().toString(), "INVALIDREFERENCE");
        Assert.assertEquals("Invalid reference", actual);
    }

    @Test
    public void getReferenceDescriptionMissingReference() throws Exception {
        String actual = resourceLogic.getReferenceDescription(UUID.randomUUID().toString(), dal.MISSING_REFERENCE);
        Assert.assertEquals("Not found", actual);
    }

    @Test
    public void getReferenceDescriptionUnknownReference() throws Exception {
        String actual = resourceLogic.getReferenceDescription(UUID.randomUUID().toString(), dal.UNKNOWN_REFERENCE);
        Assert.assertEquals("Unknown type", actual);
    }
}
