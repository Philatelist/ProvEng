package com.provectus.proveng.domain.dto;

import com.provectus.proveng.domain.User;

public class UserShortDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String url;

    public UserShortDto() {
    }

    public UserShortDto(Long id, String firstName, String lastName, String url) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.url = url;
    }

    public static UserShortDto convertToDto(User user) {
        UserShortDto dto = new UserShortDto();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUrl(user.getUrl());

        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
