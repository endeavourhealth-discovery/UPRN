package org.endeavourhealth.dataassurance.logic.mocks;

import org.keycloak.KeycloakPrincipal;

public class Mock_KeycloakPrincipal extends KeycloakPrincipal {
    public Mock_KeycloakPrincipal(Mock_KeycloakSecurityContext securityContext) {
        super("mock", securityContext);
    }
}
