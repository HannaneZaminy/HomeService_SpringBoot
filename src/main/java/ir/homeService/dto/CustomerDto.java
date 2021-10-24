package ir.homeService.dto;

import ir.homeService.data.enums.UserRole;
import ir.homeService.data.enums.UserSituation;

import java.util.ArrayList;
import java.util.List;

public class CustomerDto extends UserDto {
    private List<OrderDto> orders = new ArrayList<>();
    private List<CommentDto> comments = new ArrayList<>();

    public CustomerDto() {
        super.setCredit(0.0);
        super.setUserRole(UserRole.CUSTOMER);
        super.setUserSituation(UserSituation.New);
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public CustomerDto setOrders(List<OrderDto> orders) {
        this.orders = orders;
        return this;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public CustomerDto setComments(List<CommentDto> comments) {
        this.comments = comments;
        return this;
    }

}
