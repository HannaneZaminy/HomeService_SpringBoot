package ir.homeService.dto;

import ir.homeService.service.validation.ChangePasswordValidation;
import ir.homeService.service.validation.RegisterValidation;
import ir.homeService.data.enums.UserRole;
import ir.homeService.data.enums.UserSituation;
import ir.homeService.service.validation.LoginValidation;
import ir.homeService.service.validation.ValidPassword;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.Objects;

public class UserDto {
    private Integer id;
    @NotBlank(message = "name", groups = {RegisterValidation.class})
    @Size(min = 2, max = 10, message = "name.size", groups = {RegisterValidation.class})
    private String name;
    @NotBlank(message = "last.name", groups = {RegisterValidation.class})
    @Size(min = 2, max = 15, message = "last.name.size", groups = {RegisterValidation.class})
    private String lastName;
    @Email(groups = {LoginValidation.class, RegisterValidation.class})
    @NotBlank(message = "email", groups = {LoginValidation.class, RegisterValidation.class})
    private String email;
    @ValidPassword(message = "password.exception",groups = {LoginValidation.class, RegisterValidation.class, ChangePasswordValidation.class})
    private String password;
    private UserSituation userSituation;
    private Date date;
    private UserRole userRole;
    private Double credit;
    private String verificationCode;
    private boolean enabled=false;

    public UserDto() {
        this.credit = 0.0;
        this.userSituation = UserSituation.New;
    }

    public Integer getId() {
        return id;
    }

    public UserDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserSituation getUserSituation() {
        return userSituation;
    }

    public UserDto setUserSituation(UserSituation userSituation) {
        this.userSituation = userSituation;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public UserDto setDate(Date date) {
        this.date = date;
        return this;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public UserDto setUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public Double getCredit() {
        return credit;
    }

    public UserDto setCredit(Double credit) {
        this.credit = credit;
        return this;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public UserDto setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public UserDto setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto dto = (UserDto) o;
        return getId().equals(dto.getId()) && getName().equals(dto.getName()) && getLastName().equals(dto.getLastName()) && getEmail().equals(dto.getEmail()) && getPassword().equals(dto.getPassword()) && userRole == dto.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getLastName(), getEmail(), getPassword(), userRole);
    }
}
