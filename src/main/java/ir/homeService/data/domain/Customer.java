package ir.homeService.data.domain;

import ir.homeService.data.enums.UserRole;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends Users {

    @OneToMany(mappedBy = "customer")
    private List<Orders> orders = new ArrayList<>();
    @OneToMany(mappedBy = "customer")
    private List<Comments> comments = new ArrayList<>();

    public Customer() {
        this.setUserRole(UserRole.CUSTOMER);
    }


    public List<Orders> getOrders() {
        return orders;
    }

    public Customer setOrders(List<Orders> orders) {
        this.orders = orders;
        return this;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public Customer setComments(List<Comments> comments) {
        this.comments = comments;
        return this;
    }
}
