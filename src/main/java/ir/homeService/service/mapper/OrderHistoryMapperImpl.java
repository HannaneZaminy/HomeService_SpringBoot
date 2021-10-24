package ir.homeService.service.mapper;

import ir.homeService.data.domain.OrderHistory;
import ir.homeService.dto.OrderHistoryDto;
import org.springframework.stereotype.Component;

@Component
public class OrderHistoryMapperImpl implements OrderHistoryMapper {
    private final OrderMapper orderMapper;

    public OrderHistoryMapperImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderHistory toOrderHistory(OrderHistoryDto dto) {
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrder(orderMapper.toOrder(dto.getOrderDto()));
        orderHistory.setId(dto.getId());
        orderHistory.setOrderSituation(dto.getOrderSituation());
        return orderHistory;
    }

    @Override
    public OrderHistoryDto toOrderHistoryDto(OrderHistory orderHistory) {
        OrderHistoryDto orderHistoryDto = new OrderHistoryDto();
        orderHistoryDto.setId(orderHistory.getId());
        orderHistoryDto.setOrderSituation(orderHistory.getOrderSituation());
        orderHistoryDto.setOrderDto(orderMapper.toOrderDto(orderHistory.getOrder()));
        return orderHistoryDto;
    }
}
