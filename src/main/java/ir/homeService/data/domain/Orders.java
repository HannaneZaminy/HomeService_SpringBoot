package ir.homeService.data.domain;

import ir.homeService.data.enums.OrderSituation;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Double proposedPrice;
    @Column
    private String jobDescription;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateOfOrderRegistration;
    @Temporal(TemporalType.DATE)
    private Date dateOfWork;
    @Enumerated(value = EnumType.STRING)
    private OrderSituation situation;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private SubService subService;
    @OneToMany(mappedBy = "orders")
    private List<Offers> offers = new ArrayList<>();
    @ManyToOne
    private Expert expert;
    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;


    public Integer getId() {
        return id;
    }

    public Orders setId(Integer id) {
        this.id = id;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public Orders setAddress(Address address) {
        this.address = address;
        return this;
    }

    public Double getProposedPrice() {
        return proposedPrice;
    }

    public Orders setProposedPrice(Double proposedPrice) {
        this.proposedPrice = proposedPrice;
        return this;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public Orders setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
        return this;
    }

    public Date getDateOfOrderRegistration() {
        return dateOfOrderRegistration;
    }

    public Orders setDateOfOrderRegistration(Date dateOfOrderRegistration) {
        this.dateOfOrderRegistration = dateOfOrderRegistration;
        return this;
    }

    public Date getDateOfWork() {
        return dateOfWork;
    }

    public Orders setDateOfWork(Date dateOfWork) {
        this.dateOfWork = dateOfWork;
        return this;
    }

    public OrderSituation getSituation() {
        return situation;
    }

    public Orders setSituation(OrderSituation situation) {
        this.situation = situation;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Orders setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public SubService getSubService() {
        return subService;
    }

    public Orders setSubService(SubService subService) {
        this.subService = subService;
        return this;
    }

    public List<Offers> getOffers() {
        return offers;
    }

    public Orders setOffers(List<Offers> offers) {
        this.offers = offers;
        return this;
    }

    public Expert getExpert() {
        return expert;
    }

    public Orders setExpert(Expert expert) {
        this.expert = expert;
        return this;
    }

}
