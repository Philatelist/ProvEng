package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.utils.view.TestView;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "TestCard")
public class TestCard extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "TEST_CARD_ID", referencedColumnName = "ID")
    @JsonView(TestView.AllInfoLevel.class)
    Collection<TestAnswer> testAnswers = new HashSet<>();
    @Column(name = "NAME")
    @JsonView(TestView.AllInfoLevel.class)
    private String name;
    @Column(name = "ORDER_NUMBER")
    private Integer orderNumber;
    @Column(name = "QUESTION")
    @JsonView(TestView.AllInfoLevel.class)
    private String question;


    public TestCard() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Collection<TestAnswer> getTestAnswers() {
        return testAnswers;
    }

    public void setTestAnswers(Collection<TestAnswer> testAnswers) {
        this.testAnswers = testAnswers;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((orderNumber == null) ? 0 : orderNumber.hashCode());
        result = prime * result + ((question == null) ? 0 : question.hashCode());
        result = prime * result + ((testAnswers == null) ? 0 : testAnswers.hashCode());
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
        TestCard other = (TestCard) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (orderNumber == null) {
            if (other.orderNumber != null)
                return false;
        } else if (!orderNumber.equals(other.orderNumber))
            return false;
        if (question == null) {
            if (other.question != null)
                return false;
        } else if (!question.equals(other.question))
            return false;
        if (testAnswers == null) {
            if (other.testAnswers != null)
                return false;
        } else if (!testAnswers.equals(other.testAnswers))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TestCard [name=" + name + ", orderNumber=" + orderNumber + ", question=" + question
                + ", testAnswers=" + testAnswers + "]";
    }


//	@ManyToOne
//	@JoinColumn(name = "TEST_ID", nullable=false, updatable=false)
//	private Test test;


}
