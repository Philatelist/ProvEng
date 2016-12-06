package com.provectus.proveng.domain.dto;

import com.provectus.proveng.domain.Department;

public class DepartmentShortDto {
    private Long id;
    private String name;
    private String url;
    private String description;

    public static DepartmentShortDto convertToDto(Department dep) {
        if (dep == null) return null;
        DepartmentShortDto depDto = new DepartmentShortDto();
        depDto.setDescription(dep.getDescription());
        depDto.setId(dep.getId());
        depDto.setName(dep.getName());
        depDto.setUrl(dep.getUrl());
        return depDto;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
