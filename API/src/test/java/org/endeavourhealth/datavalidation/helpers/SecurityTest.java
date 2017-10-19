package org.endeavourhealth.datavalidation.helpers;

import org.endeavourhealth.datavalidation.helpers.Security;
import org.endeavourhealth.datavalidation.logic.mocks.Mock_SecurityContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.SecurityContext;

import java.util.*;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class SecurityTest {
    private Security security;

    @Before
    public void setup() {
        security = new Security();
    }

    @Test
    public void getUserAllowedOrganisationIdsFromSecurityContext() throws Exception {
        List<Map<String, Object>> orgGroups = new ArrayList<>();
        Map<String, Object> orgGroup = new HashMap<>();
        orgGroup.put("organisationId", "1234567890");
        orgGroups.add(orgGroup);
        orgGroup = new HashMap<>();
        orgGroup.put("organisationId", "0987654321");
        orgGroups.add(orgGroup);

        SecurityContext securityContext = new Mock_SecurityContext(orgGroups);
        Set<String> orgs = security.getUserAllowedOrganisationIdsFromSecurityContext(securityContext);

        Assert.assertEquals(2, orgs.size());
        assertThat(orgs, hasItems("1234567890", "0987654321"));
    }

}