package org.endeavourhealth.dataassurance.logic;

import org.endeavourhealth.core.database.dal.admin.models.DefinitionItemType;
import org.endeavourhealth.core.xml.QueryDocument.LibraryItem;
import org.endeavourhealth.dataassurance.logic.mocks.Mock_LibraryDAL;
import org.endeavourhealth.dataassurance.models.FolderContentsList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class LibraryLogicTest {
    private LibraryLogic libraryLogic;
    private Mock_LibraryDAL libraryDAL;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        libraryDAL = new Mock_LibraryDAL();
        libraryLogic = new LibraryLogic(libraryDAL);
    }

    @Test
    public void getLibraryItemNullXml() throws Exception {
        thrown.expect(NullPointerException.class);
        libraryDAL.setupLibraryDALItems(2,2);
        libraryLogic.getLibraryItem(null);
        Assert.fail();
    }

    @Test
    public void getLibraryItemInvalidXml() throws Exception {
        thrown.expect(NullPointerException.class);
        libraryDAL.item.setXmlContent("INVALID_XML_STRING");
        libraryDAL.setupLibraryDALItems(2,2);
        libraryLogic.getLibraryItem(null);
        Assert.fail();
    }

    @Test
    public void getLibraryItemValidXml() throws Exception {
        libraryDAL.setupLibraryDALItems(2,2);
        libraryDAL.item.setXmlContent("<libraryItem>" +
            "<uuid>12345</uuid>" +
            "<name>mock item</name>" +
            "<folderUuid>54321</folderUuid>" +
            "<system>" +
            "   <uuid>12345</uuid>" +
            "   <name>mock system</name>" +
            "</system>" +
            "</libraryItem>");
        LibraryItem libraryItem = libraryLogic.getLibraryItem(null);
        Assert.assertNotNull(libraryItem);
    }

    @Test
    public void getFolderContentsNoDefinitionItemType() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("No DefinitionItemType -1");
        libraryDAL.setupLibraryDALItems(2, 2);
        FolderContentsList folderContentsList = libraryLogic.getFolderContents(null);
        Assert.fail();
    }

    @Test
    public void getFolderContentsUnexpectedType() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Unexpected content 'Report' in folder");
        libraryDAL.setupLibraryDALItems(1, 1);
        libraryDAL.activeItemList.get(0).setItemTypeId(DefinitionItemType.Report.getValue());
        FolderContentsList folderContentsList = libraryLogic.getFolderContents(null);
    }

    @Test
    public void getFolderContents() throws Exception {
        libraryDAL.setupLibraryDALItems(1, 1);
        libraryDAL.activeItemList.get(0).setItemTypeId(DefinitionItemType.Query.getValue());
        FolderContentsList folderContentsList = libraryLogic.getFolderContents(null);
    }
}