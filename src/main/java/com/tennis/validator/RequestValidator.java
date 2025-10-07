package com.tennis.validator;

import java.util.Optional;

public class RequestValidator {

    private static final String EMPTY_FIELD_ERROR = "All fields must be filled in";
    private static final String IDENTICAL_NAMES_ERROR = "Names must be different";
    private static final String NAME_ERROR = "The name must be 1 - 15 English letters";
    private static final String NAME_PATTERN = "[a-zA-Z]{1,15}";

    public static Optional<String> validateParam(String firstName, String secondName) {
        if (isEmpty(firstName, secondName)) {
            return Optional.of(EMPTY_FIELD_ERROR);
        }

        if (!isValidFormat(firstName, secondName)) {
            return Optional.of(NAME_ERROR);
        }

        if (firstName.equals(secondName)) {
            return Optional.of(IDENTICAL_NAMES_ERROR);
        }

        return Optional.empty();
    }

    private static boolean isEmpty(String firstName, String secondName) {
        return ((firstName == null || secondName == null)
        || (firstName.isBlank() || secondName.isBlank()));
    }

    private static boolean isValidFormat(String firstName, String secondName) {
        return (firstName.matches(NAME_PATTERN) && secondName.matches(NAME_PATTERN));
    }

}
