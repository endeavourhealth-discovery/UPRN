package org.endeavourhealth.datavalidation.dal;

import org.endeavourhealth.core.data.admin.ServiceRepository;
import org.endeavourhealth.core.data.admin.models.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class AdminDAL_Cassandra implements AdminDAL {
    private static final Logger LOG = LoggerFactory.getLogger(AdminDAL_Cassandra.class);

    @Override
    public String getServiceName(String serviceId) {
        ServiceRepository serviceRepository = new ServiceRepository();
        Service service = serviceRepository.getById(UUID.fromString(serviceId));
        if (service == null)
            return null;

        return service.getName();
    }
}
