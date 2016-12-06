package com.provectus.proveng.utils.view;

public class EventView extends AbstractView {

    public interface ShortInfoLevel extends BasicInfoLevel {
    }

    public interface CalendarInfoLevel extends ShortInfoLevel {
    }

    public interface ScheduleInfoLevel extends CalendarInfoLevel {
    }

    public interface MediumInfoLevel extends CalendarInfoLevel {
    }

    public interface RelationLevel extends MediumInfoLevel {

    }

    public interface AllInfoLevel extends MediumInfoLevel {
    }
}
