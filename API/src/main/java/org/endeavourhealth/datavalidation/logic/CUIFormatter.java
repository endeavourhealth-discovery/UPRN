package org.endeavourhealth.datavalidation.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class CUIFormatter {
    static String getFormattedName(String title, String forename, String surname) {
        List<String> parts = new ArrayList<>();

        if (title != null && !title.isEmpty())
            parts.add("(" + CUIFormatter.toSentenceCase(title) + ")");

        if (forename != null && !forename.isEmpty())
            parts.add(CUIFormatter.toSentenceCase(forename));

        if (surname != null && !surname.isEmpty())
            if (parts.size() == 0)
                parts.add(surname.toUpperCase());
            else
                parts.add(surname.toUpperCase()+",");

        Collections.reverse(parts);

        return String.join(" ", parts);
    }

    static String toSentenceCase(String sentence) {
        if (sentence == null || sentence.isEmpty())
            return sentence;

        return Character.toUpperCase(sentence.charAt(0)) + sentence.substring(1).toLowerCase();
    }

}
