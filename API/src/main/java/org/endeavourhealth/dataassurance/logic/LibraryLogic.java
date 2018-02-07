package org.endeavourhealth.dataassurance.logic;

import org.endeavourhealth.core.database.dal.DalProvider;
import org.endeavourhealth.core.database.dal.admin.LibraryDalI;
import org.endeavourhealth.core.database.dal.admin.models.*;
import org.endeavourhealth.core.xml.QueryDocument.LibraryItem;
import org.endeavourhealth.core.xml.QueryDocumentSerializer;
import org.endeavourhealth.dataassurance.models.DependencyType;
import org.endeavourhealth.dataassurance.models.FolderContent;
import org.endeavourhealth.dataassurance.models.FolderContentsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class LibraryLogic {
    private static final Logger LOG = LoggerFactory.getLogger(LibraryLogic.class);
    private static LibraryDalI _libraryDal;

    public LibraryLogic() {
        if (_libraryDal == null)
            _libraryDal = _libraryDal = DalProvider.factoryLibraryDal();
    }

    LibraryLogic(LibraryDalI libraryDal) {
        _libraryDal = libraryDal;
    }

    public LibraryItem getLibraryItem(UUID libraryItemUuid) throws Exception {
        LOG.trace("GettingLibraryItem for UUID {}", libraryItemUuid);

        ActiveItem activeItem = _libraryDal.getActiveItemByItemId(libraryItemUuid);

        Item item = _libraryDal.getItemByKey(activeItem.getItemId(), activeItem.getAuditId());
        String xml = item.getXmlContent();

        return QueryDocumentSerializer.readLibraryItemFromXml(xml);
    }

    public FolderContentsList getFolderContents(UUID folderUuid) throws Exception {
        LOG.trace("GettingFolderContents for folder {}", folderUuid);

        List<ActiveItem> childActiveItems = getChildActiveItemList(folderUuid);

        HashMap<UUID, Audit> hmAuditsByAuditUuid = getAuditsByAuditUuid(childActiveItems);

        HashMap<UUID, Item> hmItemsByItemUuid = getItemsByItemUuid(childActiveItems);

        FolderContentsList ret = BuildFolderContents(childActiveItems, hmAuditsByAuditUuid, hmItemsByItemUuid);
        return ret;
    }

    private FolderContentsList BuildFolderContents(List<ActiveItem> childActiveItems, HashMap<UUID, Audit> hmAuditsByAuditUuid, HashMap<UUID, Item> hmItemsByItemUuid) {
        FolderContentsList ret = new FolderContentsList();

        for (int i = 0; i < childActiveItems.size(); i++) {

            ActiveItem activeItem = childActiveItems.get(i);
            Item item = hmItemsByItemUuid.get(activeItem.getItemId());

            DefinitionItemType itemType = DefinitionItemType.get(activeItem.getItemTypeId());
            Audit audit = hmAuditsByAuditUuid.get(item.getAuditId());

            FolderContent c = new FolderContent(activeItem, item, audit);
            ret.addContent(c);

            //and set any extra data we need
            if (itemType == DefinitionItemType.Query) {
            } else if (itemType == DefinitionItemType.Test) {
            } else if (itemType == DefinitionItemType.Resource) {
            } else if (itemType == DefinitionItemType.CodeSet) {
            } else if (itemType == DefinitionItemType.DataSet) {
            } else if (itemType == DefinitionItemType.Protocol) {
            } else if (itemType == DefinitionItemType.System) {
            } else if (itemType == DefinitionItemType.CountReport) {
            } else {
                throw new RuntimeException("Unexpected content '" + itemType + "' in folder");
            }
        }

        if (ret.getContents() != null) {
            Collections.sort(ret.getContents());
        }

        return ret;
    }

    private HashMap<UUID, Item> getItemsByItemUuid(List<ActiveItem> childActiveItems) throws Exception {
        HashMap<UUID, Item> hmItemsByItemUuid = new HashMap<>();
        for (ActiveItem activeItem: childActiveItems) {
            Item item = _libraryDal.getItemByKey(activeItem.getItemId(), activeItem.getAuditId());
            hmItemsByItemUuid.put(item.getId(), item);
        }
        return hmItemsByItemUuid;
    }

    private HashMap<UUID, Audit> getAuditsByAuditUuid(List<ActiveItem> childActiveItems) throws Exception {
        HashMap<UUID, Audit> hmAuditsByAuditUuid = new HashMap<>();
        for (ActiveItem activeItem: childActiveItems) {
            Audit audit = _libraryDal.getAuditByKey(activeItem.getAuditId());
            hmAuditsByAuditUuid.put(audit.getId(), audit);
        }
        return hmAuditsByAuditUuid;
    }

    private List<ActiveItem> getChildActiveItemList(UUID folderUuid) throws Exception {
        List<ActiveItem> childActiveItems = new ArrayList();

        Iterable<ItemDependency> itemDependency = _libraryDal.getItemDependencyByDependentItemId(folderUuid, DependencyType.IsContainedWithin.getValue());

        for (ItemDependency dependency: itemDependency) {
            Iterable<ActiveItem> item = _libraryDal.getActiveItemByAuditId(dependency.getAuditId());
            for (ActiveItem activeItem: item) {
                if (!activeItem.isDeleted()) {
                    childActiveItems.add(activeItem);
                }
            }
        }
        return childActiveItems;
    }

}
