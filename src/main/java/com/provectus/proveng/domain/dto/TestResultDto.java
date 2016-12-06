package com.provectus.proveng.domain.dto;

public class TestResultDto {
    private boolean isPassed;
    private int mark;
    private String level;

    public TestResultDto() {
    }

    public TestResultDto(boolean isPassed, int mark, String level) {
        super();
        this.isPassed = isPassed;
        this.mark = mark;
        this.level = level;
    }

    public boolean getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(boolean isPassed) {
        this.isPassed = isPassed;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "TestResult [isPassed=" + isPassed + ", mark=" + mark + ", level=" + level + "]";
    }

}
