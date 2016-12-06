package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.utils.view.PasswordView;
import com.provectus.proveng.utils.view.UserView;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;


@Entity
@Component
@Table(name = "password")
@SequenceGenerator(name = "s_po_password_seq", sequenceName = "s_po_password_seq", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "ID"))
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Password extends BaseEntity {

    /**
     * Password could be local password, Google token or other OAUTH system token.
     */
    @Column(name = "password")
    @JsonView({UserView.AllInfoLevel.class, PasswordView.AllInfoLevel.class})
    public String password;

    @ManyToOne
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    @JsonView(PasswordView.AllInfoLevel.class)
    public User user;

    public Password() {
    }

    public Password(String password) {
        this.password = password;
    }

    public Password(String password, User user) {
        this.password = password;
        this.user = user;
    }

    @Override
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "s_po_password_seq")
    public long getId() {
        return super.getId();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }


    public void setUser(User newUser) {
        if (this.user == null || !this.user.equals(newUser)) {
            if (this.user != null) {
                User oldUser = this.user;
                this.user = null;
                oldUser.removePassword(this);
            }
            if (newUser != null) {
                this.user = newUser;
                this.user.addPassword(this);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Password password1 = (Password) o;

        if (password != null ? !password.equals(password1.password) : password1.password != null) return false;
        return user != null ? user.equals(password1.user) : password1.user == null;

    }

    @Override
    public int hashCode() {
        int result = password != null ? password.hashCode() : 0;
//        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Password{" +
                "password='" + password + '\'' +
//                ", user=" + user +
                '}';
    }
}
