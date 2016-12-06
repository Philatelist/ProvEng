package com.provectus.proveng.enumaration;

public enum ErrorType {
    AUTH_FALSE("An error occurred during authentication."),

    NO_ACCESS("You don`t have a valid permission."),

    ALREADY_IN_GROUP("You are already in group."),

    EMPTY_FIELDS("Some fields is empty."),

    DOUBLE("This object already exist."),

    BAD_INPUT("Check the data you entered is correct."),

    NOT_IN_DB("This object is not in the database."),

    NO_ADD_TO_WORKSHOP("Unfortunately the workshop is full. You are in queue."),

    NOT_IN_GROUPS("You don`t belong to any of the groups.");

    private String description;

    ErrorType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
