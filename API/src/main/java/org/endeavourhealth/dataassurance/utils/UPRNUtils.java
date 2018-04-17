package org.endeavourhealth.dataassurance.utils;


import java.util.Date;

public class UPRNUtils {

    public static String FLAT_DEMARCATOR = "flat";

    // General sanitization of data before matching
    public static String sanitizeGeneralData(String data) {
        String sanitizedData = "";

        if (data == null) {
            return sanitizedData;
        } else {

            // Do some general cleanup first
            sanitizedData = data.trim().toLowerCase()
                    .replace(",", " ").replace(".", " ").replace("  ", " ");

        }

        return sanitizedData;
    }

    // Sanitization of Discovery data before matching
    public static String sanitizeDiscoveryData(String data) {
        String sanitizedData = "";

        if (data == null) {
            return sanitizedData;
        } else {

            // Sanitize road abbreviations
            sanitizedData = data.replace(" rd", " road");

        }

        return sanitizedData;
    }

    // Sanitization of Discovery flat data before matching
    public static String sanitizeDiscoveryFlatData(String data) {
        String sanitizedData = "";

        // Sanitize flat abbreviations
        sanitizedData = data.replace("flt", FLAT_DEMARCATOR).replace("apt", FLAT_DEMARCATOR);

        return sanitizedData;
    }

    // Sanitization of Address Base Premium data before matching
    public static String sanitizeABPData(String data) {
        String sanitizedData = "";

        if (data == null) {
            return sanitizedData;
        } else {

            sanitizedData = data.replace(" rd", " road");

        }

        return sanitizedData;
    }


    // Checks if data contains "flat" and if so, return true if its a flat
    public static boolean checkFlatPrefix (String data) {
        boolean isFlat=false;

        if (data.contains(" "+FLAT_DEMARCATOR)|| data.contains(FLAT_DEMARCATOR+" ")) {
            isFlat = true;
        }

        return isFlat;
    }

    // Extract just the flat number from the passed in address line
    public static String extractFlatNumber(String data) {
        String flatNumberStr="";

        String flatNumText = data.substring(FLAT_DEMARCATOR.length()+1);

        flatNumberStr = extractNumber(flatNumText);

        // System.out.println("Extracted flat number: "+FLAT_DEMARCATOR+" "+flatNumberStr+" from text: "+data);

        return FLAT_DEMARCATOR+" "+flatNumberStr;
    }

    // Create a flat number with prefix
    public static String createFlatNumber(String data) {

        return FLAT_DEMARCATOR+" "+data;
    }


    // Extract just the number from the passed in address line
    public static String extractNumber(String data) {
        String numberStr="";

        char[] numTextChars = data.toCharArray();

        for (int i=0; i<numTextChars.length; i++) {

            char chr = numTextChars[i];

            if (Character.isDigit(chr) || Character.isAlphabetic(chr)) {

                numberStr = numberStr+String.valueOf(chr);
            } else {
                break;
            }
        }

        // System.out.println("Extracted number: "+numberStr);

        return numberStr;
    }

    public static boolean containsNumber(String data) {
        boolean containsNumber=false;

        char[] numTextChars = data.toCharArray();

        boolean hadNumber=false;
        for (int i=0; i<numTextChars.length; i++) {

            char chr = numTextChars[i];

            if (Character.isDigit(chr)) {

                hadNumber = true;
            } else {

                if(hadNumber && Character.isSpaceChar(chr)) {
                    containsNumber=true;

                    break;
                }
            }
        }

        return containsNumber;
    }

    // Get date and time for metrics
    public static String getDateAndTime() {
        Date dateAndTime = new Date();

        return dateAndTime.toString();
    }



}
