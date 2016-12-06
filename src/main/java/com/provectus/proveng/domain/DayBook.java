package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.utils.view.DayBookView;
import com.provectus.proveng.utils.view.UserView;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "daybook")
@SequenceGenerator(name = "daybook_seq", sequenceName = "daybook_seq", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "ID"))

public class DayBook extends BaseEntity {

    @Column(name = "mark")
    @JsonView({DayBookView.ShortInfoLevel.class, UserView.AllInfoLevel.class,
            DayBookView.TestResultLevel.class, UserView.UserLevel.class})
    private int mark = 0;

    @Column(name = "type")
    @JsonView({DayBookView.ShortInfoLevel.class, UserView.AllInfoLevel.class, UserView.UserLevel.class})
    private String type = "Test";

    @Column(name = "mark_date")
    @JsonView({DayBookView.ShortInfoLevel.class, UserView.AllInfoLevel.class, UserView.UserLevel.class})
    private Date markDate = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID", updatable = false)
    @JsonView(DayBookView.AllInfoLevel.class)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    @JsonView(DayBookView.AllInfoLevel.class)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEST_ATTEMPT_ID")
    @JsonView(DayBookView.TestResultLevel.class)
    private TestAttempt testAttempt;

    public DayBook() {
    }

    public DayBook(int mark, String type, Date markDate, Event event, User user,
                   TestAttempt testAttempt) {
        super();
        this.mark = mark;
        this.type = type;
        this.markDate = markDate;
        this.event = event;
        this.user = user;
        this.testAttempt = testAttempt;
    }

    @Override
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "daybook_seq")
    public long getId() {
        return super.getId();
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getMarkDate() {
        return markDate;
    }

    public void setMarkDate(Date markDate) {
        this.markDate = markDate;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event newEvent) {
        if (this.event == null || !this.event.equals(newEvent)) {
            if (this.event != null) {
                Event oldEvent = this.event;
                this.event = null;
                oldEvent.removeDayBook(this);
            }
            if (newEvent != null) {
                this.event = newEvent;
                this.event.addDayBook(this);
            }
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User newUser) {
        if (this.user == null || !this.user.equals(newUser)) {
            if (this.user != null) {
                User oldUser = this.user;
                this.user = null;
                oldUser.removeDayBook(this);
            }
            if (newUser != null) {
                this.user = newUser;
                this.user.addDayBook(this);
            }
        }
    }

    public TestAttempt getTestAttempt() {
        return testAttempt;
    }

    public void setTestAttempt(TestAttempt testAttempt) {
        this.testAttempt = testAttempt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DayBook dayBook = (DayBook) o;

        if (mark != dayBook.mark)
            return false;
        if (type != null ? !type.equals(dayBook.type) : dayBook.type != null)
            return false;
        if (user != null ? user.getId() == (dayBook.user.getId()) : dayBook.user != null)
            return false;
        if (event != null ? event.getId() == (dayBook.event.getId()) : dayBook.event != null)
            return false;
        return markDate != null ? !markDate.equals(dayBook.markDate) : dayBook.markDate != null;

    }

    @Override
    public int hashCode() {
        int result = mark;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (markDate != null ? markDate.hashCode() : 0);
        result = 31 * result + (event != null ? (int) (event.getId() ^ (event.getId() >>> 32)) : 0);
        result = 31 * result + (user != null ? (int) (user.getId() ^ (user.getId() >>> 32)) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DayBook{" + "mark=" + mark + ", type='" + type + '\'' + ", markDate=" + markDate
                + ", event=" + (event == null ? "null" : event.getId()) + ", user="
                + (user == null ? "null" : user.getId()) + '}';
    }
}
