package org.endeavourhealth.dataassurance.models;

public class UPRNLPIAddress {

    // Represents a record of ABP LPI Address information

    // Constant definitions

    // MySQL table Field Names
    public static String UPRN_LPI_ADDR_SQL_COL_UPRN = "UPRN";
    public static String UPRN_LPI_ADDR_SQL_COL_SAO_START_NUMBER="SAO_START_NUMBER";
    public static String UPRN_LPI_ADDR_SQL_COL_SAO_START_SUFFIX="SAO_START_SUFFIX";
    public static String UPRN_LPI_ADDR_SQL_COL_SAO_END_NUMBER="SAO_END_NUMBER";
    public static String UPRN_LPI_ADDR_SQL_COL_SAO_END_SUFFIX="SAO_END_SUFFIX";
    public static String UPRN_LPI_ADDR_SQL_COL_SAO_TEXT="SAO_TEXT";
    public static String UPRN_LPI_ADDR_SQL_COL_PAO_START_NUMBER="PAO_START_NUMBER";
    public static String UPRN_LPI_ADDR_SQL_COL_PAO_START_SUFFIX="PAO_START_SUFFIX";
    public static String UPRN_LPI_ADDR_SQL_COL_PAO_END_NUMBER="PAO_END_NUMBER";
    public static String UPRN_LPI_ADDR_SQL_COL_PAO_END_SUFFIX="PAO_END_SUFFIX";
    public static String UPRN_LPI_ADDR_SQL_COL_PAO_TEXT="PAO_TEXT";

    // Model attributes
    // Unique property reference number
    private long uprn;
    private String sao_start_number;
    private String sao_start_suffix;
    private String sao_end_number;
    private String sao_end_suffix;
    private String sao_text;
    private String pao_start_number;
    private String pao_start_suffix;
    private String pao_end_number;
    private String pao_end_suffix;
    private String pao_text;


    public UPRNLPIAddress()
    {

    }

    public UPRNLPIAddress(long uprn,
                               String sao_start_number,
                               String sao_start_suffix,
                               String sao_end_number,
                               String sao_end_suffix,
                               String sao_text,
                               String pao_start_number,
                               String pao_start_suffix,
                               String pao_end_number,
                               String pao_end_suffix,
                               String pao_text) {
        this.uprn = uprn;
        this.sao_start_number = sao_start_number;
        this.sao_start_suffix = sao_start_suffix;
        this.sao_end_number = sao_end_number;
        this.sao_end_suffix = sao_end_suffix;
        this.sao_text = sao_text;
        this.pao_start_number = pao_start_number;
        this.pao_start_suffix = pao_start_suffix;
        this.pao_end_number = pao_end_number;
        this.pao_end_suffix = pao_end_suffix;
        this.pao_text = pao_text;

    }

    public long getUprn() {
        return uprn;
    }

    public void setUprn(long uprn) {
        this.uprn = uprn;
    }

    public String getSao_start_number() {
        return sao_start_number;
    }

    public void setSao_start_number(String sao_start_number) {
        this.sao_start_number = sao_start_number;
    }

    public String getSao_start_suffix() {
        return sao_start_suffix;
    }

    public void setSao_start_suffix(String sao_start_suffix) {
        this.sao_start_suffix = sao_start_suffix;
    }

    public String getSao_end_number() {
        return sao_end_number;
    }

    public void setSao_end_number(String sao_end_number) {
        this.sao_end_number = sao_end_number;
    }

    public String getSao_end_suffix() {
        return sao_end_suffix;
    }

    public void setSao_end_suffix(String sao_end_suffix) {
        this.sao_end_suffix = sao_end_suffix;
    }

    public String getSao_text() {
        return sao_text;
    }

    public void setSao_text(String sao_text) {
        this.sao_text = sao_text;
    }

    public String getPao_start_number() {
        return pao_start_number;
    }

    public void setPao_start_number(String pao_start_number) {
        this.pao_start_number = pao_start_number;
    }

    public String getPao_start_suffix() {
        return pao_start_suffix;
    }

    public void setPao_start_suffix(String pao_start_suffix) {
        this.pao_start_suffix = pao_start_suffix;
    }

    public String getPao_end_number() {
        return pao_end_number;
    }

    public void setPao_end_number(String pao_end_number) {
        this.pao_end_number = pao_end_number;
    }

    public String getPao_end_suffix() {
        return pao_end_suffix;
    }

    public void setPao_end_suffix(String pao_end_suffix) {
        this.pao_end_suffix = pao_end_suffix;
    }

    public String getPao_text() {
        return pao_text;
    }

    public void setPao_text(String pao_text) {
        this.pao_text = pao_text;
    }

    public String toString() {
        String str= "uprn: " + uprn +
                "sao_start_number: " + sao_start_number +
                "sao_start_suffix: " + sao_start_suffix +
                "sao_end_number: " + sao_end_number +
                "sao_end_suffix: " + sao_end_suffix +
                "sao_text: " + sao_text +
                "pao_start_number: " + pao_start_number +
                "pao_start_suffix: " + pao_start_suffix +
                "pao_end_number: " + pao_end_number +
                "pao_end_suffix: " + pao_end_suffix +
                "pao_text: " + pao_text;
        return str;
    }
}
