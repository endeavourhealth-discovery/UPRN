package org.endeavourhealth.datavalidation.logic;

import org.endeavourhealth.core.database.dal.DalProvider;
import org.endeavourhealth.core.database.dal.admin.LibraryDalI;
import org.endeavourhealth.core.database.dal.admin.models.ActiveItem;
import org.endeavourhealth.core.database.dal.admin.models.DefinitionItemType;
import org.endeavourhealth.core.database.dal.admin.models.Item;
import org.endeavourhealth.core.database.dal.admin.models.ItemDependency;
import org.endeavourhealth.datavalidation.endpoints.FolderEndpoint;
import org.endeavourhealth.datavalidation.models.DependencyType;
import org.endeavourhealth.datavalidation.models.Folder;
import org.endeavourhealth.datavalidation.models.FolderList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class FolderLogic{
    private static final Logger LOG = LoggerFactory.getLogger(FolderEndpoint.class);

    public FolderList getFolderList(int folderType, String parentUuidStr, UUID orgUuid) throws Exception {
        //convert the nominal folder type to the actual Item DefinitionType
        DefinitionItemType itemType = null;
        if (folderType == Folder.FOLDER_TYPE_LIBRARY) {
            itemType = DefinitionItemType.LibraryFolder;
        } else {
            throw new BadRequestException("Invalid folder type " + folderType);
        }

        LOG.trace("GettingFolders under parent UUID {} and folderType {}, which is itemType {}", parentUuidStr, folderType, itemType);

        Iterable<ActiveItem> activeItems = null;
        List<Item> items = new ArrayList();
        Iterable<ItemDependency> itemDependency = null;

        LibraryDalI repository = DalProvider.factoryLibraryDal();

        //if we have no parent, then we're looking for the TOP-LEVEL folder
        if (parentUuidStr == null) {
            activeItems = repository.getActiveItemByOrgAndTypeId(orgUuid, itemType.getValue(), false);

            for (ActiveItem activeItem: activeItems) {
                itemDependency = repository.getItemDependencyByItemId(activeItem.getItemId());

                if (!itemDependency.iterator().hasNext()) {
                    Item item = repository.getItemByKey(activeItem.getItemId(), activeItem.getAuditId());
                    if (!item.isDeleted()) {
                        items.add(item);
                    }
                }
            }
        }
        //if we have a parent, then we want the child folders under it
        else {
            UUID parentUuid = parseUuidFromStr(parentUuidStr);

            itemDependency = repository.getItemDependencyByDependentItemId(parentUuid, DependencyType.IsChildOf.getValue());

            for (ItemDependency dependency: itemDependency) {
                Iterable<ActiveItem> aItem = repository.getActiveItemByAuditId(dependency.getAuditId());
                for (ActiveItem activeItem: aItem) {
                    Item item = repository.getItemByKey(activeItem.getItemId(), activeItem.getAuditId());
                    items.add(item);
                }
            }
        }

        LOG.trace("Found {} child folders", items.size());

        FolderList ret = new FolderList();

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            UUID itemUuid = item.getId();

            int childFolders = 1;//ActiveItem.retrieveCountDependencies(itemUuid, DependencyType.IsChildOf);
            int contentCount = 1;//ActiveItem.retrieveCountDependencies(itemUuid, DependencyType.IsContainedWithin);

            Folder folder = new Folder(item, contentCount, childFolders > 0);
            ret.add(folder);
        }

        Collections.sort(ret.getFolders());
        return ret;
    }

    private UUID parseUuidFromStr(String uuidStr) {
        if (uuidStr == null || uuidStr.isEmpty()) {
            return null;
        } else {
            return UUID.fromString(uuidStr);
        }
    }
}
