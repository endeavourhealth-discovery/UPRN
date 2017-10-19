package org.endeavourhealth.datavalidation.helpers;

import org.endeavourhealth.common.security.SecurityUtils;
import org.keycloak.representations.AccessToken;

import javax.ws.rs.core.SecurityContext;
import java.util.*;

public class Security {
    public Set<String> getUserAllowedOrganisationIdsFromSecurityContext(SecurityContext securityContext) {
        Set<String> orgs = new HashSet<>();

        AccessToken accessToken = SecurityUtils.getToken(securityContext);
        List<Map<String, Object>> orgGroups = (List)accessToken.getOtherClaims().getOrDefault("orgGroups", null);

        for (Object orgGroup1 : orgGroups) {
            Map<String, Object> orgGroup = (Map) orgGroup1;
            String orgGroupOrganisationId = (String) orgGroup.getOrDefault("organisationId", null);
            if (orgGroupOrganisationId != null)
                orgs.add(orgGroupOrganisationId);
        }

        return orgs;
    }
}
