package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.provectus.proveng.utils.serializer.UserShortInfoSerializer;
import com.provectus.proveng.utils.view.EventView;
import com.provectus.proveng.utils.view.GroupView;
import com.provectus.proveng.utils.view.UserView;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Departments of Client hierachical organization structure - linearizied list
 */
@Entity
@Table(name = "po_group")
@SequenceGenerator(name = "s_group_seq", sequenceName = "s_group_seq", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "ID"))
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
// property = "id")
public class Group extends BaseEntity {

    @Column(name = "name")
    @JsonView({GroupView.ShortInfoLevel.class, UserView.AllInfoLevel.class,
            EventView.ShortInfoLevel.class})
    private String name;

    @JsonView({GroupView.AllInfoLevel.class, UserView.AllInfoLevel.class})
    @Column(name = "primary_group_flag", updatable = false)
    private boolean primaryGroupFlag = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id")
    @JsonView({GroupView.AllInfoLevel.class})
    @JsonSerialize(using = UserShortInfoSerializer.class)
    private User leader;

    @Column(name = "level")
    @JsonView({GroupView.ShortInfoLevel.class, UserView.AllInfoLevel.class,
            EventView.ShortInfoLevel.class})
    private String level;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "UserGroup",
            joinColumns = @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
    //@JsonSerialize(using = CollectionOnlyIdSerializer.class)
    @JsonView(GroupView.AllInfoLevel.class)
    private Collection<User> members = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID", updatable = false)
    @JsonView(GroupView.RedundantInfoLevel.class)
    private Collection<Event> events;

    public Group() {
    }

    public Group(User leader, String name) {
        this.leader = leader;
        this.name = name;
    }

    public Group(User leader, String name, String level) {
        this.leader = leader;
        this.name = name;
        this.level = level;
    }

    @Override
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "s_group_seq")
    public long getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Collection<User> getMembers() {
//		if (members == null)
//			members = new HashSet<User>();
        return members;
    }

    public void setMembers(Collection<User> members) {
        this.members = members;
    }

    public void setUser(Collection<User> newUser) {
        removeAllUser();
        for (Iterator<User> iter = newUser.iterator(); iter.hasNext(); )
            addUser(iter.next());
    }

    @JsonIgnore
    public Iterator<User> getIteratorUser() {
        if (members == null)
            members = new HashSet<User>();
        return members.iterator();
    }

    public void addUser(User newUser) {
        if (newUser == null)
            return;
        if (this.members == null)
            this.members = new HashSet<User>();
        if (!this.members.contains(newUser)) {
            this.members.add(newUser);
            // newUser.setGroups((Collection<Groups>) this);
            // newUser.addGroup(this);
        }
    }

    public void removeUser(User oldUser) {
        if (oldUser == null)
            return;
        if (this.members != null)
            if (this.members.contains(oldUser)) {
                this.members.remove(oldUser);
                oldUser.setGroups(null);
            }
    }

    public void removeAllUser() {
        if (members != null) {
            User oldUser;
            for (Iterator<User> iter = getIteratorUser(); iter.hasNext(); ) {
                oldUser = iter.next();
                iter.remove();
                oldUser.setGroups(null);
            }
        }
    }

    public Collection<Event> getEvents() {
        if (this.events == null)
            events = new HashSet<Event>();
        return events;
    }

    public void setEvent(Collection<Event> newEvent) {
        removeAllEvent();
        for (Iterator<Event> iter = newEvent.iterator(); iter.hasNext(); )
            addEvent(iter.next());
    }

    @JsonIgnore
    public Iterator<Event> getIteratorEvent() {
        if (events == null)
            events = new HashSet<Event>();
        return events.iterator();
    }

    public Boolean getPrimaryGroupFlag() {
        return primaryGroupFlag;
    }

    public void setPrimaryGroupFlag(Boolean primaryGroupFlag) {
        this.primaryGroupFlag = primaryGroupFlag;
    }

    public void addEvent(Event newEvent) {
        if (newEvent == null)
            return;
        if (this.events == null)
            this.events = new HashSet<Event>();
        if (!this.events.contains(newEvent)) {
            this.events.add(newEvent);
            newEvent.setGroup(this);
        }
    }

    public void removeEvent(Event oldEvent) {
        if (oldEvent == null)
            return;
        if (this.events != null)
            if (this.events.contains(oldEvent)) {
                this.events.remove(oldEvent);
                oldEvent.setGroup(null);
            }
    }

    public void removeAllEvent() {
        if (events != null) {
            Event oldEvent;
            for (Iterator<Event> iter = getIteratorEvent(); iter.hasNext(); ) {
                oldEvent = iter.next();
                iter.remove();
                oldEvent.setGroup(null);
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        // result = prime * result + ((events == null) ? 0 : events.hashCode());
        result = prime * result + ((leader == null) ? 0 : leader.hashCode());
        result = prime * result + ((level == null) ? 0 : level.hashCode());
        result = prime * result + ((members == null) ? 0 : members.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Group other = (Group) obj;
        // if (events == null) {
        // if (other.events != null)
        // return false;
        // } else if (!events.equals(other.events))
        // return false;
        if (leader == null) {
            if (other.leader != null)
                return false;
        } else if (!leader.equals(other.leader))
            return false;
        if (level == null) {
            if (other.level != null)
                return false;
        } else if (!level.equals(other.level))
            return false;
        if (members == null) {
            if (other.members != null)
                return false;
        } else if (!members.equals(other.members))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Group{" + "name='" + name + '\'' + ", leader=" + leader + ", level='" + level + '\''
                +
                // ", members=" + users +
                // ", events=" + events +
                '}';
    }

    public boolean isMainGroup() {
        return (this.getPrimaryGroupFlag() == true);
    }
}
