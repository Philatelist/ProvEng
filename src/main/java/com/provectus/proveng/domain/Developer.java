package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.utils.view.DeveloperView;

import javax.persistence.*;

@Entity
@Table(name = "developers")
@SequenceGenerator(name = "s_dev_seq", sequenceName = "s_dev_seq", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "ID"))
public class Developer extends BaseEntity {

    @Column(name = "email")
    @JsonView(DeveloperView.ShortInfoLevel.class)
    private String email;

    public Developer() {
    }

    public Developer(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Developer developer = (Developer) o;

        return email != null ? email.equals(developer.email) : developer.email == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "email='" + email + '\'' +
                '}';
    }
}
