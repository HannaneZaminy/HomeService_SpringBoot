package ir.homeService.data.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@DiscriminatorValue(value = "Sub_Service")
public class SubService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Double basePrice;
    @Column
    private String description;
    @Column
    private String name;
    @ManyToOne
    private Service service;
    @ManyToMany(mappedBy = "services", fetch = FetchType.LAZY)
    private List<Expert> experts = new ArrayList<>();
    @OneToMany(mappedBy = "subService", fetch = FetchType.LAZY)
    private List<Orders> orders = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public SubService setId(Integer id) {
        this.id = id;
        return this;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public SubService setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SubService setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public SubService setName(String name) {
        this.name = name;
        return this;
    }

    public Service getService() {
        return service;
    }

    public SubService setService(Service service) {
        this.service = service;
        return this;
    }

    public List<Expert> getExperts() {
        return experts;
    }

    public SubService setExperts(List<Expert> experts) {
        this.experts = experts;
        return this;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public SubService setOrders(List<Orders> orders) {
        this.orders = orders;
        return this;
    }
}
