package ir.homeService.service.mapper;

import ir.homeService.data.domain.Orders;
import ir.homeService.dto.OrderDto;

public interface OrderMapper {
    Orders toOrder(OrderDto dto);

    OrderDto toOrderDto(Orders order);
}
