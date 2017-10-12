package org.endeavourhealth.datavalidation.logic;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

class SearchTermsParser {
    private String nhsNumber;
    private String emisNumber;
    private Date dateOfBirth;
    private List<String> names = new ArrayList<>();

    SearchTermsParser(String searchTerms) {
        if (StringUtils.isEmpty(searchTerms))
            return;

        //String[] tokens = searchTerms.split(" ");
        List<String> tokens = Arrays.asList(searchTerms.split(" "));

        //remove any empty tokens before any further processing, so accidental double-spaces don't cause problems
        removeEmptyTokens(tokens);

        //not the nicest way of doing this, but if we have three separate numeric tokens that total 10 chars,
        //then mash them together as it's an NHS number search
        combineNhsNumberTokens(tokens);

        for (String token : tokens) {

            if (StringUtils.isNumeric(token)) {
                if (token.length() == 10)
                    this.nhsNumber = token;
                else
                    this.emisNumber = token;
            } else if ((token.length() == 10 || token.length() == 11) && StringUtils.countMatches(token, "-")==2) {
                // Assume its a date dd-MMM-yyyy & attempt to parse
                SimpleDateFormat sf = new SimpleDateFormat("dd-MMM-yyyy");
                try {
                    dateOfBirth = sf.parse(token);
                } catch (ParseException e) {
                    // Not a valid date, continue and treat as regular token
                }
            }

            this.names.add(token);
        }
    }

    boolean hasNhsNumber() {
        return StringUtils.isNotBlank(this.nhsNumber);
    }

    String getNhsNumber() {
        return nhsNumber;
    }

    boolean hasEmisNumber() {
        return StringUtils.isNotBlank(this.emisNumber);
    }

    String getEmisNumber() {
        return emisNumber;
    }

    boolean hasDateOfBirth() {
        return this.dateOfBirth != null;
    }

    Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    List<String> getNames() {
        return names;
    }

    private static void removeEmptyTokens(List<String> tokens) {
        for (int i=tokens.size()-1; i>=0; i--) {
            String token = tokens.get(i);
            token = token.trim();

            if (StringUtils.isEmpty(token)) {
                tokens.remove(i);
            } else {
                //replace with the trimmed version
                tokens.set(i, token);
            }
        }
    }

    private static void combineNhsNumberTokens(List<String> tokens) {

        if (tokens.size() != 3) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        for (String token : tokens) {
            if (StringUtils.isNumeric(token)) {
                sb.append(token);
            } else {
                return;
            }
        }

        String combined = sb.toString();
        if (combined.length() != 10) {
            return;
        }

        tokens.clear();
        tokens.add(combined);
    }
}
