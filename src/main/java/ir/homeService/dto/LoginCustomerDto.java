package ir.homeService.dto;

import ir.homeService.service.validation.ValidPassword;

import javax.validation.constraints.NotBlank;

public class LoginCustomerDto {
    @NotBlank(message = "email")
    private String email;
    @ValidPassword
    private String password;

    public String getEmail() {
        return email;
    }

    public LoginCustomerDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginCustomerDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
