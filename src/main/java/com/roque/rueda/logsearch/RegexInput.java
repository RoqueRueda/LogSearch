package com.roque.rueda.logsearch;

import com.sun.istack.internal.NotNull;

import java.util.regex.Pattern;

public class RegexInput {

    private Pattern pattern;

    /**
     * Creates a RegexInput with the given regex
     * @param regex String regex used to build a pattern to find matches in another String
     */
    public RegexInput(@NotNull String regex) {
        if (regex == null) {
            return;
        }

        pattern = Pattern.compile(regex);
    }

    public Pattern getPattern() {
        return pattern;
    }
}
