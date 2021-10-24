package ir.homeService.data.domain;

import ir.homeService.data.enums.UserRole;
import ir.homeService.data.enums.UserSituation;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserSituation userSituation;
    @Column
    @CreationTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;
    @Column
    private Double credit;
    @Column(length = 64)
    private String verificationCode;
    private boolean enabled=false;

    public Users() {
    }

    public Integer getId() {
        return id;
    }

    public Users setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Users setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Users setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Users setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Users setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserSituation getUserSituation() {
        return userSituation;
    }

    public Users setUserSituation(UserSituation userSituation) {
        this.userSituation = userSituation;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Users setDate(Date date) {
        this.date = date;
        return this;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public Users setUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public Double getCredit() {
        return credit;
    }

    public Users setCredit(Double credit) {
        this.credit = credit;
        return this;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public Users setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Users setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
