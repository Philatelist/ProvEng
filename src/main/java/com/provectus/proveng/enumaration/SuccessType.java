package com.provectus.proveng.enumaration;

public enum SuccessType {


    DELETE_OK("The object was delete succesfully."),

    OK("The operation was successfully.");

    private String description;

    SuccessType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
