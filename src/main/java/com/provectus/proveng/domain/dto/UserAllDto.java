package com.provectus.proveng.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.provectus.proveng.domain.DayBook;
import com.provectus.proveng.domain.Group;
import com.provectus.proveng.domain.Role;
import com.provectus.proveng.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserAllDto {

    private Long id;
    private String loginName;
    private String firstName;
    private String lastName;
    private String email;
    private String skype;
    private Date inviteDate;
    private String phone;
    private String url;
    private String level;
    private DepartmentShortDto department;
    private Collection<RoleShortDto> roles;
    private Collection<GroupShortDto> groups;
    private Collection<DayBookShortDto> dayBooks;
    private Date modifyDtm;

    public static UserAllDto convertToDto(User user) {
        UserAllDto dto = new UserAllDto();

        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setInviteDate(user.getInviteDate());
        dto.setLastName(user.getLastName());
        dto.setLevel(user.getLevel());
        dto.setLoginName(user.getLoginName());
        dto.setModifyDtm(user.getModifyDtm());
        dto.setPhone(user.getPhone());
        dto.setSkype(user.getSkype());
        dto.setUrl(user.getUrl());

        DepartmentShortDto depDto = DepartmentShortDto.convertToDto(user.getDepartment());
        dto.setDepartment(depDto);

        List<GroupShortDto> groupsDto = new ArrayList<>();
        for (Group group : user.getGroups()) {
            GroupShortDto groupDto = GroupShortDto.convertToShortDto(group);
            groupsDto.add(groupDto);
        }
        dto.setGroups(groupsDto);

        List<RoleShortDto> rolesDto = new ArrayList<>();
        for (Role role : user.getRoles()) {
            RoleShortDto roleDto = RoleShortDto.convertToDto(role);
            rolesDto.add(roleDto);
        }
        dto.setRoles(rolesDto);

        List<DayBookShortDto> dayBooksDto = new ArrayList<>();
        for (DayBook dayBook : user.getDayBooks()) {
            DayBookShortDto dayBookDto = DayBookShortDto.convertToDto(dayBook);
            dayBooksDto.add(dayBookDto);
        }
        dto.setDayBooks(dayBooksDto);

        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public Date getInviteDate() {
        return inviteDate;
    }

    public void setInviteDate(Date inviteDate) {
        this.inviteDate = inviteDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public DepartmentShortDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentShortDto department) {
        this.department = department;
    }

    public Collection<RoleShortDto> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleShortDto> roles) {
        this.roles = roles;
    }

    public Collection<GroupShortDto> getGroups() {
        return groups;
    }

    public void setGroups(Collection<GroupShortDto> groups) {
        this.groups = groups;
    }

    public Collection<DayBookShortDto> getDayBooks() {
        return dayBooks;
    }

    public void setDayBooks(Collection<DayBookShortDto> dayBooks) {
        this.dayBooks = dayBooks;
    }

    public Date getModifyDtm() {
        return modifyDtm;
    }

    public void setModifyDtm(Date modifyDtm) {
        this.modifyDtm = modifyDtm;
    }
}
