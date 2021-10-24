package ir.homeService.dto;

import ir.homeService.data.enums.UserRole;

public class FilterUsersDto {
    private String name;
    private String lastName;
    private UserRole userRole;
    private String email;
    private Integer rate;
    private String field;

    public String getName() {
        return name;
    }

    public FilterUsersDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public FilterUsersDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserRole getRole() {
        return userRole;
    }

    public FilterUsersDto setRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public FilterUsersDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public Integer getRate() {
        return rate;
    }

    public FilterUsersDto setRate(Integer rate) {
        this.rate = rate;
        return this;
    }

    public String getField() {
        return field;
    }

    public FilterUsersDto setField(String field) {
        this.field = field;
        return this;
    }
}
