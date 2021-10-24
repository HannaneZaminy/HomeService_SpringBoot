package ir.homeService.service.mapper;

import ir.homeService.data.domain.Customer;
import ir.homeService.dto.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapperImpl implements CustomerMapper {
//    private final CommentMapper commentMapper;
//    private final OrderMapper orderMapper;
//
//    public CustomerMapperImpl(CommentMapper commentMapper, OrderMapper orderMapper) {
//        this.commentMapper = commentMapper;
//        this.orderMapper = orderMapper;
//    }

    @Override
    public Customer toCustomer(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPassword(dto.getPassword());
        customer.setUserRole(dto.getUserRole());
        customer.setUserSituation(dto.getUserSituation());
        customer.setDate(dto.getDate());
        customer.setCredit(dto.getCredit());
        customer.setEnabled(dto.isEnabled());
        customer.setVerificationCode(dto.getVerificationCode());
//        customer.setComments(
//                dto.getComments().stream().map
//                        (i->commentMapper.toComment(i))
//                        .collect(Collectors.toList()));
//
//        customer.setOrders(
//                dto.getOrders().stream().map
//                        (i->orderMapper.toOrder(i))
//                        .collect(Collectors.toList()));

        return customer;
    }

    @Override
    public CustomerDto toCustomerDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPassword(customer.getPassword());
        dto.setDate(customer.getDate());
//        dto.setComments(customer.getComments().stream().map(i->commentMapper.toCommentDto(i)).collect(Collectors.toList()));
//        dto.setOrders(customer.getOrders().stream().map(i->orderMapper.toOrderDto(i)).collect(Collectors.toList()));
        dto.setUserRole(customer.getUserRole());
        dto.setUserSituation(customer.getUserSituation());
        dto.setCredit(customer.getCredit());
        dto.setVerificationCode(customer.getVerificationCode());
        dto.setEnabled(customer.isEnabled());
        return dto;
    }
}
