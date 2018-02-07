package org.endeavourhealth.dataassurance.logic.mocks;

import org.endeavourhealth.core.database.dal.admin.LibraryDalI;
import org.endeavourhealth.core.database.dal.admin.models.ActiveItem;
import org.endeavourhealth.core.database.dal.admin.models.Audit;
import org.endeavourhealth.core.database.dal.admin.models.Item;
import org.endeavourhealth.core.database.dal.admin.models.ItemDependency;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Mock_LibraryDAL implements LibraryDalI {
    public List<ItemDependency> itemDependencyList;
    public List<ActiveItem> activeItemList;
    public Item item;
    public ActiveItem activeItem;
    public Audit audit;
    public boolean saveCalled = false;

    @Override
    public void save(List<Object> list) throws Exception {
        saveCalled = true;
    }

    @Override
    public Item getItemByKey(UUID uuid, UUID uuid1) throws Exception {
        return item;
    }

    @Override
    public Audit getAuditByKey(UUID uuid) throws Exception {
        return audit;
    }

    @Override
    public ActiveItem getActiveItemByItemId(UUID uuid) throws Exception {
        return activeItem;
    }

    @Override
    public List<ActiveItem> getActiveItemByAuditId(UUID uuid) throws Exception {
        return activeItemList;
    }

    @Override
    public List<ActiveItem> getActiveItemByTypeId(Integer integer, Boolean aBoolean) throws Exception {
        return activeItemList;
    }

    @Override
    public List<ActiveItem> getActiveItemByOrgAndTypeId(UUID uuid, Integer integer, Boolean aBoolean) throws Exception {
        return activeItemList;
    }

    @Override
    public List<ItemDependency> getItemDependencyByItemId(UUID uuid) throws Exception {
        return itemDependencyList;
    }

    @Override
    public List<ItemDependency> getItemDependencyByTypeId(UUID uuid, UUID uuid1, Integer integer) throws Exception {
        return itemDependencyList;
    }

    @Override
    public List<ItemDependency> getItemDependencyByDependentItemId(UUID uuid, Integer integer) throws Exception {
        return itemDependencyList;
    }

    @Override
    public List<Audit> getAuditByOrgAndDateDesc(UUID uuid) throws Exception {
        return null;
    }

    public void setupLibraryDALItems(int activeCount, int dependentCount) {
        activeItemList = new ArrayList<>();
        for (int i=0; i< activeCount; i++)
            activeItemList.add(new ActiveItem());

        itemDependencyList = new ArrayList<>();
        for (int i=0; i< dependentCount; i++)
            itemDependencyList.add(new ItemDependency());

        item = new Item();
        item.setTitle("Mock item");

        activeItem = new ActiveItem();

        audit = new Audit();
    }
}
