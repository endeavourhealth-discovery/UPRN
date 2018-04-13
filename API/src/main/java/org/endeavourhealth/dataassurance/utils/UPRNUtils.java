package org.endeavourhealth.dataassurance.utils;


public class UPRNUtils {

    public static String sanitize(String data) {
        if (data == null) {
            return "";
        } else {
            return (data.trim().toLowerCase()
                    .replace(" ", " ").replace(".", " "));
        }
    }

}
