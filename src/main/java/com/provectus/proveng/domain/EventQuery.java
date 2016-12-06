package com.provectus.proveng.domain;

import javax.persistence.MappedSuperclass;
import java.util.Date;

//@Entity
@MappedSuperclass

public class EventQuery {

    private Date fromDate = new Date();
    private Date toDate;
    private Date lastVisited;
    private int limit;

    public EventQuery() {
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getLastVisited() {
        return lastVisited;
    }

    public void setLastVisited(Date lastVisited) {
        this.lastVisited = lastVisited;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventQuery that = (EventQuery) o;

        if (limit != that.limit) return false;
        if (fromDate != null ? !fromDate.equals(that.fromDate) : that.fromDate != null) return false;
        if (toDate != null ? !toDate.equals(that.toDate) : that.toDate != null) return false;
        return lastVisited != null ? lastVisited.equals(that.lastVisited) : that.lastVisited == null;

    }

    @Override
    public int hashCode() {
        int result = fromDate != null ? fromDate.hashCode() : 0;
        result = 31 * result + (toDate != null ? toDate.hashCode() : 0);
        result = 31 * result + (lastVisited != null ? lastVisited.hashCode() : 0);
        result = 31 * result + limit;
        return result;
    }

    @Override
    public String toString() {
        return "EventQuery{" +
                "fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", lastVisited=" + lastVisited +
                ", limit=" + limit +
                '}';
    }
}
