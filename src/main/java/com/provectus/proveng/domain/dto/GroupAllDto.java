package com.provectus.proveng.domain.dto;

import com.provectus.proveng.domain.Group;

import java.util.List;

public class GroupAllDto {

    List<UserShortDto> members;
    private Long id;
    private String name;
    private UserShortDto leader;
    private String level;
    private boolean primaryGroupFlag;

    public static GroupAllDto convertToDto(Group group,
                                           List<UserShortDto> members) {

        GroupAllDto dto = new GroupAllDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setLevel(group.getLevel());
        dto.setLeader(UserShortDto.convertToDto(group.getLeader()));
        dto.setMembers(members);
        dto.setPrimaryGroupFlag(group.getPrimaryGroupFlag());
        return dto;

    }

    public boolean isPrimaryGroupFlag() {
        return primaryGroupFlag;
    }

    public void setPrimaryGroupFlag(boolean primaryGroupFlag) {
        this.primaryGroupFlag = primaryGroupFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserShortDto getLeader() {
        return leader;
    }

    public void setLeader(UserShortDto leader) {
        this.leader = leader;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<UserShortDto> getMembers() {
        return members;
    }

    public void setMembers(List<UserShortDto> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "GroupAllDto [id=" + id + ", name=" + name + ", leader=" + leader + ", level="
                + level + ", primaryGroupFlag=" + primaryGroupFlag + ", members=" + members + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
