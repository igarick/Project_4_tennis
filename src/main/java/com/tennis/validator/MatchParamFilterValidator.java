package com.tennis.validator;

import com.tennis.exception.ErrorInfo;
import com.tennis.exception.MatchesParamFilterException;

public class MatchParamFilterValidator {
    public static void validate(String paramFilter) {
        boolean validFormat = RequestValidator.isValidFormat(paramFilter);
        if (!validFormat) {
            throw new MatchesParamFilterException(ErrorInfo.NAME_ERROR);
        }
    }
}
