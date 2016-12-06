package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.utils.view.RoleView;
import com.provectus.proveng.utils.view.UserView;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

@Entity
@Table(name = "role")
@SequenceGenerator(name = "s_roles_seq", sequenceName = "s_roles_seq", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "ID"))
//@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Role extends BaseEntity {

    @Column(name = "name")
    @JsonView({RoleView.ShortInfoLevel.class, UserView.MediumInfoLevel.class})
    private String name;

    @OneToMany
    @JoinColumn(name = "ROLE_ID")
    @JsonView({RoleView.AllInfoLevel.class, UserView.RedundantInfoLevel.class})
    private Collection<Permission> permissions;

    @ManyToMany(mappedBy = "roles")
    @JsonView(RoleView.AllInfoLevel.class)
    private Collection<User> users;

    public Role() {
    }

    public Role(long id) {
        super.setId(id);
        this.putDate();
    }

    public Role(String name) {
        this.name = name;
        this.putDate();
    }

    public Role(int sysStatus) {
        super.setSysStatus(sysStatus);
        this.putDate();
    }

    @Override
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "s_roles_seq")
    public long getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<User> getUser() {
        if (users == null)
            users = new HashSet<User>();
        return users;
    }

    public void setUser(Collection<User> newUser) {
        removeAllUser();
        for (Iterator iter = newUser.iterator(); iter.hasNext(); )
            addUser((User) iter.next());
    }

    @JsonIgnore
    public Iterator getIteratorUser() {
        if (users == null)
            users = new HashSet<User>();
        return users.iterator();
    }

    public void addUser(User newUser) {
        if (newUser == null)
            return;
        if (this.users == null)
            this.users = new HashSet<User>();
        if (!this.users.contains(newUser)) {
            this.users.add(newUser);
//            newUser.setRoles((Collection<Role>) this);
            newUser.addRole(this);
        }
    }

    public void removeUser(User oldUsers) {
        if (oldUsers == null)
            return;
        if (this.users != null)
            if (this.users.contains(oldUsers)) {
                this.users.remove(oldUsers);
                oldUsers.setRoles(null);
            }
    }

    public void removeAllUser() {
        if (users != null) {
            User oldUsers;
            for (Iterator iter = getIteratorUser(); iter.hasNext(); ) {
                oldUsers = (User) iter.next();
                iter.remove();
                oldUsers.setRoles(null);
            }
        }
    }

    public Collection<Permission> getPermissions() {
        if (permissions == null)
            permissions = new HashSet<Permission>();
        return this.permissions;
    }

    @JsonIgnore
    public Iterator getIteratorPermission() {
        if (permissions == null)
            permissions = new HashSet<Permission>();
        return permissions.iterator();
    }


    public void setPermission(Collection<Permission> newPermissions) {
        removeAllPermission();
        for (Iterator iter = newPermissions.iterator(); iter.hasNext(); )
            addPermission((Permission) iter.next());
    }

    public void addPermission(Permission newPermission) {
        if (newPermission == null)
            return;
        if (this.permissions == null)
            this.permissions = new HashSet<Permission>();
        if (!this.permissions.contains(newPermission)) {
            this.permissions.add(newPermission);
            newPermission.setRole(this);
        }
    }

    public void removePermission(Permission oldPermission) {
        if (oldPermission == null)
            return;
        if (this.permissions != null)
            if (this.permissions.contains(oldPermission)) {
                this.permissions.remove(oldPermission);
                oldPermission.setRole(null);
            }
    }

    public void removeAllPermission() {
        if (permissions != null) {
            Permission oldPermission;
            for (Iterator iter = getIteratorPermission(); iter.hasNext(); ) {
                oldPermission = (Permission) iter.next();
                iter.remove();
                oldPermission.setRole(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (name != null ? !name.equals(role.name) : role.name != null) return false;
        if (permissions != null ? !permissions.equals(role.permissions) : role.permissions != null) return false;
        return users != null ? users.equals(role.users) : role.users == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (permissions != null ? permissions.hashCode() : 0);
//        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", permissions=" + permissions +
//                ", users=" + users +
                '}';
    }
}
