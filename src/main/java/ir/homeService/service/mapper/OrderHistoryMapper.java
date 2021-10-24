package ir.homeService.service.mapper;

import ir.homeService.data.domain.OrderHistory;
import ir.homeService.dto.OrderHistoryDto;

public interface OrderHistoryMapper {

    OrderHistory toOrderHistory(OrderHistoryDto dto);
    OrderHistoryDto toOrderHistoryDto(OrderHistory orderHistory);
}
