package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.provectus.proveng.utils.view.DepartmentView;
import com.provectus.proveng.utils.view.UserView;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

@Entity
@Table(name = "department")
@SequenceGenerator(name = "s_address_seq", sequenceName = "s_address_seq", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "ID"))
//@JsonIdentityInfo(generator = JSOGGenerator.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Department extends BaseEntity {

    @Column(name = "name")
    @JsonView({DepartmentView.ShortInfoLevel.class, UserView.MediumInfoLevel.class})
    private String name;

    @Column(name = "url")
    @JsonView({DepartmentView.ShortInfoLevel.class, UserView.MediumInfoLevel.class})
    private String url;

    @Column(name = "description")
    @JsonView({DepartmentView.MediumInfoLevel.class, UserView.MediumInfoLevel.class})
    private String description;

    @OneToMany
    @JoinColumn(name = "DEPARTMENT_ID")
//    @JsonSerialize(using = CollectionOnlyIdSerializer2.class)
//    @JsonIdentityReference(alwaysAsId = true)
    @JsonView(DepartmentView.AllInfoLevel.class)
    private Collection<User> users;

    public Department() {
    }

    public Department(String name, String url, String description) {
        this.name = name;
        this.url = url;
        this.description = description;
    }

    @Override
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "s_address_seq")
    public long getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<User> getUsers() {
        if (users == null)
            users = new HashSet<User>();
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @JsonIgnore
    public Iterator<User> getIteratorUser() {
        if (users == null)
            users = new HashSet<User>();
        return users.iterator();
    }


    public void setUser(Collection<User> newUser) {
        removeAllUser();
        for (Iterator<User> iter = newUser.iterator(); iter.hasNext(); )
            addUser(iter.next());
    }


    public void addUser(User newUser) {
        if (newUser == null)
            return;
        if (this.users == null)
            this.users = new HashSet<User>();
        if (!this.users.contains(newUser)) {
            this.users.add(newUser);
            newUser.setDepartment(this);
        }
    }


    public void removeUser(User oldUser) {
        if (oldUser == null)
            return;
        if (this.users != null)
            if (this.users.contains(oldUser)) {
                this.users.remove(oldUser);
                oldUser.setDepartment(null);
            }
    }


    public void removeAllUser() {
        if (users != null) {
            User oldUser;
            for (Iterator<User> iter = getIteratorUser(); iter.hasNext(); ) {
                oldUser = iter.next();
                iter.remove();
                oldUser.setDepartment(null);
            }
        }
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Department that = (Department) o;
//
//        if (id != that.id) return false;
//        if (sysStatus != that.sysStatus) return false;
//        if (name != null ? !name.equals(that.name) : that.name != null) return false;
//        if (url != null ? !url.equals(that.url) : that.url != null) return false;
//        if (description != null ? !description.equals(that.description) : that.description != null) return false;
//        if (createDtm != null ? !createDtm.equals(that.createDtm) : that.createDtm != null) return false;
//        if (users != null ? users.equals(that.users) : that.users != null) return false;
//        return modifyDtm != null ? !modifyDtm.equals(that.modifyDtm) : that.modifyDtm == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = (int) (id ^ (id >>> 32));
//        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + (url != null ? url.hashCode() : 0);
//        result = 31 * result + (description != null ? description.hashCode() : 0);
//        result = 31 * result + sysStatus;
//        result = 31 * result + (createDtm != null ? createDtm.hashCode() : 0);
//        result = 31 * result + (modifyDtm != null ? modifyDtm.hashCode() : 0);
//        result = 31 * result + (users != null ? users.hashCode() : 0);
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return "Department{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", url='" + url + '\'' +
//                ", description='" + description + '\'' +
//                ", sysStatus=" + sysStatus +
//                ", createDtm=" + createDtm +
//                ", modifyDtm=" + modifyDtm +
//                ", users=" + users +
//                '}';
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return users != null ? users.equals(that.users) : that.users == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", users=" + users +
                '}';
    }
}
