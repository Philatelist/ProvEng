///***********************************************************************
// * Module:  Session.java
// * Author:  andy
// * Purpose: Defines the Class Session
// ***********************************************************************/
//
package com.provectus.proveng.domain;
//

import javax.persistence.*;

//import java.util.*;
//

@Entity
@Table(name = "LoginHistory")
@SequenceGenerator(name = "S_LOGIN_HISTORY_SEQ", sequenceName = "S_LOGIN_HISTORY_SEQ", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "ID"))
public class LoginSession extends BaseEntity {

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "JSESSION_ID")
    private String token;

    public LoginSession() {

    }

    public LoginSession(User user, String token) {
        this.user = user;
        this.token = token;
    }

    @Override
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "S_LOGIN_HISTORY_SEQ")
    public long getId() {
        return super.getId();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginSession that = (LoginSession) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return token != null ? token.equals(that.token) : that.token == null;

    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoginSession{" +
                "user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
// public void setUser(User newUser) {
    // if (this.user == null || !this.user.equals(newUser)) {
    // if (this.user != null) {
    // User oldUser = this.user;
    // this.user = null;
    // oldUser.removeSession(this);
    // }
    // if (newUser != null) {
    // this.user = newUser;
    // this.user.addSession(this);
    // }
    // }
    // }
    //
}
