package com.provectus.proveng.domain;

import javax.persistence.*;

@Entity
@Table(name = "TestCardChoice")
@SequenceGenerator(name = "S_TEST_CARD_CHOICE_SEQ", sequenceName = "S_TEST_CARD_CHOICE_SEQ",
        allocationSize = 1)
public class TestCardChoice extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEST_ANSWER_ID", referencedColumnName = "ID")
    private TestAnswer testAnswer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEST_CARD_ID", referencedColumnName = "ID")
    private TestCard testCard;

    public TestCardChoice() {
        super();
    }

    public TestAnswer getTestAnswer() {
        return testAnswer;
    }

    public void setTestAnswer(TestAnswer testAnswer) {
        this.testAnswer = testAnswer;
    }

    public TestCard getTestCard() {
        return testCard;
    }

    public void setTestCard(TestCard testCard) {
        this.testCard = testCard;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((testAnswer == null) ? 0 : testAnswer.hashCode());
        result = prime * result + ((testCard == null) ? 0 : testCard.hashCode());
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
        TestCardChoice other = (TestCardChoice) obj;
        if (testAnswer == null) {
            if (other.testAnswer != null)
                return false;
        } else if (!testAnswer.equals(other.testAnswer))
            return false;
        if (testCard == null) {
            if (other.testCard != null)
                return false;
        } else if (!testCard.equals(other.testCard))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TestCardChoice [testAnswer=" + testAnswer + ", testCard=" + testCard + "]";
    }


}
