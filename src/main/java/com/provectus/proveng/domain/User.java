package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.provectus.proveng.utils.view.DepartmentView;
import com.provectus.proveng.utils.view.EventView;
import com.provectus.proveng.utils.view.GroupView;
import com.provectus.proveng.utils.view.UserView;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

@Entity
@Component
@Table(name = "po_user")
@SequenceGenerator(name = "s_po_contact_seq", sequenceName = "s_po_contact_seq", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "ID"))
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User extends BaseEntity {

    @Column(name = "login_name")
    @JsonView(UserView.AllInfoLevel.class)
    private String loginName;

    /**
     * Type of user: 'S' - System User, 'U' - User
     */
    @Column(name = "user_type")
    @JsonView(UserView.AllInfoLevel.class)
    private String userType = "U";

    @Column(name = "firstname")
    @JsonView({UserView.ShortInfoLevel.class, UserView.UserLevel.class, GroupView.AllInfoLevel.class,
            EventView.AllInfoLevel.class, DepartmentView.AllInfoLevel.class})
    private String firstName;

    @Column(name = "lastname")
    @JsonView({UserView.ShortInfoLevel.class, UserView.UserLevel.class, GroupView.AllInfoLevel.class,
            EventView.AllInfoLevel.class, DepartmentView.AllInfoLevel.class})
    private String lastName;

    @Column(name = "email")
    @JsonView(UserView.MediumInfoLevel.class)
    private String email;

    @Column(name = "skype")
    @JsonView(UserView.MediumInfoLevel.class)
    private String skype;

    @Column(name = "invite_date")
    @JsonView(UserView.AllInfoLevel.class)
    private Date inviteDate = new Date();

    @Column(name = "password_can_expire_flag")
    @JsonView(UserView.RedundantInfoLevel.class)
    private Boolean passwordCanExpireFlag = true;

    @Column(name = "phone")
    @JsonView(UserView.MediumInfoLevel.class)
    private String phone;

    @Column(name = "url")
    @JsonView({UserView.MediumInfoLevel.class, UserView.UserLevel.class, GroupView.AllInfoLevel.class,
            EventView.AllInfoLevel.class})
    private String url;

    @Column(name = "level")
    @JsonView(UserView.AllInfoLevel.class)
    private String level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID")
    @JsonView(UserView.MediumInfoLevel.class)
    private Department department;

    // private Collection<LoginSession> loginSessions;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @JsonView(UserView.RedundantInfoLevel.class)
    private Collection<Relationship> relationships;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "UserRole",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
    @JsonView(UserView.AllInfoLevel.class)
    private Collection<Role> roles;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    @JsonView(UserView.AllInfoLevel.class)
    private Collection<Group> groups;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Event", joinColumns = @JoinColumn(name = "LEADER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ID"))
    @JsonView(UserView.RedundantInfoLevel.class)
    private Collection<Event> events;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @JsonView({UserView.AllInfoLevel.class, UserView.UserLevel.class})
    private Collection<DayBook> dayBooks;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @JsonView(UserView.RedundantInfoLevel.class)
    private Collection<Password> password;

    public User() {
    }

    public User(String loginName) {
        this.loginName = loginName;
        this.putDate();
    }

    public User(long id) {
        super.setId(id);
    }

    public User(Long id, String firstName, String lastName, String email) {
        super.setId(id);
        this.loginName = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.putDate();
    }

    public User(String firstName, String lastName, String email) {
        this.loginName = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_po_contact_seq")
    // @JsonView({UserView.ShortInfoLevel.class, GroupView.AllInfoLevel.class})
    public long getId() {
        return super.getId();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
        this.putDate();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
        this.putDate();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.putDate();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.putDate();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.putDate();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        this.putDate();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
        this.putDate();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
        this.putDate();
    }

    public Date getInviteDate() {
        return inviteDate;
    }

    public void setInviteDate(Date inviteDate) {
        this.inviteDate = inviteDate;
        this.putDate();
    }

    public Boolean getPasswordCanExpireFlag() {
        return passwordCanExpireFlag;
    }

    public void setPasswordCanExpireFlag(Boolean passwordCanExpireFlag) {
        this.passwordCanExpireFlag = passwordCanExpireFlag;
        this.putDate();
    }

    public Collection<Password> getPassword() {
        if (password == null)
            password = new HashSet<>();
        return password;
    }

    public void setPassword(Collection<Password> newPassword) {
        removeAllPassword();
        for (Iterator iter = newPassword.iterator(); iter.hasNext(); )
            addPassword((Password) iter.next());
    }

    @JsonIgnore
    public Iterator getIteratorPassword() {
        if (password == null)
            password = new HashSet<>();
        return password.iterator();
    }

    public void addPassword(Password newPassword) {
        if (newPassword == null)
            return;
        if (this.password == null)
            this.password = new HashSet<>();
        if (!this.password.contains(newPassword)) {
            this.password.add(newPassword);
            newPassword.setUser(this);
        }
    }

    public void removePassword(Password oldPassword) {
        if (oldPassword == null)
            return;
        if (this.password != null)
            if (this.password.contains(oldPassword)) {
                this.password.remove(oldPassword);
                oldPassword.setUser(null);
            }
    }

    public void removeAllPassword() {
        if (password != null) {
            Password oldPassword;
            for (Iterator iter = getIteratorPassword(); iter.hasNext(); ) {
                oldPassword = (Password) iter.next();
                iter.remove();
                oldPassword.setUser(null);
            }
        }
    }

    // public Collection<LoginSession> getLoginSessions() {
    // if (loginSessions == null)
    // loginSessions = new HashSet<>();
    // return loginSessions;
    // }
    //
    // public Iterator getIteratorLoginSession() {
    // if (loginSessions == null)
    // loginSessions = new HashSet<>();
    // return loginSessions.iterator();
    // }
    //
    // public void setLoginSessions(Collection<LoginSession> newLoginSession) {
    // removeAllLoginSession();
    // for (Iterator iter = newLoginSession.iterator(); iter.hasNext(); )
    // addLoginSession((LoginSession) iter.next());
    // }
    //
    // public void addLoginSession(LoginSession newLoginSession) {
    // if (newLoginSession == null)
    // return;
    // if (this.loginSessions == null)
    // this.loginSessions = new HashSet<>();
    // if (!this.loginSessions.contains(newLoginSession)) {
    // this.loginSessions.add(newLoginSession);
    // newLoginSession.setUser(this);
    // }
    // }
    //
    // public void removeLoginSession(LoginSession oldLoginSession) {
    // if (oldLoginSession == null)
    // return;
    // if (this.loginSessions != null)
    // if (this.loginSessions.contains(oldLoginSession)) {
    // this.loginSessions.remove(oldLoginSession);
    // oldLoginSession.setUser(null);
    // }
    // }
    //
    // public void removeAllLoginSession() {
    // if (loginSessions != null) {
    // LoginSession oldLoginSession;
    // for (Iterator iter = getIteratorLoginSession(); iter.hasNext(); ) {
    // oldLoginSession = (LoginSession) iter.next();
    // iter.remove();
    // oldLoginSession.setUser(null);
    // }
    // }
    // }

    public Collection<Role> getRoles() {
        if (roles == null)
            roles = new HashSet<>();
        return roles;
    }

    public void setRoles(Collection<Role> newRoles) {
        removeAllRoles();
        for (Iterator iter = newRoles.iterator(); iter.hasNext(); )
            addRole((Role) iter.next());
    }

    @JsonIgnore
    public Iterator getIteratorRoles() {
        if (roles == null)
            roles = new HashSet<>();
        return roles.iterator();
    }

    public void addRole(Role newRole) {
        if (newRole == null)
            return;
        if (this.roles == null)
            this.roles = new HashSet<>();
        if (!this.roles.contains(newRole)) {
            this.roles.add(newRole);
            // newRole.setUser((Collection<User>) this);
            newRole.addUser(this);
        }
    }

    public void removeRole(Role oldRole) {
        if (oldRole == null)
            return;
        if (this.roles != null)
            if (this.roles.contains(oldRole)) {
                this.roles.remove(oldRole);
//				oldRole.setUser(null);
            }
    }

    public void removeAllRoles() {
        if (roles != null) {
            Role oldRole;
            for (Iterator iter = getIteratorRoles(); iter.hasNext(); ) {
                oldRole = (Role) iter.next();
                iter.remove();
                oldRole.setUser(null);
            }
        }
    }

    public Collection<Relationship> getRelationships() {
        if (relationships == null)
            relationships = new HashSet<>();
        return relationships;
    }

    public void setRelationships(Collection<Relationship> newRelationship) {
        removeAllRelationship();
        for (Iterator iter = newRelationship.iterator(); iter.hasNext(); )
            addRelationship((Relationship) iter.next());
    }

    @JsonIgnore
    public Iterator getIteratorRelationship() {
        if (relationships == null)
            relationships = new HashSet<>();
        return relationships.iterator();
    }

    public void addRelationship(Relationship newRelationship) {
        if (newRelationship == null)
            return;
        if (this.relationships == null)
            this.relationships = new HashSet<>();
        if (!this.relationships.contains(newRelationship)) {
            this.relationships.add(newRelationship);
            newRelationship.setUser(this);
        }
    }

    public void removeRelationship(Relationship oldRelationship) {
        if (oldRelationship == null)
            return;
        if (this.relationships != null)
            if (this.relationships.contains(oldRelationship)) {
                this.relationships.remove(oldRelationship);
                oldRelationship.setUser(null);
            }
    }

    public void removeAllRelationship() {
        if (relationships != null) {
            Relationship oldRelationship;
            for (Iterator iter = getIteratorRelationship(); iter.hasNext(); ) {
                oldRelationship = (Relationship) iter.next();
                iter.remove();
                oldRelationship.setUser(null);
            }
        }
    }

    public Collection<Group> getGroups() {
        if (groups == null)
            groups = new HashSet<>();
        return groups;
    }

    public void setGroups(Collection<Group> newGroups) {
        removeAllGroup();
        for (Iterator iter = newGroups.iterator(); iter.hasNext(); )
            addGroup((Group) iter.next());
    }

    @JsonIgnore
    public Iterator getIteratorGroup() {
        if (groups == null)
            groups = new HashSet<>();
        return groups.iterator();
    }

    public void addGroup(Group newGroup) {
        if (newGroup == null)
            return;
        if (this.groups == null)
            this.groups = new HashSet<>();
        if (!this.groups.contains(newGroup)) {
            this.groups.add(newGroup);
            // newGroup.setUser((Collection<User>) this);
            newGroup.addUser(this);
        }
    }

    public void removeGroup(Group oldGroup) {
        if (oldGroup == null)
            return;
        if (this.groups != null)
            if (this.groups.contains(oldGroup)) {
                this.groups.remove(oldGroup);
                oldGroup.setUser(null);
            }
    }

    public void removeAllGroup() {
        if (groups != null) {
            Group oldGroup;
            for (Iterator iter = getIteratorGroup(); iter.hasNext(); ) {
                oldGroup = (Group) iter.next();
                iter.remove();
                oldGroup.setUser(null);
            }
        }
    }

    public Collection<Event> getEvents() {
        if (events == null)
            events = new HashSet<>();
        return events;
    }

    public void setEvents(Collection<Event> newEvent) {
        removeAllEvent();
        for (Iterator iter = newEvent.iterator(); iter.hasNext(); )
            addEvent((Event) iter.next());
    }

    @JsonIgnore
    public Iterator getIteratorEvent() {
        if (events == null)
            events = new HashSet<>();
        return events.iterator();
    }

    public void addEvent(Event newEvent) {
        if (newEvent == null)
            return;
        if (this.events == null)
            this.events = new HashSet<>();
        if (!this.events.contains(newEvent)) {
            this.events.add(newEvent);
            // newEvent.setUsers((Collection<User>) this);
            newEvent.addUser(this);
        }
    }

    public void removeEvent(Event oldEvent) {
        if (oldEvent == null)
            return;
        if (this.events != null)
            if (this.events.contains(oldEvent)) {
                this.events.remove(oldEvent);
                oldEvent.setUsers(null);
            }
    }

    public void removeAllEvent() {
        if (events != null) {
            Event oldEvent;
            for (Iterator iter = getIteratorEvent(); iter.hasNext(); ) {
                oldEvent = (Event) iter.next();
                iter.remove();
                oldEvent.setUsers(null);
            }
        }
    }

    public Collection<DayBook> getDayBooks() {
        if (dayBooks == null)
            dayBooks = new HashSet<>();
        return dayBooks;
    }

    public void setDayBooks(Collection<DayBook> newDayBook) {
        removeAllDayBook();
        for (Iterator iter = newDayBook.iterator(); iter.hasNext(); )
            addDayBook((DayBook) iter.next());
    }

    @JsonIgnore
    public Iterator getIteratorDayBook() {
        if (dayBooks == null)
            dayBooks = new HashSet<>();
        return dayBooks.iterator();
    }

    public void addDayBook(DayBook newDayBook) {
        if (newDayBook == null)
            return;
        if (this.dayBooks == null)
            this.dayBooks = new HashSet<>();
        if (!this.dayBooks.contains(newDayBook)) {
            this.dayBooks.add(newDayBook);
            // newDayBook.setUser(this);
        }
    }

    public void removeDayBook(DayBook oldDayBook) {
        if (oldDayBook == null)
            return;
        if (this.dayBooks != null)
            if (this.dayBooks.contains(oldDayBook)) {
                this.dayBooks.remove(oldDayBook);
                // oldDayBook.setUser(null);
            }
    }

    public void removeAllDayBook() {
        if (dayBooks != null) {
            DayBook oldDayBook;
            for (Iterator iter = getIteratorDayBook(); iter.hasNext(); ) {
                oldDayBook = (DayBook) iter.next();
                iter.remove();
                // oldDayBook.setUser(null);
            }
        }
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department newDepartment) {
        if (this.department == null || !this.department.equals(newDepartment)) {
            if (this.department != null) {
                Department oldDepartment = this.department;
                this.department = null;
                oldDepartment.removeUser(this);
            }
            if (newDepartment != null) {
                this.department = newDepartment;
                this.department.addUser(this);
            }
        }
    }

    public boolean hasPermission(String name, String object, String flag) {
        for (Role role : this.roles) {
            Collection<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                if (permission.getName().equals(name) && permission.getObject().equals(object) && permission.getAccessFlag().equals(flag)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasPermission(String object, String flag) {
        for (Role role : this.roles) {
            Collection<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                if (permission.getObject().equals(object) && permission.getAccessFlag().equals(flag)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasRole(String name) {
        for (Role role : this.getRoles()) {
            if (role.getName().equals(name)) return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        if (loginName != null ? !loginName.equals(user.loginName) : user.loginName != null)
            return false;
        if (userType != null ? !userType.equals(user.userType) : user.userType != null)
            return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null)
            return false;
        if (email != null ? !email.equals(user.email) : user.email != null)
            return false;
        if (skype != null ? !skype.equals(user.skype) : user.skype != null)
            return false;
        if (inviteDate != null ? !inviteDate.equals(user.inviteDate) : user.inviteDate != null)
            return false;
        if (passwordCanExpireFlag != null
                ? !passwordCanExpireFlag.equals(user.passwordCanExpireFlag)
                : user.passwordCanExpireFlag != null)
            return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null)
            return false;
        if (url != null ? !url.equals(user.url) : user.url != null)
            return false;
        if (department != null ? department.getId() == (user.department.getId())
                : user.department != null)
            return false;
        if (roles != null ? !roles.equals(user.roles) : user.roles != null)
            return false;
//		if (groups != null ? !groups.equals(user.groups) : user.groups != null)
//			return false;
        // if (relationships != null ? !relationships.equals(user.relationships)
        // : user.relationships != null)
        // return false;
        if (dayBooks != null ? dayBooks.equals(user.dayBooks) : user.dayBooks == null) return false;
        if (level != null ? level.equals(user.level) : user.level == null) return false;
        return events != null ? !events.equals(user.events) : user.events != null;

    }

    @Override
    public int hashCode() {
        int result = loginName != null ? loginName.hashCode() : 0;
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (skype != null ? skype.hashCode() : 0);
        result = 31 * result + (inviteDate != null ? inviteDate.hashCode() : 0);
        result = 31 * result
                + (passwordCanExpireFlag != null ? passwordCanExpireFlag.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (department != null
                ? (int) (department.getId() ^ (department.getId() >>> 32)) : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        //result = 31 * result + (groups != null ? groups.hashCode() : 0);
        result = 31 * result + (relationships != null ? relationships.hashCode() : 0);
        result = 31 * result + (events != null ? events.hashCode() : 0);
        result = 31 * result + (dayBooks != null ? dayBooks.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" + "loginName='" + loginName + '\'' + ", userType='" + userType + '\''
                + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
                + ", email='" + email + '\'' + ", skype='" + skype + '\'' + ", inviteDate="
                + inviteDate + ", passwordCanExpireFlag=" + passwordCanExpireFlag + ", phone='"
                + phone + '\'' + ", url='" + url + '\'' + ", department="
                + (department == null ? "null" : department.getId()) + ", roles=" + roles
                + /* ", groups=" + groups + */
                // ", relationships=" + relationships +
                ", events=" + events + ", dayBooks=" + dayBooks + ", level=" + level + '}';
    }
}
