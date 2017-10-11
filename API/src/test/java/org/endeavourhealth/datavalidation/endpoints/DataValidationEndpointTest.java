package org.endeavourhealth.datavalidation.endpoints;

import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class DataValidationEndpointTest {
    @Test
    public void get() throws Exception {
        Response response = new DataValidationEndpoint().get(null, null, null, null);
        Assert.assertEquals("OK", response.getEntity());
    }

}