package ir.homeService.service;

import ir.homeService.data.repository.OrderHistoryRepository;
import ir.homeService.dto.OrderHistoryDto;
import ir.homeService.service.mapper.OrderHistoryMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService{
    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderHistoryMapper orderHistoryMapper;

    public OrderHistoryServiceImpl(OrderHistoryRepository orderHistoryRepository, OrderHistoryMapper orderHistoryMapper) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.orderHistoryMapper = orderHistoryMapper;
    }

    @Override
    public void save(OrderHistoryDto dto) {
        orderHistoryRepository.save(orderHistoryMapper.toOrderHistory(dto));
    }
}
