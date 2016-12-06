package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.*;
import com.provectus.proveng.utils.view.EventView;
import com.provectus.proveng.utils.view.GroupView;
import com.provectus.proveng.utils.view.UserView;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

@Entity
@Table(name = "event")
@SequenceGenerator(name = "event_seq", sequenceName = "event_seq", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "ID"))
public class Event extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "leader_id")
    @JsonView({EventView.MediumInfoLevel.class, EventView.ScheduleInfoLevel.class})
    @JsonIdentityReference(alwaysAsId = true)/* - work*/
    private User leader;

    @ManyToOne
    @JoinColumn(name = "creater_id")
    @JsonView(EventView.ShortInfoLevel.class)
    private User creater;

    @Column(name = "name")
    @JsonView({EventView.ShortInfoLevel.class, GroupView.AllInfoLevel.class, UserView.AllInfoLevel.class})
    private String name;

    @Column(name = "type")
    @JsonView({EventView.ShortInfoLevel.class, UserView.AllInfoLevel.class})
    private String type;

    @Column(name = "date_start")
    @JsonView({EventView.ShortInfoLevel.class, UserView.AllInfoLevel.class})
    private Date dateStart = new Date();

    @Column(name = "regular")
    @JsonView(EventView.MediumInfoLevel.class)
    private String regular;

    @Column(name = "date_end")
    @JsonView(EventView.ShortInfoLevel.class)
    private Date dateEnd;

    @Column(name = "note")
    @JsonView(EventView.ShortInfoLevel.class)
    private String note;

    @OneToMany
    @JoinColumn(name = "SUPEREVENT_ID", updatable = false)
    @JsonIdentityReference(alwaysAsId = true)/* - work*/
//    @JsonProperty(value = "id")
//    @JsonSerialize(using = CollectionOnlyIdSerializer.class)
//    @JsonIgnoreProperties({"childEvents"})
    @JsonView(EventView.MediumInfoLevel.class)
//    @JsonBackReference
//    @JsonManagedReference
    private Collection<Event> childEvents;

    @OneToMany
    @JoinColumn(name = "EVENT_ID")
    @JsonView(EventView.AllInfoLevel.class)
    private Collection<DayBook> dayBook;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID", updatable = false)
    @JsonView(EventView.CalendarInfoLevel.class)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "LOCATION_ID")
    @JsonView(EventView.CalendarInfoLevel.class)
    private Location location;

    @ManyToMany(mappedBy = "events")
    @JsonView(EventView.AllInfoLevel.class)
    private Collection<User> users;

    @ManyToMany(mappedBy = "events")
    private Collection<User> queue;

    @ManyToOne
    @JoinColumn(name = "SUPEREVENT_ID", updatable = false)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonView(EventView.MediumInfoLevel.class)
    @JsonBackReference
    private Event parentEvent;

    @Any(metaColumn = @Column(name = "ITEM_NAME"), fetch = FetchType.EAGER)
    @AnyMetaDef(idType = "long", metaType = "string", metaValues = {
            @MetaValue(targetEntity = Test.class, value = "TEST"),
            @MetaValue(targetEntity = Material.class, value = "MATERIAL")
    })
    @JoinColumn(name = "ITEM_ID")
    @JsonView(EventView.RelationLevel.class)
    private Eventable eventableItem;

    public Event() {
    }

    public Event(User leader, String name, String type) {
        this.leader = leader;
        this.name = name;
        this.type = type;
    }

    public Event(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Event(Long id, User leader, User creater, Long group_id,
                 String name, String type, Date dateStart,
                 String regular, Date dateEnd, String note) {
        super.setId(id);
        this.leader = leader;
        this.creater = creater;
//        this.group_id = group_id;
        this.name = name;
        this.type = type;
        this.dateStart = dateStart;
        this.regular = regular;
        this.dateEnd = dateEnd;
        this.note = note;
    }

    public Event(User leader,
                 String name, String type, Date dateStart,
                 String regular, Date dateEnd, String note) {
        this.leader = leader;
        this.name = name;
        this.type = type;
        this.dateStart = dateStart;
        this.regular = regular;
        this.dateEnd = dateEnd;
        this.note = note;
    }

    public Event(String name, User leader, User creater, String type, String note, Event parentEvent) {
        this.name = name;
        this.creater = creater;
        this.leader = leader;
        this.type = type;
        this.note = note;
        this.parentEvent = parentEvent;
    }

    public Event(User leader, String type, Date dateStart, String regular, Date dateEnd, Group group, Location location, Event parentEvent) {
        this.leader = leader;
        this.type = type;
        this.dateStart = dateStart;
        this.regular = regular;
        this.dateEnd = dateEnd;
        this.group = group;
        this.location = location;
        this.parentEvent = parentEvent;
    }

    public Eventable getEventableItem() {
        return eventableItem;
    }

    public void setEventableItem(Eventable eventableItem) {
        this.eventableItem = eventableItem;
    }

    public Collection<User> getQueue() {
        return queue;
    }

    public void setQueue(Collection<User> queue) {
        this.queue = queue;
    }

    @Override
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "event_seq")
    @JsonProperty("id")
    public long getId() {
        return super.getId();
    }

    @JsonView(EventView.AllInfoLevel.class)
    public int getSysStatus() {
        return super.getSysStatus();
    }


    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public User getCreater() {
        return creater;
    }

    public void setCreater(User creater) {
        this.creater = creater;
//        if (this.creater == null || !this.creater.equals(newCreater)) {
//            if (this.creater != null) {
//                User oldCreater = this.creater;
//                this.creater = null;
//                oldCreater.removeEvent(this);
//            }
//            if (newCreater != null) {
//                this.creater = newCreater;
//                this.creater.addEvent(this);
//            }
//        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Collection<Event> getChildEvents() {
        if (childEvents == null)
            childEvents = new HashSet<>();
        return childEvents;
    }

    public void setChildEvents(Collection<Event> newEventB) {
        removeAllEventChilds();
        for (Iterator iter = newEventB.iterator(); iter.hasNext(); )
            addEventChild((Event) iter.next());
    }

    @JsonIgnore
    public Iterator getIteratorEventChilds() {
        if (childEvents == null)
            childEvents = new HashSet<>();
        return childEvents.iterator();
    }

    public void addEventChild(Event newEvent) {
        if (newEvent == null)
            return;
        if (this.childEvents == null)
            this.childEvents = new HashSet<>();
        if (!this.childEvents.contains(newEvent)) {
            this.childEvents.add(newEvent);
            newEvent.setParentEvent(this);
        }
    }


    public void removeEventChild(Event oldEvent) {
        if (oldEvent == null)
            return;
        if (this.childEvents != null)
            if (this.childEvents.contains(oldEvent)) {
                this.childEvents.remove(oldEvent);
                oldEvent.setParentEvent(null);
            }
    }


    public void removeAllEventChilds() {
        if (childEvents != null) {
            Event oldEvent;
            for (Iterator iter = getIteratorEventChilds(); iter.hasNext(); ) {
                oldEvent = (Event) iter.next();
                iter.remove();
                oldEvent.setParentEvent(null);
            }
        }
    }

    public Collection<User> getUsers() {
        if (users == null)
            users = new HashSet<>();
        return users;
    }

    public void setUsers(Collection<User> newUser) {
        removeAllUser();
        for (Iterator iter = newUser.iterator(); iter.hasNext(); )
            addUser((User) iter.next());
    }

    @JsonIgnore
    public Iterator getIteratorUser() {
        if (users == null)
            users = new HashSet<>();
        return users.iterator();
    }

    public void addUser(User newUser) {
        if (newUser == null)
            return;
        if (this.users == null)
            this.users = new HashSet<>();
        if (!this.users.contains(newUser)) {
            this.users.add(newUser);
            newUser.setEvents((Collection<Event>) this);
        }
    }

    public void removeUser(User oldUser) {
        if (oldUser == null)
            return;
        if (this.users != null)
            if (this.users.contains(oldUser)) {
                this.users.remove(oldUser);
                oldUser.setEvents(null);
            }
    }

    public void removeAllUser() {
        if (users != null) {
            User oldUser;
            for (Iterator iter = getIteratorUser(); iter.hasNext(); ) {
                oldUser = (User) iter.next();
                iter.remove();
                oldUser.setEvents(null);
            }
        }
    }

    public Collection<DayBook> getDayBook() {
        if (dayBook == null)
            dayBook = new HashSet<>();
        return dayBook;
    }

    public void setDayBook(Collection<DayBook> newDayBook) {
        removeAllDayBook();
        for (Iterator iter = newDayBook.iterator(); iter.hasNext(); )
            addDayBook((DayBook) iter.next());
    }

    @JsonIgnore
    public Iterator getIteratorDayBook() {
        if (dayBook == null)
            dayBook = new HashSet<>();
        return dayBook.iterator();
    }

    public void addDayBook(DayBook newDayBook) {
        if (newDayBook == null)
            return;
        if (this.dayBook == null)
            this.dayBook = new HashSet<>();
        if (!this.dayBook.contains(newDayBook)) {
            this.dayBook.add(newDayBook);
            newDayBook.setEvent(this);
        }
    }


    public void removeDayBook(DayBook oldDayBook) {
        if (oldDayBook == null)
            return;
        if (this.dayBook != null)
            if (this.dayBook.contains(oldDayBook)) {
                this.dayBook.remove(oldDayBook);
                oldDayBook.setEvent(null);
            }
    }


    public void removeAllDayBook() {
        if (dayBook != null) {
            DayBook oldDayBook;
            for (Iterator iter = getIteratorDayBook(); iter.hasNext(); ) {
                oldDayBook = (DayBook) iter.next();
                iter.remove();
                oldDayBook.setEvent(null);
            }
        }
    }

    public Group getGroup() {
        return group;
    }


    public void setGroup(Group newGroup) {
        if (this.group == null || !this.group.equals(newGroup)) {
            if (this.group != null) {
                Group oldGroup = this.group;
                this.group = null;
                oldGroup.removeEvent(this);
            }
            if (newGroup != null) {
                this.group = newGroup;
                this.group.addEvent(this);
            }
        }
    }

    public Location getLocation() {
        return location;
    }


    public void setLocation(Location newLocation) {
        if (this.location == null || !this.location.equals(newLocation)) {
            if (this.location != null) {
                Location oldLocation = this.location;
                this.location = null;
                oldLocation.removeEvent(this);
            }
            if (newLocation != null) {
                this.location = newLocation;
                this.location.addEvent(this);
            }
        }
    }

    public Event getParentEvent() {
        return parentEvent;
    }


    public void setParentEvent(Event newEvent) {
        if (this.parentEvent == null || !this.parentEvent.equals(newEvent)) {
            if (this.parentEvent != null) {
                Event oldEvent = this.parentEvent;
                this.parentEvent = null;
                oldEvent.removeEventChild(this);
            }
            if (newEvent != null) {
                this.parentEvent = newEvent;
                this.parentEvent.addEventChild(this);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (leader != null ? !leader.equals(event.leader) : event.leader != null) return false;
        if (creater != null ? !creater.equals(event.creater) : event.creater != null) return false;
        if (name != null ? !name.equals(event.name) : event.name != null) return false;
        if (type != null ? !type.equals(event.type) : event.type != null) return false;
        if (dateStart != null ? !dateStart.equals(event.dateStart) : event.dateStart != null) return false;
        if (regular != null ? !regular.equals(event.regular) : event.regular != null) return false;
        if (dateEnd != null ? !dateEnd.equals(event.dateEnd) : event.dateEnd != null) return false;
        if (note != null ? !note.equals(event.note) : event.note != null) return false;
        if (childEvents != null ? !childEvents.equals(event.childEvents) : event.childEvents != null) return false;
        if (dayBook != null ? !dayBook.equals(event.dayBook) : event.dayBook != null) return false;
        if (group != null ? !group.equals(event.group) : event.group != null) return false;
        if (location != null ? location.getId() == (event.location.getId()) : event.location != null) return false;
        if (users != null ? !users.equals(event.users) : event.users != null) return false;
        return parentEvent != null ? parentEvent.equals(event.parentEvent) : event.parentEvent == null;

    }

    @Override
    public int hashCode() {
        int result = leader != null ? leader.hashCode() : 0;
        result = 31 * result + (creater != null ? (int) (creater.getId() ^ (creater.getId() >>> 32)) : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (dateStart != null ? dateStart.hashCode() : 0);
        result = 31 * result + (regular != null ? regular.hashCode() : 0);
        result = 31 * result + (dateEnd != null ? dateEnd.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (childEvents != null ? childEvents.hashCode() : 0);
        result = 31 * result + (dayBook != null ? dayBook.hashCode() : 0);
        result = 31 * result + (group != null ? (int) (group.getId() ^ (group.getId() >>> 32)) : 0);
        result = 31 * result + (location != null ? (int) (location.getId() ^ (location.getId() >>> 32)) : 0);
//        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (parentEvent != null ? (int) (parentEvent.getId() ^ (parentEvent.getId() >>> 32)) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "leader=" + (leader == null ? "null" : leader.getId()) +
                ", creater=" + (creater == null ? "null" : creater.getId()) +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", dateStart=" + dateStart +
                ", regular='" + regular + '\'' +
                ", dateEnd=" + dateEnd +
                ", note='" + note + '\'' +
                ", childEvents=" + childEvents +
                ", dayBook=" + dayBook +
                ", group=" + (group == null ? "null" : group.getId()) +
                ", location=" + (location == null ? "null" : location.getId()) +
//                ", users=" + users +
                ", parentEvent=" + (parentEvent == null ? "null" : parentEvent.getId()) +
                '}';
    }
}
