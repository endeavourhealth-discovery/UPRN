package org.endeavourhealth.dataassurance.models;


import org.endeavourhealth.dataassurance.utils.UPRNUtils;

public class UPRNDPAAddress extends UPRNAddress {


    // Represents a record of ABP LPI Address information

    // Constant definitions

    // MySQL table Field Names
    public static String UPRN_DPA_ADDR_SQL_COL_UPRN = "UPRN";
    public static String UPRN_DPA_ADDR_SQL_COL_UDPRN = "UDPRN";
    public static String UPRN_DPA_ADDR_SQL_COL_ORGANISATION_NAME="ORGANISATION_NAME";
    public static String UPRN_DPA_ADDR_SQL_COL_DEPARTMENT_NAME="DEPARTMENT_NAME";
    public static String UPRN_DPA_ADDR_SQL_COL_SUB_BUILDING_NAME="SUB_BUILDING_NAME";
    public static String UPRN_DPA_ADDR_SQL_COL_BUILDING_NAME="BUILDING_NAME";
    public static String UPRN_DPA_ADDR_SQL_COL_BUILDING_NUMBER="BUILDING_NUMBER";
    public static String UPRN_DPA_ADDR_SQL_COL_DEPENDENT_THOROUGHFARE="DEPENDENT_THOROUGHFARE";
    public static String UPRN_DPA_ADDR_SQL_COL_THOROUGHFARE="THOROUGHFARE";
    public static String UPRN_DPA_ADDR_SQL_COL_DOUBLE_DEPENDENT_LOCALITY="DOUBLE_DEPENDENT_LOCALITY";
    public static String UPRN_DPA_ADDR_SQL_COL_DEPENDENT_LOCALITY="DEPENDENT_LOCALITY";
    public static String UPRN_DPA_ADDR_SQL_COL_POST_TOWN="POST_TOWN";
    public static String UPRN_DPA_ADDR_SQL_COL_POSTCODE="POSTCODE";
    public static String UPRN_DPA_ADDR_SQL_COL_POSTCODE_TYPE="POSTCODE_TYPE";
    public static String UPRN_DPA_ADDR_SQL_COL_DELIVERY_POINT_SUFFIX="DELIVERY_POINT_SUFFIX";
    public static String UPRN_DPA_ADDR_SQL_COL_WELSH_DEPENDENT_THOROUGHFARE="WELSH_DEPENDENT_THOROUGHFARE";
    public static String UPRN_DPA_ADDR_SQL_COL_WELSH_THOROUGHFARE="WELSH_THOROUGHFARE";
    public static String UPRN_DPA_ADDR_SQL_COL_WELSH_DOUBLE_DEPENDENT_LOCALITY="WELSH_DOUBLE_DEPENDENT_LOCALITY";
    public static String UPRN_DPA_ADDR_SQL_COL_WELSH_DEPENDENT_LOCALITY="WELSH_DEPENDENT_LOCALITY";
    public static String UPRN_DPA_ADDR_SQL_COL_WELSH_POST_TOWN="WELSH_POST_TOWN";
    public static String UPRN_DPA_ADDR_SQL_COL_PO_BOX_NUMBER="PO_BOX_NUMBER";

    // Model attributes

    private String organisation_name;
    private String department_name;
    private String sub_building_name;
    private String building_name;
    private String building_number;
    private String dependent_thoroughfare;
    private String thoroughfare;
    private String double_dependent_locality;
    private String dependent_locality;
    private String post_town;
    private String postcode;
    private String postcode_type;
    private String delivery_point_suffix;
    private String welsh_dependent_thoroughfare;
    private String welsh_thoroughfare;
    private String welsh_double_dependent_locality;
    private String welsh_dependent_locality;
    private String welsh_post_town;
    private String po_box_number;


    public UPRNDPAAddress()
    {
        super();

    }

    public UPRNDPAAddress(long uprn,
                          long udprn,
                          String organisation_name,
                          String department_name,
                          String sub_building_name,
                          String building_name,
                          String building_number,
                          String dependent_thoroughfare,
                          String thoroughfare,
                          String double_dependent_locality,
                          String dependent_locality,
                          String post_town,
                          String postcode,
                          String postcode_type,
                          String delivery_point_suffix,
                          String welsh_dependent_thoroughfare,
                          String welsh_thoroughfare,
                          String welsh_double_dependent_locality,
                          String welsh_dependent_locality,
                          String welsh_post_town,
                          String po_box_number) {
        super(uprn, udprn);

        this.organisation_name = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(organisation_name));
        this.department_name = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(department_name));
        this.sub_building_name = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(sub_building_name));
        this.building_name = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(building_name));
        this.building_number = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(building_number));
        this.dependent_thoroughfare = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(dependent_thoroughfare));
        this.thoroughfare = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(thoroughfare));
        this.double_dependent_locality = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(double_dependent_locality));
        this.dependent_locality = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(dependent_locality));
        this.post_town = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(post_town));
        this.postcode = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(postcode));
        this.postcode_type = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(postcode_type));
        this.delivery_point_suffix = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(delivery_point_suffix));
        this.welsh_dependent_thoroughfare = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(welsh_dependent_thoroughfare));
        this.welsh_thoroughfare = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(welsh_thoroughfare));
        this.welsh_double_dependent_locality = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(welsh_double_dependent_locality));
        this.welsh_dependent_locality = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(welsh_dependent_locality));
        this.welsh_post_town = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(welsh_post_town));
        this.po_box_number = UPRNUtils.sanitizeABPData(UPRNUtils.sanitizeGeneralData(po_box_number));

    }

    public boolean isMatched(UPRNPatientResource patientResource) {
        boolean isMatched = false;

        // System.out.println("Attempting to match: >"+patientResource.getAddress_line1()+"< >"+patientResource.getAddress_line2()+"<");
        // System.out.println("Attempting to match city: >"+patientResource.getCity()+"< >"+post_town+"<");

        // First ensure that the post codes match
        if (patientResource.getPost_code().equals(postcode)) {


            // Postcode and matches
            // System.out.println("Matched post code");

            // Try an initial property match - only for non-flats which dont have sub building names
            if (sub_building_name.isEmpty()) {
                isMatched = isMatchedNonFlat(patientResource);
            }

            if (isMatched == false) {

                // Still not matched - try to match a flat
                isMatched = isMatchedFlat(patientResource);
            }

        }


        if (isMatched == true) {
            // System.out.println("Matched: " + patientResource.getAddress_line1());
            // System.out.println("Matched: " + patientResource.toString() + " with DPA "+toString());
        } else {
            // System.out.println("Failed to match: " + patientResource.toString() + " with DPA "+toString());
        }


        return isMatched;
    }

    private boolean isMatchedNonFlat(UPRNPatientResource patientResource) {
        boolean isMatchedNonFlat = false;

        if (building_number.isEmpty()) {
            // System.out.println("Matching Building name");

            // Try building name
            if (patientResource.getAddress_line1().equals(building_name) &&
                    patientResource.getAddress_line2().equals(thoroughfare)
                    ) {
                // System.out.println("Match non-flat 1");

                isMatchedNonFlat = true;
            }

        } else {
            // System.out.println("Matching Building number");

            // Try building number
            String firstAddrLine = building_number + " " + thoroughfare;
            if (patientResource.getAddress_line1().equals(firstAddrLine)
                    || patientResource.getAddress_line2().equals(firstAddrLine) // Allowing for address 1 in wrong field
                    || patientResource.getDistrict().equals(firstAddrLine) // Allowing for address 1 in wrong field
                    || patientResource.getCity().equals(firstAddrLine) // Allowing for address 1 in wrong field
                    ) {
                // System.out.println("Match non-flat 2");

                isMatchedNonFlat = true;
            }

            if (isMatchedNonFlat == false) {
                // No match yet so try address 1 as *just* the building number without the street,
                // and address2 as the street/thoroughfare

                if (patientResource.getAddress_line1().equals(building_number) &&
                        patientResource.getAddress_line2().equals(thoroughfare)
                        ) {
                    // System.out.println("Match non-flat 3");

                    isMatchedNonFlat = true;
                } else {

                    // Cater for the fact that sometimes the Discovery street name is put in the District field
                    if (patientResource.getAddress_line1().equals(building_number) &&
                            patientResource.getDistrict().equals(thoroughfare)) {
                        // System.out.println("Match non-flat 4");

                        isMatchedNonFlat = true;
                    }

                }

            }

            if (isMatchedNonFlat == false) {
                // Apply some catch all logic

                // If there is a sub-building name then try and match it with the prefix in address line 1
                if (sub_building_name.equals(UPRNUtils.createFlatNumber(UPRNUtils.extractNumber(patientResource.getAddress_line1())))
                        && patientResource.getAddress_line2().contains(thoroughfare)) {

                    isMatchedNonFlat = true;
                }

            }

        }


//        if (isMatchedNonFlat == true) {
//            System.out.println("Matched non flat: "+patientResource.toString());
//        }

        return isMatchedNonFlat;
    }

    private boolean isMatchedFlat(UPRNPatientResource patientResource) {
        boolean isMatchedFlat = false;

        //System.out.println("Trying to match Flat for: "+patientResource.getFlatNumber()+" for "+patientResource.toString());

        if (patientResource.hasFlatIndicator()) {

            if (!sub_building_name.isEmpty()) {

                System.out.println("Looking at flat indicator: "+patientResource.getFlatIndicatorNumber());
                if (patientResource.getFlatIndicatorNumber().equals(sub_building_name)) {

                    String firstAddrLine = building_number + " " + thoroughfare;

                    if (patientResource.getAddress_line1().equals(firstAddrLine)
                      || patientResource.getAddress_line2().equals(firstAddrLine) // Allowing for address 1 in wrong field
                            || patientResource.getDistrict().equals(firstAddrLine) // Allowing for address 1 in wrong field
                                || patientResource.getCity().equals(firstAddrLine) // Allowing for address 1 in wrong field
                                ) {
                        // System.out.println("Match flat 1a");
                        isMatchedFlat = true;
                    }

                    if (isMatchedFlat == false) {
                        // Check if the building name is in the 2nd address line
                        if (patientResource.getAddress_line2().equals(building_name)) {

                            // System.out.println("Match flat 1b");
                            isMatchedFlat = true;
                        }

                    }
                }

            }
        }

        if (isMatchedFlat == false) {

            // Try another method of determining if its a flat address to match
            if (!building_name.isEmpty()) {

                String candidateFlat = patientResource.getAddress_line1();
                //System.out.println("1: >"+candidateFlat+"<");
                //System.out.println("2: >"+patientResource.getAddress_line2()+"<");
                //System.out.println("3: >"+thoroughfare+"<");

                if (candidateFlat.contains(building_name) &&
                        patientResource.getAddress_line2().equals(thoroughfare)) {

                    isMatchedFlat = matchFlatFromBuildingName(candidateFlat);
                }

                if (isMatchedFlat == false) {
                    // See if thoroughfare is in the district field instead as sometimes happens with
                    // Discovery address data

                    if (patientResource.getDistrict().equals(thoroughfare)) {
                        // Try a match with the building name in the first address line

                        if (candidateFlat.contains(building_name)) {
                            isMatchedFlat = matchFlatFromBuildingName(candidateFlat);
                        }

                        if (isMatchedFlat == false) {
                            // Try a match with the building name in the second address line
                            if (patientResource.getAddress_line2().contains(building_name)) {
                                isMatchedFlat = matchFlatFromBuildingName(candidateFlat);
                            }
                        }
                    }
                }

                if (isMatchedFlat == false) {

                    // Street may not have been specified so use flat name, building name and post code
                    if (candidateFlat.contains(building_name)) {
                        isMatchedFlat = matchFlatFromBuildingName(candidateFlat);
                    }
                }

            }
        }



//        if (isMatchedFlat == true) {
//            System.out.println("Matched flat: "+patientResource.toString());
//        }


        return isMatchedFlat;
    }

    private boolean matchFlatFromBuildingName(String candidateFlat) {
        boolean isMatchedFlat = false;

        if (UPRNUtils.containsNumber(candidateFlat)) {
            //System.out.println("a: >"+thoroughfare+"<");

            String flatNum = UPRNUtils.extractFlatNumber(candidateFlat);
            //System.out.println("b: >"+flatNum+"<");
            //System.out.println("b: >"+sub_building_name+"<");

            if (flatNum.equals(sub_building_name)) {

                //System.out.println("matchFlatFromBuildingName");
                isMatchedFlat = true;
            }
        }

        return isMatchedFlat;
    }

    public String getOrganisation_name() {
        return organisation_name;
    }

    public void setOrganisation_name(String organisation_name) {
        this.organisation_name = organisation_name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getSub_building_name() {
        return sub_building_name;
    }

    public void setSub_building_name(String sub_building_name) {
        this.sub_building_name = sub_building_name;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getBuilding_number() {
        return building_number;
    }

    public void setBuilding_number(String building_number) {
        this.building_number = building_number;
    }

    public String getDependent_thoroughfare() {
        return dependent_thoroughfare;
    }

    public void setDependent_thoroughfare(String dependent_thoroughfare) {
        this.dependent_thoroughfare = dependent_thoroughfare;
    }

    public String getThoroughfare() {
        return thoroughfare;
    }

    public void setThoroughfare(String thoroughfare) {
        this.thoroughfare = thoroughfare;
    }

    public String getDouble_dependent_locality() {
        return double_dependent_locality;
    }

    public void setDouble_dependent_locality(String double_dependent_locality) {
        this.double_dependent_locality = double_dependent_locality;
    }

    public String getDependent_locality() {
        return dependent_locality;
    }

    public void setDependent_locality(String dependent_locality) {
        this.dependent_locality = dependent_locality;
    }

    public String getPost_town() {
        return post_town;
    }

    public void setPost_town(String post_town) {
        this.post_town = post_town;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPostcode_type() {
        return postcode_type;
    }

    public void setPostcode_type(String postcode_type) {
        this.postcode_type = postcode_type;
    }

    public String getDelivery_point_suffix() {
        return delivery_point_suffix;
    }

    public void setDelivery_point_suffix(String delivery_point_suffix) {
        this.delivery_point_suffix = delivery_point_suffix;
    }

    public String getWelsh_dependent_thoroughfare() {
        return welsh_dependent_thoroughfare;
    }

    public void setWelsh_dependent_thoroughfare(String welsh_dependent_thoroughfare) {
        this.welsh_dependent_thoroughfare = welsh_dependent_thoroughfare;
    }

    public String getWelsh_thoroughfare() {
        return welsh_thoroughfare;
    }

    public void setWelsh_thoroughfare(String welsh_thoroughfare) {
        this.welsh_thoroughfare = welsh_thoroughfare;
    }

    public String getWelsh_double_dependent_locality() {
        return welsh_double_dependent_locality;
    }

    public void setWelsh_double_dependent_locality(String welsh_double_dependent_locality) {
        this.welsh_double_dependent_locality = welsh_double_dependent_locality;
    }

    public String getWelsh_dependent_locality() {
        return welsh_dependent_locality;
    }

    public void setWelsh_dependent_locality(String welsh_dependent_locality) {
        this.welsh_dependent_locality = welsh_dependent_locality;
    }

    public String getWelsh_post_town() {
        return welsh_post_town;
    }

    public void setWelsh_post_town(String welsh_post_town) {
        this.welsh_post_town = welsh_post_town;
    }

    public String getPo_box_number() {
        return po_box_number;
    }

    public void setPo_box_number(String po_box_number) {
        this.po_box_number = po_box_number;
    }

    public String getDisplayableAddress() {

        String displayableAddress =
                sub_building_name+"\n"+
                        building_name+"\n"+
                        building_number+"\n"+
                        thoroughfare+"\n"+
                        post_town+"\n"+
                        postcode+"\n\n"+
                //        "UPRN: "+String.valueOf(uprn)+"\n\n"; // Omit for now
                        "\n\n";

        return displayableAddress;
    }


    public String toString() {
        String str= super.toString() +
                "organisation_name: " + organisation_name +
                "department_name: " + department_name +
                "sub_building_name: " + sub_building_name +
                "building_name: " + building_name +
                "building_number: " + building_number +
                "dependent_thoroughfare: " + dependent_thoroughfare +
                "thoroughfare: " + thoroughfare +
                "double_dependent_locality: " + double_dependent_locality +
                "dependent_locality: " + dependent_locality +
                "post_town: " + post_town +
                "postcode: " + postcode +
                "postcode_type: " + postcode_type +
                "delivery_point_suffix: " + delivery_point_suffix +
                "welsh_dependent_thoroughfare: " + welsh_dependent_thoroughfare +
                "welsh_thoroughfare: " + welsh_thoroughfare +
                "welsh_double_dependent_locality: " + welsh_double_dependent_locality +
                "welsh_dependent_locality: " + welsh_dependent_locality +
                "welsh_post_town: " + welsh_post_town +
                "po_box_number: " + po_box_number;

        return str;
    }

}
