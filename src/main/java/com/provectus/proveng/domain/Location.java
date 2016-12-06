package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.utils.view.EventView;
import com.provectus.proveng.utils.view.LocationView;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

@Entity
@Table(name = "location")
@SequenceGenerator(name = "location_seq", sequenceName = "location_seq", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "ID"))
//@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Location extends BaseEntity {

    @Column(name = "name")
    @JsonView(LocationView.ShortInfoLevel.class)
    private String name;

    @Column(name = "place")
    @JsonView({LocationView.ShortInfoLevel.class, EventView.ShortInfoLevel.class})
    private String place;

    @Column(name = "roominess")
    @JsonView({LocationView.ShortInfoLevel.class, EventView.ShortInfoLevel.class})
    private int roominess;

    @Column(name = "note")
    @JsonView(LocationView.MediumInfoLevel.class)
    private String note;

    @OneToMany
    @JoinColumn(name = "LOCATION_ID")
    @JsonView(LocationView.AllInfoLevel.class)
    private Collection<Event> events;

    public Location() {
    }

    public Location(long id) {
        super.setId(id);
    }

    public Location(Long id, String name, String place, int roominess) {
        super.setId(id);
        this.name = name;
        this.place = place;
        this.roominess = roominess;
    }

    public Location(String name, String place, int roominess) {
        this.name = name;
        this.place = place;
        this.roominess = roominess;
    }

    @Override
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "location_seq")
    public long getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getRoominess() {
        return roominess;
    }

    public void setRoominess(int roominess) {
        this.roominess = roominess;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Collection<Event> getEvents() {
        if (events == null)
            events = new HashSet<Event>();
        return events;
    }

    public void setEvents(Collection<Event> newEvent) {
        removeAllEvents();
        for (Iterator iter = newEvent.iterator(); iter.hasNext(); )
            addEvent((Event) iter.next());
    }

    @JsonIgnore
    public Iterator getIteratorEvent() {
        if (events == null)
            events = new HashSet<Event>();
        return events.iterator();
    }

    public void addEvent(Event newEvent) {
        if (newEvent == null)
            return;
        if (this.events == null)
            this.events = new HashSet<Event>();
        if (!this.events.contains(newEvent)) {
            this.events.add(newEvent);
            newEvent.setLocation(this);
        }
    }


    public void removeEvent(Event oldEvent) {
        if (oldEvent == null)
            return;
        if (this.events != null)
            if (this.events.contains(oldEvent)) {
                this.events.remove(oldEvent);
                oldEvent.setLocation(null);
            }
    }


    public void removeAllEvents() {
        if (events != null) {
            Event oldEvent;
            for (Iterator iter = getIteratorEvent(); iter.hasNext(); ) {
                oldEvent = (Event) iter.next();
                iter.remove();
                oldEvent.setLocation(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (roominess != location.roominess) return false;
        if (name != null ? !name.equals(location.name) : location.name != null) return false;
        if (place != null ? !place.equals(location.place) : location.place != null) return false;
//        if (events != null ? events.equals(location.events) : location.events == null) return false;
        return note != null ? !note.equals(location.note) : location.note != null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + roominess;
        result = 31 * result + (note != null ? note.hashCode() : 0);
//        result = 31 * result + (events != null ? events.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", roominess=" + roominess +
                ", note='" + note + '\'' +
//                ", events=" + events +
                '}';
    }
}
