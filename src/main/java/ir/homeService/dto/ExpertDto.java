package ir.homeService.dto;

import ir.homeService.data.enums.UserRole;
import ir.homeService.data.enums.UserSituation;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ExpertDto extends UserDto {
    private Double rate;
    @NotNull(message = "image")
    private byte[] image;
    private String field;
    private List<SubServiceDto> services = new ArrayList<>();
    private List<OfferDto> offers = new ArrayList<>();
    private List<CommentDto> comments = new ArrayList<>();
    private List<OrderDto> orders = new ArrayList<>();



    public ExpertDto() {
        super.setCredit(0.0);
        super.setUserRole(UserRole.EXPERT);
        super.setUserSituation(UserSituation.New);
        this.rate=0.0;
    }

    public String getField() {
        return field;
    }

    public ExpertDto setField(String field) {
        this.field = field;
        return this;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public ExpertDto setOrders(List<OrderDto> orders) {
        this.orders = orders;
        return this;
    }

    public Double getRate() {
        return rate;
    }

    public ExpertDto setRate(Double rate) {
        this.rate = rate;
        return this;
    }

    public byte[] getImage() {
        return image;
    }

    public ExpertDto setImage(byte[] image) {
        this.image = image;
        return this;
    }

    public List<SubServiceDto> getServices() {
        return services;
    }

    public ExpertDto setServices(List<SubServiceDto> services) {
        this.services = services;
        return this;
    }

    public List<OfferDto> getOffers() {
        return offers;
    }

    public ExpertDto setOffers(List<OfferDto> offers) {
        this.offers = offers;
        return this;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public ExpertDto setComments(List<CommentDto> comments) {
        this.comments = comments;
        return this;
    }
}
