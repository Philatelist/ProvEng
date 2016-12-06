package com.provectus.proveng.utils.view;

public class DepartmentView extends AbstractView {

    public interface ShortInfoLevel extends BasicInfoLevel {
    }

    public interface MediumInfoLevel extends ShortInfoLevel {

    }

    public interface AllInfoLevel extends MediumInfoLevel {
    }
}
