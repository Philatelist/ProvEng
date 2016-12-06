package com.provectus.proveng.enumaration;

public enum DaybookType {

    LESSON("Lesson"),

    PRESENTATION("Presentation"),

    TEST("Test"),

    START_TEST("StartTest"),

    WORKSHOP("Workshop"),

    TOTAL_POINTS("TotalPoints"),

    FINAL_TEST("FinalTest");

    private String daybookType;

    DaybookType(String daybookType) {
        this.daybookType = daybookType;
    }

    public String getDaybookType() {
        return daybookType;
    }
}
