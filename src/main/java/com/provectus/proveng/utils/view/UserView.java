package com.provectus.proveng.utils.view;

public class UserView extends AbstractView {

    public interface ShortInfoLevel extends BasicInfoLevel {
    }

    public interface UserLevel extends BasicInfoLevel {

    }

    public interface MediumInfoLevel extends ShortInfoLevel {
    }

    public interface AllInfoLevel extends MediumInfoLevel {
    }

    public interface RedundantInfoLevel extends AllInfoLevel {
    }
}
