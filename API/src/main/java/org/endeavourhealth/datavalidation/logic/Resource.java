package org.endeavourhealth.datavalidation.logic;

import org.endeavourhealth.datavalidation.dal.ResourceDAL;
import org.endeavourhealth.datavalidation.dal.ResourceDAL_Cassandra;
import org.endeavourhealth.datavalidation.models.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Resource {
    private static final Logger LOG = LoggerFactory.getLogger(Resource.class);
    static ResourceDAL dal = new ResourceDAL_Cassandra();

    public static List<ResourceType> getTypes() {
        return dal.getResourceTypes();
    }
}
