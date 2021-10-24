package ir.homeService.data.domain;

import javax.persistence.*;

@Entity
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String userName;
    @Column
    private String password;

    public Integer getId() {
        return id;
    }

    public Manager setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Manager setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Manager setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
