package org.endeavourhealth.dataassurance.logic;



import org.endeavourhealth.dataassurance.dal.UPRNPatientResourceDAL;
import org.endeavourhealth.dataassurance.dal.UPRNPatientResourceDAL_Jdbc;
import org.endeavourhealth.dataassurance.dal.UPRNDPAAddressResourceDAL;
import org.endeavourhealth.dataassurance.dal.UPRNDPAAddressResourceDAL_Jdbc;
import org.endeavourhealth.dataassurance.models.UPRNPatientResource;
import org.endeavourhealth.dataassurance.models.UPRNDPAAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UPRNPatientResourceLogic {
    private static final Logger LOG = LoggerFactory.getLogger(UPRNPatientResourceLogic.class);


    // Patient Address Resources Data Access Layer
    static UPRNPatientResourceDAL dal_pr;

    // Address Base Premium Data Access Layers
    static UPRNDPAAddressResourceDAL_Jdbc dal_dpa;


    public UPRNPatientResourceLogic() {
        if (dal_pr == null)
         dal_pr = new UPRNPatientResourceDAL_Jdbc();

        if (dal_dpa == null)
            dal_dpa = new UPRNDPAAddressResourceDAL_Jdbc();

    }

    public List<UPRNPatientResource> searchByPatientAddr1(String filter) throws Exception  {

        List<UPRNPatientResource> prAddr1Matches = dal_pr.searchByPatientAddr1(filter);;

        if (prAddr1Matches != null && prAddr1Matches.size() > 0) {

            // Only used for Debugging
            // showPRResults(result);
            System.out.println("(searchByPatientAddr1) Number of Patient Resources found to match: "+prAddr1Matches.size());
        } else {

            System.out.println("(searchByPatientAddr1) No Patient Resource matches found for filter: "+filter);
        }

        // UPRN Test with the first patient match
        UPRNPatientResource patientResource = prAddr1Matches.get(0);

        String postCode = patientResource.getPost_code();
        System.out.println("Matching Post Code: " + postCode);

        List<UPRNDPAAddress> dpaAddresses = dal_dpa.getCandidateAddresses(postCode);;

        if (dpaAddresses != null && dpaAddresses.size() > 0) {

            // testDPAAddressResults(dpaAddresses);
            // displayCandidateDPAAddresses(dpaAddresses);


            // Next attempt a patient address match from the candidate ABP addresses with same post code
            for (UPRNDPAAddress uprnDPAAddress : dpaAddresses) {
                if (uprnDPAAddress.isMatched(patientResource)){
                    patientResource.setAbp_match_address(uprnDPAAddress.getDisplayableAddress());
                    patientResource.setUprn(String.valueOf(uprnDPAAddress.getUprn()));

                    // Now update the UPRN number
                    dal_pr.updatePatientUPRN(patientResource, patientResource.getUprn());

                    break;
                }
            }




            } else {

            LOG.debug("No Candidate DPA Address matches found");
        }


        return prAddr1Matches;
    }

    public List<UPRNPatientResource> searchByPatientPostcode(String filter) throws Exception {

        List<UPRNPatientResource> prPostCodeMatches = dal_pr.searchByPatientPostcode(filter);

        if (prPostCodeMatches != null && prPostCodeMatches.size() > 0) {

            // Only used for Debugging
            // showPRResults(result);
            System.out.println("(searchByPatientPostcode) (" +prPostCodeMatches.size()+ ") Patient Resources found to match filter: "+filter);
        } else {

            System.out.println("(searchByPatientPostcode) No Patient Resource matches found for filter: "+filter);
            return prPostCodeMatches;
        }


        // Retrieve the list of candidate ABP DPA addresses
        // Get candidate DPA addresses for patient search post code
        List<UPRNDPAAddress> dpaAddresses = dal_dpa.getCandidateAddresses(filter);

        if (dpaAddresses != null && dpaAddresses.size() > 0) {
            // System.out.println("Found number of candidate ABP DPA addresses: " + dpaAddresses.size());

            // testDPAAddressResults(dpaAddresses);
            // displayCandidateDPAAddresses(dpaAddresses);

            // Only used for Debugging
            // showPRResults(result);

        } else {

            LOG.debug("No Candidate DPA addresses found");
        }

        long processPatientCount=0;
        long matchPatientCount=0;

        for (UPRNPatientResource prpcm : prPostCodeMatches) {

            ++processPatientCount;

            // Next attempt a patient address match from the candidate ABP addresses with same post code
            for (UPRNDPAAddress uprnDPAAddress  : dpaAddresses) {

                if (uprnDPAAddress.isMatched(prpcm)) {

                    prpcm.setUprn(String.valueOf(uprnDPAAddress.getUprn()));
                    prpcm.setAbp_match_address(uprnDPAAddress.getDisplayableAddress());

                    // Now update the UPRN number
                    dal_pr.updatePatientUPRN(prpcm, prpcm.getUprn());

                    matchPatientCount++;

                    // Break out of inner loop
                    break;
                }
                //else {
                //    continue;
                //}
            }

            if ((processPatientCount%50) == 0) {
                System.out.println("Processed: " + String.valueOf(processPatientCount));
            }
        }

        System.out.println(" ");
        System.out.println("Processed: " + String.valueOf(processPatientCount)+", Matched: "+String.valueOf(matchPatientCount)+" Discovery Addresses");
        System.out.println("Percent addresses Matched: " + String.valueOf(matchPatientCount*100.0/processPatientCount));

        System.out.println("(searchByPatientPostcode) - COMPLETED the run for matches found for filter: "+filter);

        return prPostCodeMatches;

    }

    // Remove these methods once all working
    private void testPatientResourceResults(List<UPRNPatientResource> result) {

        System.out.println("Verify UPRN Patient resource Matches: "+String.valueOf(result.size())+" Matches");


        for (int i = 0; i < result.size(); i++) {
            UPRNPatientResource uprnPatientResource = result.get(i);

            System.out.println("Result UPRN Patient resource: "+uprnPatientResource.toString());
        }
    }

    private void testDPAAddressResults(List<UPRNDPAAddress> result) {

        System.out.println("Verify DPA Candidate Addresses list: "+String.valueOf(result.size())+" Items");


        for (int i = 0; i < result.size(); i++) {
            UPRNDPAAddress uprnDPAAddress = result.get(i);

            System.out.println("Result UPRN DPA Candidate Address: "+uprnDPAAddress.toString());
        }
    }

    private void displayCandidateDPAAddresses(List<UPRNDPAAddress> result) {

        System.out.println("Verify DPA Candidate Addresses list: "+String.valueOf(result.size())+" Items");


        for (int i = 0; i < result.size(); i++) {
            UPRNDPAAddress uprnDPAAddress = result.get(i);

            System.out.println("Candidate Address: \n"
                            +uprnDPAAddress.getDisplayableAddress()
                            +"\n");

        }
    }
}
