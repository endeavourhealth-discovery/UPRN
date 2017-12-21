package org.endeavourhealth.datavalidation.helpers;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SearchTermsParser {
    private String nhsNumber;
    private String emisNumber;
    private Date dateOfBirth;
    private List<String> names = new ArrayList<>();

    public SearchTermsParser(String searchTerms) {
        if (StringUtils.isEmpty(searchTerms))
            return;

        //String[] tokens = searchTerms.split(" ");
        List<String> tokens = Arrays.asList(searchTerms.split(" "));

        //remove any empty tokens before any further processing, so accidental double-spaces don't cause problems
        tokens = removeEmptyTokens(tokens);

        //not the nicest way of doing this, but if we have three separate numeric tokens that total 10 chars,
        //then mash them together as it's an NHS number search
        tokens = combineNhsNumberTokens(tokens);

        for (String token : tokens) {

            if (isDate(token)) {
                // Assume its a date dd-MMM-yyyy & attempt to parse
                SimpleDateFormat sf = new SimpleDateFormat("dd-MMM-yyyy");
                try {
                    dateOfBirth = sf.parse(token);
                } catch (ParseException e) {
                    // Not a valid date, continue and treat as regular token
                }
            } else if (!StringUtils.isAlpha(token)) {
                if (token.length() == 10)
                    this.nhsNumber = token;
                else
                    this.emisNumber = token;
            } else {
                this.names.add(token);
            }
        }
    }

    public boolean isDate(String token) {
        return (token.length() == 10 || token.length() == 11) && StringUtils.countMatches(token, "-")==2;
    }

    public boolean hasNhsNumber() {
        return StringUtils.isNotBlank(this.nhsNumber);
    }

    public String getNhsNumber() {
        return nhsNumber;
    }

    public boolean hasEmisNumber() {
        return StringUtils.isNotBlank(this.emisNumber);
    }

    public String getEmisNumber() {
        return emisNumber;
    }

    public boolean hasDateOfBirth() {
        return this.dateOfBirth != null;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public List<String> getNames() {
        return names;
    }

    private static List<String> removeEmptyTokens(List<String> tokens) {
        List<String> result = new ArrayList<>();

        for(String token : tokens) {
            token = token.trim();
            if (!StringUtils.isEmpty(token)) {
                result.add(token);
            }
        }

        return result;
    }

    private static List<String> combineNhsNumberTokens(List<String> tokens) {
        if (tokens.size() != 3)
            return tokens;

        StringBuilder sb = new StringBuilder();

        for (String token : tokens) {
            if (StringUtils.isNumeric(token)) {
                sb.append(token);
            } else {
                return tokens;
            }
        }

        String combined = sb.toString();
        if (combined.length() != 10)
            return tokens;

        List<String> result = new ArrayList<>();
        result.add(combined);
        return result;
    }
}
