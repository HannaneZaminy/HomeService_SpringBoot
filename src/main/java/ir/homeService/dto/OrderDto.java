package ir.homeService.dto;

import ir.homeService.data.enums.OrderSituation;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDto {
    private Integer id;
    private Double proposedPrice;
    @NotBlank(message = "jobDescription")
    private String jobDescription;
    private Date dateOfOrderRegistration;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateOfWork;
    private OrderSituation situation;
    private CustomerDto customer;
    private SubServiceDto subService;
    private List<OfferDto> offers = new ArrayList<>();
    private ExpertDto expert;
    private AddressDto address;



    public Integer getId() {
        return id;
    }

    public OrderDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public ExpertDto getExpert() {
        return expert;
    }

    public OrderDto setExpert(ExpertDto expert) {
        this.expert = expert;
        return this;
    }

    public Double getProposedPrice() {
        return proposedPrice;
    }

    public OrderDto setProposedPrice(Double proposedPrice) {
        this.proposedPrice = proposedPrice;
        return this;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public OrderDto setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
        return this;
    }

    public Date getDateOfOrderRegistration() {
        return dateOfOrderRegistration;
    }

    public OrderDto setDateOfOrderRegistration(Date dateOfOrderRegistration) {
        this.dateOfOrderRegistration = dateOfOrderRegistration;
        return this;
    }

    public Date getDateOfWork() {
        return dateOfWork;
    }

    public OrderDto setDateOfWork(Date dateOfWork) {
        this.dateOfWork = dateOfWork;
        return this;
    }

    public OrderSituation getSituation() {
        return situation;
    }

    public OrderDto setSituation(OrderSituation situation) {
        this.situation = situation;
        return this;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public OrderDto setCustomer(CustomerDto customer) {
        this.customer = customer;
        return this;
    }

    public SubServiceDto getSubService() {
        return subService;
    }

    public OrderDto setSubService(SubServiceDto subService) {
        this.subService = subService;
        return this;
    }

    public List<OfferDto> getOffers() {
        return offers;
    }

    public OrderDto setOffers(List<OfferDto> offers) {
        this.offers = offers;
        return this;
    }

    public AddressDto getAddress() {
        return address;
    }

    public OrderDto setAddress(AddressDto address) {
        this.address = address;
        return this;
    }
}
