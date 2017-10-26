package org.endeavourhealth.datavalidation.dal;

import org.endeavourhealth.core.database.dal.DalProvider;
import org.endeavourhealth.core.database.dal.admin.LibraryDalI;
import org.endeavourhealth.core.database.dal.admin.ServiceDalI;
import org.endeavourhealth.core.database.dal.admin.models.ActiveItem;
import org.endeavourhealth.core.database.dal.admin.models.Item;
import org.endeavourhealth.core.database.dal.admin.models.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class AdminDAL_Cassandra implements AdminDAL {
    private static final Logger LOG = LoggerFactory.getLogger(AdminDAL_Cassandra.class);

    @Override
    public String getServiceName(String serviceId) {
        ServiceDalI serviceRepository = DalProvider.factoryServiceDal();

        Service service;
        try {
            service = serviceRepository.getById(UUID.fromString(serviceId));
        } catch (Exception ex) {
            LOG.error("Failed to retrieve service " + serviceId, ex);
            return "Not known";
        }

        if (service == null) {
            return "Not known";
        }

        return service.getName();
    }

    @Override
    public String getSystemName(String systemId) {

        try {
            LibraryDalI libraryRepository = DalProvider.factoryLibraryDal();
            ActiveItem activeItem = libraryRepository.getActiveItemByItemId(UUID.fromString(systemId));
            if (activeItem == null) {
                return "Not known";
            }

            Item item = libraryRepository.getItemByKey(activeItem.getItemId(), activeItem.getAuditId());
            if (item == null) {
                return "Not known";
            }

            return item.getTitle();

        } catch (Exception ex) {
            LOG.error("Error retrieving system name", ex);
            return "Not known";
        }
    }
}
