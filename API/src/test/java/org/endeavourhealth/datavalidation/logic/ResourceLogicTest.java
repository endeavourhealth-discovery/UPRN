package org.endeavourhealth.datavalidation.logic;

import org.endeavourhealth.datavalidation.logic.mocks.Mock_ResourceDAL;
import org.endeavourhealth.datavalidation.models.ServicePatientResource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ResourceLogicTest {
    private Mock_ResourceDAL dal;
    private ResourceLogic resourceLogic;

    @Before
    public void setup() {
        dal = new Mock_ResourceDAL();
        resourceLogic = new ResourceLogic(dal);
    }

    @Test
    public void getServicePatientResources() throws Exception {

    }
}