package ir.homeService.dto;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SubServiceDto {
    private Integer id;
    private Double basePrice;
    @NotBlank(message = "description")
    private String description;
    @NotBlank(message = "name")
    private String name;
    private ServiceDto service;
    private List<ExpertDto> experts = new ArrayList<>();
    private List<OrderDto> orders = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceDto getService() {
        return service;
    }

    public List<ExpertDto> getExperts() {
        return experts;
    }

    public SubServiceDto setExperts(List<ExpertDto> experts) {
        this.experts = experts;
        return this;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public SubServiceDto setOrders(List<OrderDto> orders) {
        this.orders = orders;
        return this;
    }

    public SubServiceDto setService(ServiceDto service) {
        this.service = service;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubServiceDto)) return false;
        SubServiceDto that = (SubServiceDto) o;
        return getId().equals(that.getId()) && getBasePrice().equals(that.getBasePrice()) && getDescription().equals(that.getDescription()) && getName().equals(that.getName()) && getService().equals(that.getService());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBasePrice(), getDescription(), getName(), getService());
    }
}
