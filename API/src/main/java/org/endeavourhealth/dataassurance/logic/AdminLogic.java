package org.endeavourhealth.dataassurance.logic;

import org.endeavourhealth.dataassurance.dal.AdminDAL;
import org.endeavourhealth.dataassurance.dal.AdminDAL_Cassandra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminLogic {
    static AdminDAL dal;

    public AdminLogic() {
        if (dal == null)
            dal = new AdminDAL_Cassandra();
    }

    private static final Logger LOG = LoggerFactory.getLogger(AdminLogic.class);

    public String getServiceName(String serviceId) {
        return dal.getServiceName(serviceId);
    }
}
