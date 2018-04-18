package org.endeavourhealth.dataassurance.models;


import org.endeavourhealth.dataassurance.utils.UPRNUtils;

public class UPRNPatientResource {

    // MySQL table Field Names
    public static String UPRN_PATIENT_SQL_COL_FORENAMES = "forenames";
    public static String UPRN_PATIENT_SQL_COL_SURNAME = "surname";

    public static String UPRN_PATIENT_SQL_COL_ADDR1 = "address_line_1";
    public static String UPRN_PATIENT_SQL_COL_ADDR2 = "address_line_2";
    public static String UPRN_PATIENT_SQL_COL_ADDR3 = "address_line_3";

    public static String UPRN_PATIENT_SQL_COL_CITY = "city";
    public static String UPRN_PATIENT_SQL_COL_DISTRICT = "district";
    public static String UPRN_PATIENT_SQL_COL_POST_CODE = "postcode";

    // Model attributes
    private String forenames;
    private String surname;

    private String address_line1;
    private String address_line2;
    private String address_line3;

    private String city;
    private String district;
    private String post_code;

    // Meta data
    private boolean hasFlatIndicator;
    private String flatIndicatorNumber;

    // Matching Address Attributes
    private String uprn;
    private String abp_match_address;



    // Represents a Patient for UPRN matching purposes

    public UPRNPatientResource()
    {
        initState();

    }

    private void initState() {

        // Initialise variable
        this.hasFlatIndicator=false;

    }

    public UPRNPatientResource(String forenames, String surname,
                String address_line1, String address_line2, String address_line3,
                String city, String district, String post_code) {

        try {

            initState();

            this.forenames = forenames;
            this.surname = surname;

            this.address_line1 = UPRNUtils.sanitizeDiscoveryData(UPRNUtils.sanitizeGeneralData(address_line1));
            this.address_line2 = UPRNUtils.sanitizeDiscoveryData(UPRNUtils.sanitizeGeneralData(address_line2));
            this.address_line3 = UPRNUtils.sanitizeDiscoveryData(UPRNUtils.sanitizeGeneralData(address_line3));


            this.city = UPRNUtils.sanitizeDiscoveryData(UPRNUtils.sanitizeGeneralData(city));
            this.district = UPRNUtils.sanitizeDiscoveryData(UPRNUtils.sanitizeGeneralData(district));
            this.post_code = UPRNUtils.sanitizeDiscoveryData(UPRNUtils.sanitizeGeneralData(post_code));

            // Next do processing for any flat names
            sanitizeFlatData();

        } catch (Exception e) {

            //e.printStackTrace();
        }
    }

    // Sanitize and process any flat data
    private void sanitizeFlatData() {

        this.address_line1 = UPRNUtils.sanitizeDiscoveryFlatData(this.address_line1);

        if (this.hasFlatIndicator = UPRNUtils.checkFlatPrefix(this.address_line1)) {
            this.flatIndicatorNumber = UPRNUtils.extractFlatNumber(this.address_line1);
        } else {
            // In the absence of a "flat" word, check if there is a number in both address lines

            if (UPRNUtils.containsNumber(this.address_line1) && UPRNUtils.containsNumber(this.address_line2)) {
                this.hasFlatIndicator = true;
                this.flatIndicatorNumber = UPRNUtils.createFlatNumber(UPRNUtils.extractNumber(this.address_line1));
            }
        }

    }


    public String getForenames() {
        return forenames;
    }

    public void setForenames(String forenames) {
        this.forenames = forenames;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getAddress_line2() {
        return address_line2;
    }

    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

    public String getAddress_line3() {
        return address_line3;
    }

    public void setAddress_line3(String address_line3) {
        this.address_line3 = address_line3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }


    public String getUprn() {
        return this.uprn;
    }

    public void setUprn(String uprn) {
        this.uprn = uprn;
    }

    public String getAbp_match_address() {
        return this.abp_match_address;
    }

    public void setAbp_match_address(String abp_match_address) {
        this.abp_match_address = abp_match_address;
    }



    public String toString() {
        String str= "Forenames: "+forenames +
                "Surname: " + surname +
                "Address Line 1: "+address_line1 +
                "Address Line 2: " + address_line2 +
                "Address Line 3: " + address_line3 +
                "City: " + city +
                "District: " + district +
                "Post Code: " + post_code +
                "UPRN: " + uprn;

        return str;
    }

    public boolean hasFlatIndicator() {
        return this.hasFlatIndicator;
    }

    public void setHasFlatIndicator(boolean flatIndicator) {
        this.hasFlatIndicator = flatIndicator;
    }

    public String getFlatIndicatorNumber() {
        return this.flatIndicatorNumber;
    }

    public void setFlatIndicatorNumber(String flatIndicatorNumber) {
        this.flatIndicatorNumber = flatIndicatorNumber;
    }

}
