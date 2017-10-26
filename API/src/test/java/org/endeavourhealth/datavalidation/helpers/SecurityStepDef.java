package org.endeavourhealth.datavalidation.helpers;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.endeavourhealth.datavalidation.logic.mocks.Mock_SecurityContext;
import org.junit.Assert;

import javax.ws.rs.core.SecurityContext;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class SecurityStepDef implements En {
    private String[] organisations;
    public SecurityStepDef() {
        Given("^A security context containing (.*)$", (String arg0) -> {
            organisations = arg0.split(",");
        });

        Then("^The user allowed organisations will be (.*)$", (String arg0) -> {
            String[] expected = arg0.split(",");

            List<Map<String, Object>> orgGroups = new ArrayList<>();
            for (String organisation : organisations) {
                Map<String, Object> orgGroup = new HashMap<>();
                orgGroup.put("organisationId", organisation);
                orgGroups.add(orgGroup);
            }
            SecurityContext securityContext = new Mock_SecurityContext(orgGroups);
            Set<String> actual = new Security().getUserAllowedOrganisationIdsFromSecurityContext(securityContext);
            assertEquals(expected.length, actual.size());
            assertThat(actual, hasItems(expected));
        });
    }
}
