package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.utils.view.DayBookView;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TestAttempt")
@SequenceGenerator(name = "S_TEST_ATTEMPT_SEQ", sequenceName = "S_TEST_ATTEMPT_SEQ",
        allocationSize = 1)
public class TestAttempt extends BaseEntity {

    @Column(name = "IS_PASSED")
    @JsonView(DayBookView.TestResultLevel.class)
    private Boolean isPassed;

    @Column(name = "NOTES")
    @JsonView(DayBookView.TestResultLevel.class)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "TEST_ID", referencedColumnName = "ID")
    @JsonView(DayBookView.TestResultLevel.class)
    private Test test;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "TEST_ATTEMPT_ID", referencedColumnName = "ID")
    private Set<TestCardChoice> testCardChoices = new HashSet<>();

    @JsonView(DayBookView.TestResultLevel.class)
    public Boolean getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Boolean isPassed) {
        this.isPassed = isPassed;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Set<TestCardChoice> getTestCardChoices() {
        return testCardChoices;
    }

    public void setTestCardChoices(Set<TestCardChoice> testCardChoices) {
        this.testCardChoices = testCardChoices;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((isPassed == null) ? 0 : isPassed.hashCode());
        result = prime * result + ((notes == null) ? 0 : notes.hashCode());
        result = prime * result + ((test == null) ? 0 : test.hashCode());
        result = prime * result + ((testCardChoices == null) ? 0 : testCardChoices.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        TestAttempt other = (TestAttempt) obj;
        if (isPassed == null) {
            if (other.isPassed != null)
                return false;
        } else if (!isPassed.equals(other.isPassed))
            return false;
        if (notes == null) {
            if (other.notes != null)
                return false;
        } else if (!notes.equals(other.notes))
            return false;
        if (test == null) {
            if (other.test != null)
                return false;
        } else if (!test.equals(other.test))
            return false;
        if (testCardChoices == null) {
            if (other.testCardChoices != null)
                return false;
        } else if (!testCardChoices.equals(other.testCardChoices))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TestAttempt [isPassed=" + isPassed + ", notes=" + notes + ", test=" + test
                + ", testCardChoices=" + testCardChoices + "]";
    }


}
