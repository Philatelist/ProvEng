package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.utils.view.PermissionView;
import com.provectus.proveng.utils.view.UserView;

import javax.persistence.*;


@Entity
@Table(name = "permission")
@SequenceGenerator(name = "s_permission_seq", sequenceName = "s_permission_seq", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "ID"))
//@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Permission extends BaseEntity {

    @Column(name = "type")
    @JsonView(PermissionView.ShortInfoLevel.class)
    private int type = 0; // static(0)/dynamic(1)

    @Column(name = "name")
    @JsonView({PermissionView.ShortInfoLevel.class, UserView.MediumInfoLevel.class})
    private String name;

    @Column(name = "access_flag")
    @JsonView({PermissionView.AllInfoLevel.class, UserView.MediumInfoLevel.class})
    private String accessFlag; //  R/W/C/D (means Read, Write, Create, Delete)

    @Column(name = "object")
    @JsonView({PermissionView.AllInfoLevel.class, UserView.AllInfoLevel.class})
    private String object;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", insertable = false, updatable = false)
    @JsonView(PermissionView.AllInfoLevel.class)
    @JsonBackReference
    private Role role;

    public Permission() {
    }

    public Permission(String name, String accessFlag, String object) {
        this.name = name;
        this.accessFlag = accessFlag;
        this.object = object;
    }

    public Permission(String name, String accessFlag) {
        this.name = name;
        this.accessFlag = accessFlag;
    }

    @Override
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "s_permission_seq")
    public long getId() {
        return super.getId();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessFlag() {
        return accessFlag;
    }

    public void setAccessFlag(String accessFlag) {
        this.accessFlag = accessFlag;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role newRole) {
        if (this.role == null || !this.role.equals(newRole)) {
            if (this.role != null) {
                Role oldRole = this.role;
                this.role = null;
                oldRole.removePermission(this);
            }
            if (newRole != null) {
                this.role = newRole;
                this.role.addPermission(this);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        if (type != that.type) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (accessFlag != null ? !accessFlag.equals(that.accessFlag) : that.accessFlag != null)
            return false;
        if (object != null ? !object.equals(that.object) : that.object != null) return false;
        return role != null ? role.equals(that.role) : that.role == null;

    }

    @Override
    public int hashCode() {
        int result = type;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (accessFlag != null ? accessFlag.hashCode() : 0);
        result = 31 * result + (object != null ? object.hashCode() : 0);
//        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", accessFlag='" + accessFlag + '\'' +
                ", object='" + object + '\'' +
//                ", role=" + role +
                '}';
    }
}
