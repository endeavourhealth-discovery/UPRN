package org.endeavourhealth.datavalidation.logic.mocks;

import org.endeavourhealth.datavalidation.dal.ResourceDAL;
import org.endeavourhealth.datavalidation.models.ResourceType;
import org.hl7.fhir.instance.model.Resource;

import java.util.List;

public class Mock_ResourceDAL implements ResourceDAL {
    @Override
    public List<ResourceType> getResourceTypes() {
        return null;
    }

    @Override
    public List<String> getPatientResources(String serviceId, String systemId, String patientId, List<String> resourceTypes) {
        return null;
    }

    @Override
    public Resource getResource(org.hl7.fhir.instance.model.ResourceType resourceType, String resourceId) {
        return null;
    }
}
