package com.provectus.proveng.utils.view;

public class MaterialView extends AbstractView {

    public interface ShortInfoLevel extends BasicInfoLevel {
    }

    public interface AllInfoLevel extends ShortInfoLevel {
    }

    public interface RedundantInfoLevel extends AllInfoLevel {
    }
}
