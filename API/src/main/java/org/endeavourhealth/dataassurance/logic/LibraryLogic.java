package org.endeavourhealth.dataassurance.logic;

import org.endeavourhealth.core.database.dal.DalProvider;
import org.endeavourhealth.core.database.dal.admin.LibraryDalI;
import org.endeavourhealth.core.database.dal.admin.models.*;
import org.endeavourhealth.core.database.dal.audit.UserAuditDalI;
import org.endeavourhealth.core.database.dal.audit.models.AuditModule;
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
    private static final UserAuditDalI userAudit = DalProvider.factoryUserAuditDal(AuditModule.EdsUiModule.Library);
    private static final LibraryDalI libraryRepository = DalProvider.factoryLibraryDal();

    public LibraryItem getLibraryItem(UUID libraryItemUuid) throws Exception {
        LOG.trace("GettingLibraryItem for UUID {}", libraryItemUuid);

        ActiveItem activeItem = libraryRepository.getActiveItemByItemId(libraryItemUuid);

        Item item = libraryRepository.getItemByKey(activeItem.getItemId(), activeItem.getAuditId());
        String xml = item.getXmlContent();

        return QueryDocumentSerializer.readLibraryItemFromXml(xml);
    }

    public FolderContentsList getFolderContents(UUID folderUuid) throws Exception {
        LOG.trace("GettingFolderContents for folder {}", folderUuid);

        FolderContentsList ret = new FolderContentsList();

        List<ActiveItem> childActiveItems = new ArrayList();

        Iterable<ItemDependency> itemDependency = libraryRepository.getItemDependencyByDependentItemId(folderUuid, DependencyType.IsContainedWithin.getValue());

        for (ItemDependency dependency: itemDependency) {
            Iterable<ActiveItem> item = libraryRepository.getActiveItemByAuditId(dependency.getAuditId());
            for (ActiveItem activeItem: item) {
                if (!activeItem.isDeleted()) {
                    childActiveItems.add(activeItem);
                }
            }
        }

        HashMap<UUID, Audit> hmAuditsByAuditUuid = new HashMap<>();
        List<Audit> audits = new ArrayList<>();
        for (ActiveItem activeItem: childActiveItems) {
            Audit audit = libraryRepository.getAuditByKey(activeItem.getAuditId());
            audits.add(audit);
        }

        for (Audit audit: audits) {
            hmAuditsByAuditUuid.put(audit.getId(), audit);
        }

        HashMap<UUID, Item> hmItemsByItemUuid = new HashMap<>();
        List<Item> items = new ArrayList<>();
        for (ActiveItem activeItem: childActiveItems) {
            Item item = libraryRepository.getItemByKey(activeItem.getItemId(), activeItem.getAuditId());
            items.add(item);
        }

        for (Item item: items) {
            hmItemsByItemUuid.put(item.getId(), item);
        }

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
                throw new RuntimeException("Unexpected content " + item + " in folder");
            }
        }

        if (ret.getContents() != null) {
            Collections.sort(ret.getContents());
        }
        return ret;
    }

}
