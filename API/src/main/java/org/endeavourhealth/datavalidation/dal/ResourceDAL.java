package org.endeavourhealth.datavalidation.dal;

import org.endeavourhealth.datavalidation.models.ResourceType;

import java.util.List;

public interface ResourceDAL {
    List<ResourceType> getResourceTypes();
    List<String> getPatientResources(String patientId, String serviceId, List<String> resourceTypes);
}
