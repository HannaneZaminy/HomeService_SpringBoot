package ir.homeService.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CreditCardInfo {
    @NotBlank(message = "creditNumber")
    @Pattern(regexp = "[1-9][0-9]{15}",message = "creditNumber")
    private String creditNumber;
    @NotBlank(message = "card.pass")
    @Pattern(regexp = "[0-9]{5}",message = "card.pass")
    private String password;
    @NotBlank(message = "expireDate")
    @Pattern(regexp ="^(0[1-9]|1[0-2])/?([0-9]{2})$",message = "expireDate")
    private String expireDate;
    @NotBlank(message = "cvv2")
    @Pattern(regexp = "[0-9]{4}",message = "cvv2")
    private String cvv2;
    @NotBlank(message = "captcha")
    private String captcha;

    public String getCreditNumber() {
        return creditNumber;
    }

    public CreditCardInfo setCreditNumber(String creditNumber) {
        this.creditNumber = creditNumber;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public CreditCardInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public CreditCardInfo setExpireDate(String expireDate) {
        this.expireDate = expireDate;
        return this;
    }

    public String getCvv2() {
        return cvv2;
    }

    public CreditCardInfo setCvv2(String cvv2) {
        this.cvv2 = cvv2;
        return this;
    }

    public String getCaptcha() {
        return captcha;
    }

    public CreditCardInfo setCaptcha(String captcha) {
        this.captcha = captcha;
        return this;
    }
}
