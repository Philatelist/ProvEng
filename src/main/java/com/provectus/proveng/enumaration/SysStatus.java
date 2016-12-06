package com.provectus.proveng.enumaration;

public enum SysStatus {
    ON(0), OFF(-1);
    private int number;

    SysStatus(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
