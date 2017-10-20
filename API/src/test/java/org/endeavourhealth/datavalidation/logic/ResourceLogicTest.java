package org.endeavourhealth.datavalidation.logic;

import org.endeavourhealth.datavalidation.logic.mocks.Mock_ResourceDAL;
import org.junit.Before;
import org.junit.Test;

public class ResourceLogicTest {
    private Mock_ResourceDAL dal;
    private ResourceLogic resourceLogic;

    @Before
    public void setup() {
        dal = new Mock_ResourceDAL();
        ResourceLogic.dal = dal;
        resourceLogic = new ResourceLogic();
    }

    @Test
    public void getServicePatientResources() throws Exception {

    }
}