package org.endeavourhealth.dataassurance.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CUIFormatter {
    public String getFormattedName(String title, String forename, String surname) {
        List<String> parts = new ArrayList<>();

        if (title != null && !title.isEmpty())
            parts.add("(" + toSentenceCase(title) + ")");

        if (forename != null && !forename.isEmpty())
            parts.add(toSentenceCase(forename));

        if (surname != null && !surname.isEmpty())
            if (parts.size() == 0)
                parts.add(surname.toUpperCase());
            else
                parts.add(surname.toUpperCase() + ",");

        if (parts.size() == 0)
            return "Unknown";

        Collections.reverse(parts);

        return String.join(" ", parts);
    }

    public String toSentenceCase(String sentence) {
        if (sentence == null || sentence.isEmpty())
            return sentence;

        return Character.toUpperCase(sentence.charAt(0)) + sentence.substring(1).toLowerCase();
    }

}
