package com.provectus.proveng.enumaration;

public enum LevelType {

    UNKNOWN(0, "Unknown"),
    ELEMENTARY(1, "Elementary"),
    PRE_INTERMEDIATE(2, "Pre-intermediate"),
    INTERMEDIATE(3, "Intermediate"),
    UPPER_INTERMEDIATE(4, "Upper-intermediate");
    //ADVANCED(5, "Advanced");

    private int numberValue;
    private String stringValue;

    LevelType(int numberValue, String stringValue) {
        this.numberValue = numberValue;
        this.stringValue = stringValue;
    }

    public static String convertToString(int intValue) {
        String stringValue = null;

        switch (intValue) {
            case 0:
                stringValue = LevelType.UNKNOWN.stringValue;
                break;
            case 1:
                stringValue = LevelType.ELEMENTARY.stringValue;
                break;
            case 2:
                stringValue = LevelType.PRE_INTERMEDIATE.stringValue;
                break;
            case 3:
                stringValue = LevelType.INTERMEDIATE.stringValue;
                break;
            case 4:
                stringValue = LevelType.UPPER_INTERMEDIATE.stringValue;
                break;

        }
        return stringValue;
    }

    public int getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(int numberValue) {
        this.numberValue = numberValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

}
