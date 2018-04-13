package org.endeavourhealth.dataassurance.dal;

import org.endeavourhealth.dataassurance.models.UPRNDPAAddress;

import java.util.List;

public interface UPRNDPAAddressResourceDAL {

    List<UPRNDPAAddress> getCandidateAddresses(String postcode);

}
