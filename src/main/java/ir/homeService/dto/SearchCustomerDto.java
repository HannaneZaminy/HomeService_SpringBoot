package ir.homeService.dto;

import ir.homeService.data.enums.UserRole;

public class SearchCustomerDto {
    private String name;
    private String lastName;
    private UserRole userRole;
    private String email;

    public String getName() {
        return name;
    }

    public SearchCustomerDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public SearchCustomerDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserRole getRole() {
        return userRole;
    }

    public SearchCustomerDto setRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public SearchCustomerDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
