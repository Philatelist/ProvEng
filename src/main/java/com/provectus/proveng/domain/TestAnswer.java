package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.utils.view.TestView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TestAnswer")
public class TestAnswer extends BaseEntity {

    @Column(name = "NAME")
    @JsonView(TestView.AllInfoLevel.class)
    private String name;

    @Column(name = "IS_CORRECT")
    private Boolean isCorrect;

    @Column(name = "TEXT")
    @JsonView(TestView.AllInfoLevel.class)
    private String text;

    @Column(name = "IS_VISIBLE")
    private Boolean isVisible;

    public TestAnswer() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((isCorrect == null) ? 0 : isCorrect.hashCode());
        result = prime * result + ((isVisible == null) ? 0 : isVisible.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
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
        TestAnswer other = (TestAnswer) obj;
        if (isCorrect == null) {
            if (other.isCorrect != null)
                return false;
        } else if (!isCorrect.equals(other.isCorrect))
            return false;
        if (isVisible == null) {
            if (other.isVisible != null)
                return false;
        } else if (!isVisible.equals(other.isVisible))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TestAnswer [name=" + name + ", isCorrect=" + isCorrect + ", text=" + text
                + ", isVisible=" + isVisible + "]";
    }

    public boolean isCorrect() {
        return (this.isCorrect == null) ? false : this.isCorrect;
    }


}
