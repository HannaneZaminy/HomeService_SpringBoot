package ir.homeService.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


public class CommentDto {

    private Integer id;
    private CustomerDto customer;
    private ExpertDto expert;
    private String comment;
    @Max(value = 5,message = "max.rate")
    @Min(value = 0,message = "min.rate")
    private Double rate;
    private OrderDto orderDto;

    public Integer getId() {
        return id;
    }

    public CommentDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public CommentDto setCustomer(CustomerDto customer) {
        this.customer = customer;
        return this;
    }

    public ExpertDto getExpert() {
        return expert;
    }

    public CommentDto setExpert(ExpertDto expert) {
        this.expert = expert;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public CommentDto setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Double getRate() {
        return rate;
    }

    public CommentDto setRate(Double rate) {
        this.rate = rate;
        return this;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public CommentDto setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
        return this;
    }
}
