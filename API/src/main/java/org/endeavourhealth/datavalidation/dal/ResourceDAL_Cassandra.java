package org.endeavourhealth.datavalidation.dal;

import org.endeavourhealth.datavalidation.models.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ResourceDAL_Cassandra implements ResourceDAL {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceDAL_Cassandra.class);

    @Override
    public List<ResourceType> getResourceTypes() {
        List<ResourceType> resourceTypes = new ArrayList<>();
        resourceTypes.add(new ResourceType().setId("EpisodeOfCare").setName("Episode Of Care"));
        resourceTypes.add(new ResourceType().setId("Encounter").setName("Encounter"));
        resourceTypes.add(new ResourceType().setId("Observation").setName("Observation"));
        return resourceTypes;
    }
}
