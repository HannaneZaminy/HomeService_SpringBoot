package ir.homeService.dto;

import ir.homeService.data.enums.OrderSituation;
import ir.homeService.data.enums.UserRole;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class FilterSpecialUserOrdersDto {
    private Integer userId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endDate;
    private OrderSituation situation;
    private Double maxOfferPrice;
    private Double minOfferPrice;
    private UserRole role;

    public Integer getUserId() {
        return userId;
    }

    public FilterSpecialUserOrdersDto setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public FilterSpecialUserOrdersDto setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public FilterSpecialUserOrdersDto setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public OrderSituation getSituation() {
        return situation;
    }

    public FilterSpecialUserOrdersDto setSituation(OrderSituation situation) {
        this.situation = situation;
        return this;
    }

    public Double getMaxOfferPrice() {
        return maxOfferPrice;
    }

    public FilterSpecialUserOrdersDto setMaxOfferPrice(Double maxOfferPrice) {
        this.maxOfferPrice = maxOfferPrice;
        return this;
    }

    public Double getMinOfferPrice() {
        return minOfferPrice;
    }

    public FilterSpecialUserOrdersDto setMinOfferPrice(Double minOfferPrice) {
        this.minOfferPrice = minOfferPrice;
        return this;
    }

    public UserRole getRole() {
        return role;
    }

    public FilterSpecialUserOrdersDto setRole(UserRole role) {
        this.role = role;
        return this;
    }
}
