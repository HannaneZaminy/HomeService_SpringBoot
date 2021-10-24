package ir.homeService.dto;

public class LoginExpertDto {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public LoginExpertDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginExpertDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
