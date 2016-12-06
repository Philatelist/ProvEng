package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.utils.view.RelationshipView;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Table(name = "relationship")
@SequenceGenerator(name = "relationship_seq", sequenceName = "relationship_seq", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "ID"))
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Relationship extends BaseEntity {

    @Column(name = "type")
    @JsonView(RelationshipView.ShortInfoLevel.class)
    public String type;

    @Column(name = "sys_status")
    @JsonView(RelationshipView.AllInfoLevel.class)
    public int sysStatus = 0;

    @ManyToOne
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    @JsonView(RelationshipView.AllInfoLevel.class)
    public User user;

    @Column(name = "user_id")
    @JsonView(RelationshipView.AllInfoLevel.class)
    private Long user_id;

    @Column(name = "related_person_id")
    @JsonView(RelationshipView.AllInfoLevel.class)
    private Long related_person_id;

    public Relationship() {
    }

    public Relationship(Long user_id, Long related_person_id, String type) {
        this.user_id = user_id;
        this.related_person_id = related_person_id;
        this.type = type;
    }

    @Override
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "relationship_seq")
    public long getId() {
        return super.getId();
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getRelated_person_id() {
        return related_person_id;
    }

    public void setRelated_person_id(Long related_person_id) {
        this.related_person_id = related_person_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int getSysStatus() {
        return sysStatus;
    }

    @Override
    public void setSysStatus(int sysStatus) {
        this.sysStatus = sysStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User newUser) {
        if (this.user == null || !this.user.equals(newUser)) {
            if (this.user != null) {
                User oldUser = this.user;
                this.user = null;
                oldUser.removeRelationship(this);
            }
            if (newUser != null) {
                this.user = newUser;
                this.user.addRelationship(this);
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Relationship that = (Relationship) o;

        if (sysStatus != that.sysStatus) return false;
        if (user_id != null ? !user_id.equals(that.user_id) : that.user_id != null) return false;
        if (related_person_id != null ? !related_person_id.equals(that.related_person_id) : that.related_person_id != null)
            return false;
        if (user != null ? user.getId() == (that.user.getId()) : that.user != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (related_person_id != null ? related_person_id.hashCode() : 0);
        result = 31 * result + (user_id != null ? user_id.hashCode() : 0);
        result = 31 * result + sysStatus;
//        result = 31 * result + (user != null ? (int) (user.getId() ^ (user.getId() >>> 32)) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "type='" + type + '\'' +
                ", user_id=" + user_id +
                ", related_person_id=" + related_person_id +
                ", sysStatus=" + sysStatus +
//                ", user=" + (user == null ? "null" : user.getId()) +
                '}';
    }
}
