package com.provectus.proveng.domain.dto;

import com.provectus.proveng.domain.Group;

public class GroupShortDto {
    private Long id;
    private String name;
    private String level;
    private boolean primaryGroupFlag;

    public static GroupShortDto convertToShortDto(Group group) {

        GroupShortDto dto = new GroupShortDto();
        dto.setId(group.getId());
        dto.setLevel(group.getLevel());
        dto.setName(group.getName());
        dto.setPrimaryGroupFlag(group.getPrimaryGroupFlag());
        return dto;
    }

    public boolean isPrimaryGroupFlag() {
        return primaryGroupFlag;
    }

    public void setPrimaryGroupFlag(boolean primaryGroupFlag) {
        this.primaryGroupFlag = primaryGroupFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "GroupShortDto [id=" + id + ", name=" + name + ", level=" + level
                + ", primaryGroupFlag=" + primaryGroupFlag + "]";
    }

}
