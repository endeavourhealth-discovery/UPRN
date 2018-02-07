package org.endeavourhealth.dataassurance.logic;

import org.endeavourhealth.dataassurance.logic.mocks.Mock_LibraryDAL;
import org.endeavourhealth.dataassurance.models.FolderList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.ws.rs.BadRequestException;

import java.util.UUID;

public class FolderLogicTest {
    private FolderLogic folderLogic;
    private Mock_LibraryDAL libraryDAL;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        libraryDAL = new Mock_LibraryDAL();
        folderLogic = new FolderLogic(libraryDAL);
    }

    @Test
    public void getFolderListInvalidFolderType() throws Exception {
        thrown.expect(BadRequestException.class);
        thrown.expectMessage("Invalid folder type 2");
        FolderList folderList = folderLogic.getFolderList(2, "", null);
        Assert.assertNotNull(folderList);
        Assert.assertNotNull(folderList.getFolders());
        Assert.assertEquals(1, folderList.getFolders().size());
    }

    @Test
    public void getFolderListNullParent() throws Exception {
        libraryDAL.setupLibraryDALItems(2, 0);

        FolderList folderList = folderLogic.getFolderList(1, null, null);
        Assert.assertNotNull(folderList);
        Assert.assertNotNull(folderList.getFolders());
        Assert.assertEquals(2, folderList.getFolders().size());
    }

    @Test
    public void getFolderListDeleted() throws Exception {
        libraryDAL.setupLibraryDALItems(2, 0);
        libraryDAL.item.setDeleted(true);

        FolderList folderList = folderLogic.getFolderList(1, null, null);
        Assert.assertNotNull(folderList);
        Assert.assertNotNull(folderList.getFolders());
        Assert.assertEquals(0, folderList.getFolders().size());
    }

    @Test
    public void getFolderListEmptyParent() throws Exception {
        libraryDAL.setupLibraryDALItems(2,2);

        FolderList folderList = folderLogic.getFolderList(1, "", null);
        Assert.assertNotNull(folderList);
        Assert.assertNotNull(folderList.getFolders());
        Assert.assertEquals(4, folderList.getFolders().size());
    }

    @Test
    public void getFolderListValidParent() throws Exception {
        libraryDAL.setupLibraryDALItems(2,2);

        FolderList folderList = folderLogic.getFolderList(1, UUID.randomUUID().toString(), null);
        Assert.assertNotNull(folderList);
        Assert.assertNotNull(folderList.getFolders());
        Assert.assertEquals(4, folderList.getFolders().size());
    }
}