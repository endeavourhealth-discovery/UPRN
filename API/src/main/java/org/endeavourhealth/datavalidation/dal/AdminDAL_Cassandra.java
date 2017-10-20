package org.endeavourhealth.datavalidation.dal;

import org.endeavourhealth.core.data.admin.LibraryRepository;
import org.endeavourhealth.core.data.admin.ServiceRepository;
import org.endeavourhealth.core.data.admin.models.ActiveItem;
import org.endeavourhealth.core.data.admin.models.Item;
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
            return "Not known";

        return service.getName();
    }

    @Override
    public String getSystemName(String systemId) {
        LibraryRepository libraryRepository = new LibraryRepository();
        ActiveItem activeItem = libraryRepository.getActiveItemByItemId(UUID.fromString(systemId));
        if (activeItem == null)
            return "Not known";

        Item item = libraryRepository.getItemByKey(activeItem.getItemId(), activeItem.getAuditId());
        if (item == null)
            return "Not known";

        return item.getTitle();
    }
}
