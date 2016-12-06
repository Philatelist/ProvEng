package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.provectus.proveng.utils.serializer.EntityOnlyIdSerializer;
import com.provectus.proveng.utils.serializer.LevelNumberToStringSerializer;
import com.provectus.proveng.utils.view.DayBookView;
import com.provectus.proveng.utils.view.EventView;
import com.provectus.proveng.utils.view.TestView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Test")
@SequenceGenerator(name = "S_TEST_SEQ", sequenceName = "S_TEST_SEQ", allocationSize = 1)
public class Test extends BaseEntity implements Eventable {

    @JsonView(TestView.AllInfoLevel.class)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "TEST_ID", referencedColumnName = "ID")
    @OrderColumn(name = "ARRAY_INDEX")
    List<TestCard> testCards = new ArrayList<>();

    @JsonView({TestView.ShortInfoLevel.class, EventView.RelationLevel.class})
    @Column(name = "NAME")
    private String name;

    @Column(name = "VERSION")
    private int version;

    @JsonView({TestView.ShortInfoLevel.class, EventView.RelationLevel.class})
    @Column(name = "TYPE")
    private String type;

    @JsonView({TestView.ShortInfoLevel.class, EventView.RelationLevel.class})
    @Column(name = "DURATION")
    private long duration;

    @JsonView({TestView.ShortInfoLevel.class, DayBookView.TestResultLevel.class,
            EventView.RelationLevel.class})
    @Column(name = "WEIGHT")
    private int weight;

    // @JsonView(TestView.ShortInfoLevel.class)
    @Column(name = "PASS_MARK")
    private int passMark;

    // @JsonView(TestView.ShortInfoLevel.class)
    @Column(name = "ALLOWED_ATTEMPTS")
    private int allowedAttempts;

    @JsonView(TestView.ShortInfoLevel.class)
    @JsonSerialize(using = LevelNumberToStringSerializer.class)
    @Column(name = "MIN_LEVEL")
    private int minLevel;

    @JsonSerialize(using = EntityOnlyIdSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
    private Test parent;

    @Column(name = "IGNORE_ORDER")
    private boolean ignoreOrder;

    public Test() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPassMark() {
        return passMark;
    }

    public void setPassMark(int passMark) {
        this.passMark = passMark;
    }

    public int getAllowedAttempts() {
        return allowedAttempts;
    }

    public void setAllowedAttempts(int allowedAttempts) {
        this.allowedAttempts = allowedAttempts;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public List<TestCard> getTestCards() {
        return testCards;
    }

    public void setTestCards(List<TestCard> testCards) {
        this.testCards = testCards;
    }

    public Test getParent() {
        return parent;
    }

    public void setParent(Test parent) {
        this.parent = parent;
    }

    public boolean isIgnoreOrder() {
        return ignoreOrder;
    }

    public void setIgnoreOrder(boolean ignoreOrder) {
        this.ignoreOrder = ignoreOrder;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + allowedAttempts;
        result = prime * result + (int) (duration ^ (duration >>> 32));
        result = prime * result + (ignoreOrder ? 1231 : 1237);
        result = prime * result + minLevel;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        result = prime * result + passMark;
        result = prime * result + ((testCards == null) ? 0 : testCards.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + version;
        result = prime * result + weight;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Test other = (Test) obj;
        if (allowedAttempts != other.allowedAttempts)
            return false;
        if (duration != other.duration)
            return false;
        if (ignoreOrder != other.ignoreOrder)
            return false;
        if (minLevel != other.minLevel)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (parent == null) {
            if (other.parent != null)
                return false;
        } else if (!parent.equals(other.parent))
            return false;
        if (passMark != other.passMark)
            return false;
        if (testCards == null) {
            if (other.testCards != null)
                return false;
        } else if (!testCards.equals(other.testCards))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (version != other.version)
            return false;
        return weight == other.weight;
    }

    @Override
    public String toString() {
        return "Test [name=" + name + ", version=" + version + ", type=" + type + ", duration="
                + duration + ", weight=" + weight + ", passMark=" + passMark + ", allowedAttempts="
                + allowedAttempts + ", minLevel=" + minLevel + ", testCards=" + testCards
                + ", parent=" + parent + ", ignoreOrder=" + ignoreOrder + "]";
    }

}
