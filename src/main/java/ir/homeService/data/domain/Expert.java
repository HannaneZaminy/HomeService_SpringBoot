package ir.homeService.data.domain;

import ir.homeService.data.enums.UserRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Expert extends Users {
    @Column
    //double begir
    private Double rate;
    @Lob
    @Column(columnDefinition = "BLOB", length = 300000)
    private byte[] image;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "expert_services",
            joinColumns = {
                    @JoinColumn(name = "expert_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "service_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private List<SubService> services = new ArrayList<>();
    @OneToMany(mappedBy = "expert")
    private List<Offers> offers = new ArrayList<>();
    @OneToMany(mappedBy = "expert")
    private List<Comments> comments = new ArrayList<>();
    @OneToMany(mappedBy = "expert")
    private List<Orders> orders = new ArrayList<>();
    @Column
    private String field;

    public String getField() {
        return field;
    }

    public Expert setField(String field) {
        this.field = field;
        return this;
    }

    public Expert() {
        this.setUserRole(UserRole.EXPERT);
    }


    public Double getRate() {
        return rate;
    }

    public Expert setRate(Double rate) {
        this.rate = rate;
        return this;
    }

    public byte[] getImage() {
        return image;
    }

    public Expert setImage(byte[] image) {
        this.image = image;
        return this;
    }

    public List<SubService> getServices() {
        return services;
    }

    public Expert setServices(List<SubService> services) {
        this.services = services;
        return this;
    }

    public List<Offers> getOffers() {
        return offers;
    }

    public Expert setOffers(List<Offers> offers) {
        this.offers = offers;
        return this;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public Expert setComments(List<Comments> comments) {
        this.comments = comments;
        return this;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public Expert setOrders(List<Orders> orders) {
        this.orders = orders;
        return this;
    }
}
