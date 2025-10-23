package com.tennis.validator;

import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class RequestValidator {
    private static final String EMPTY_FIELD_ERROR = "All fields must be filled in";
    private static final String IDENTICAL_NAMES_ERROR = "Names must be different";
    public static final String NAME_ERROR = "The name must be 2 - 20 English letters";
    private static final String NAME_PATTERN = "[a-zA-Z ]{2,20}";

    public static Optional<String> validateParam(String firstName, String secondName) {
        if (isEmpty(firstName, secondName)) {
            return Optional.of(EMPTY_FIELD_ERROR);
        }

        if (!isValidFormat(firstName) || !isValidFormat(secondName)) {
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

    public static boolean isValidFormat(String name) {
        return (name.matches(NAME_PATTERN));
    }

}
