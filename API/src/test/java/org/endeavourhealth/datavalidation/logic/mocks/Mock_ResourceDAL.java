package org.endeavourhealth.datavalidation.logic.mocks;

import org.endeavourhealth.datavalidation.dal.ResourceDAL;
import org.endeavourhealth.datavalidation.models.ResourceType;

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
}
