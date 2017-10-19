package org.endeavourhealth.datavalidation.logic;

import org.endeavourhealth.datavalidation.dal.AdminDAL;
import org.endeavourhealth.datavalidation.dal.AdminDAL_Cassandra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminLogic {
    private AdminDAL dal;

    public AdminLogic() {
        dal = new AdminDAL_Cassandra();
    }

    AdminLogic(AdminDAL dal) {
        this.dal = dal;
    }

    private static final Logger LOG = LoggerFactory.getLogger(AdminLogic.class);

    public String getServiceName(String serviceId) {
        return dal.getServiceName(serviceId);
    }
}
