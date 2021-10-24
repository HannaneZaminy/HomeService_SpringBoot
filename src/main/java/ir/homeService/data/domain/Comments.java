package ir.homeService.data.domain;

import javax.persistence.*;

@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Expert expert;
    @Column
    private String comment;
    @Column
    private Double rate;
    @OneToOne
    private Orders order;


    public Integer getId() {
        return id;
    }

    public Comments setId(Integer id) {
        this.id = id;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Comments setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public Expert getExpert() {
        return expert;
    }

    public Comments setExpert(Expert expert) {
        this.expert = expert;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Comments setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Double getRate() {
        return rate;
    }

    public Comments setRate(Double rate) {
        this.rate = rate;
        return this;
    }

    public Orders getOrder() {
        return order;
    }

    public Comments setOrder(Orders order) {
        this.order = order;
        return this;
    }
}
