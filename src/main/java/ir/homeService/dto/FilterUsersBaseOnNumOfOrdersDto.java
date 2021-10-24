package ir.homeService.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class FilterUsersBaseOnNumOfOrdersDto {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endDate;
    private Integer maxNumberOfOrders;
    private Integer minNumberOfOrders;
    private Integer maxNumberOfOffers;
    private Integer minNumberOfOffers;

    public Date getStartDate() {
        return startDate;
    }

    public FilterUsersBaseOnNumOfOrdersDto setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public FilterUsersBaseOnNumOfOrdersDto setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public Integer getMaxNumberOfOrders() {
        return maxNumberOfOrders;
    }

    public FilterUsersBaseOnNumOfOrdersDto setMaxNumberOfOrders(Integer maxNumberOfOrders) {
        this.maxNumberOfOrders = maxNumberOfOrders;
        return this;
    }

    public Integer getMinNumberOfOrders() {
        return minNumberOfOrders;
    }

    public FilterUsersBaseOnNumOfOrdersDto setMinNumberOfOrders(Integer minNumberOfOrders) {
        this.minNumberOfOrders = minNumberOfOrders;
        return this;
    }

    public Integer getMaxNumberOfOffers() {
        return maxNumberOfOffers;
    }

    public FilterUsersBaseOnNumOfOrdersDto setMaxNumberOfOffers(Integer maxNumberOfOffers) {
        this.maxNumberOfOffers = maxNumberOfOffers;
        return this;
    }

    public Integer getMinNumberOfOffers() {
        return minNumberOfOffers;
    }

    public FilterUsersBaseOnNumOfOrdersDto setMinNumberOfOffers(Integer minNumberOfOffers) {
        this.minNumberOfOffers = minNumberOfOffers;
        return this;
    }
}
