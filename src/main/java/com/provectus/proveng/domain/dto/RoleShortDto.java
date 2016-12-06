package com.provectus.proveng.domain.dto;

import com.provectus.proveng.domain.Role;

public class RoleShortDto {

    private Long id;
    private String name;

    public static RoleShortDto convertToDto(Role role) {
        RoleShortDto dto = new RoleShortDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
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
}
