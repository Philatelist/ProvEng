package com.provectus.proveng.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.provectus.proveng.utils.serializer.LevelNumberToStringSerializer;
import com.provectus.proveng.utils.view.EventView;
import com.provectus.proveng.utils.view.MaterialView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Material")
@SequenceGenerator(name = "S_MATERIAL_SEQ", sequenceName = "S_MATERIAL_SEQ", allocationSize = 1)
public class Material extends BaseEntity implements Eventable {

    @JsonView({MaterialView.ShortInfoLevel.class, EventView.RelationLevel.class})
    @Column(name = "NAME")
    private String name;

    @JsonView(MaterialView.ShortInfoLevel.class)
    @JsonSerialize(using = LevelNumberToStringSerializer.class)
    @Column(name = "MIN_LEVEL")
    private int minLevel;

    @JsonView(MaterialView.AllInfoLevel.class)
    @Column(name = "DESCRIPTION")
    private String description;

    @JsonView({MaterialView.AllInfoLevel.class, EventView.RelationLevel.class})
    @Column(name = "LINK")
    private String link;

    @JsonView({MaterialView.ShortInfoLevel.class, EventView.RelationLevel.class})
    @Column(name = "TYPE")
    private String type;

    public Material() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Material material = (Material) o;

        if (minLevel != material.minLevel) return false;
        if (name != null ? !name.equals(material.name) : material.name != null) return false;
        if (description != null ? !description.equals(material.description) : material.description != null)
            return false;
        if (link != null ? !link.equals(material.link) : material.link != null) return false;
        return type != null ? type.equals(material.type) : material.type == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + minLevel;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Material [name=" + name + ", minLevel=" + minLevel + ", description=" + description
                + ", link=" + link + ", type=" + type + "]";
    }


}
