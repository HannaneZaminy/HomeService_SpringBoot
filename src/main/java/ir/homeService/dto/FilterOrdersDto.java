package ir.homeService.dto;

import ir.homeService.data.enums.OrderSituation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class FilterOrdersDto {
    private OrderSituation situation;
    private String serviceName;
    private String subServiceName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endDate;

    public OrderSituation getSituation() {
        return situation;
    }

    public FilterOrdersDto setSituation(OrderSituation situation) {
        this.situation = situation;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public FilterOrdersDto setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getSubServiceName() {
        return subServiceName;
    }

    public FilterOrdersDto setSubServiceName(String subServiceName) {
        this.subServiceName = subServiceName;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public FilterOrdersDto setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public FilterOrdersDto setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }
}
