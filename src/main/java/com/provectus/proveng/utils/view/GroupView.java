package com.provectus.proveng.utils.view;

public class GroupView extends AbstractView {

    public interface ShortInfoLevel extends BasicInfoLevel {
    }

    public interface AllInfoLevel extends ShortInfoLevel {
    }

    public interface RedundantInfoLevel extends AllInfoLevel {
    }
}
