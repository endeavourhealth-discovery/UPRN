package org.endeavourhealth.datavalidation.logic;

import org.endeavourhealth.datavalidation.dal.AdminDAL;
import org.endeavourhealth.datavalidation.dal.AdminDAL_Cassandra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Admin {
    static AdminDAL dal = new AdminDAL_Cassandra();
    private static final Logger LOG = LoggerFactory.getLogger(Admin.class);

    public static String getServiceName(String serviceId) {
        return dal.getServiceName(serviceId);
    }
}
