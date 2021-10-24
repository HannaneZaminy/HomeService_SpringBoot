package ir.homeService.service.mapper;

import ir.homeService.data.domain.Orders;
import ir.homeService.dto.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class OrderMapperImpl implements OrderMapper {
    private final CustomerMapper customerMapper;
    private final SubServiceMapper serviceMapper;
    private final ExpertMapper expertMapper;
    private final AddressMapper addressMapper;

    public OrderMapperImpl(CustomerMapper customerMapper, SubServiceMapper serviceMapper, ExpertMapper expertMapper, AddressMapper addressMapper) {
        this.customerMapper = customerMapper;
        this.serviceMapper = serviceMapper;
        this.expertMapper = expertMapper;
        this.addressMapper = addressMapper;
    }


//    public OrderMapperImpl(CustomerMapper customerMapper, OfferMapper offerMapper, SubServiceMapper serviceMapper, ExpertMapper expertMapper) {
//        this.customerMapper = customerMapper;
//        this.offerMapper = offerMapper;
//        this.serviceMapper = serviceMapper;
//        this.expertMapper = expertMapper;
//    }

    @Override
    public Orders toOrder(OrderDto dto) {
        Orders order = new Orders();
        order.setId(dto.getId());
        order.setCustomer(customerMapper.toCustomer(dto.getCustomer()));
        order.setDateOfOrderRegistration(dto.getDateOfOrderRegistration());
//        order.setOffers(dto.getOffers().stream().map(i->offerMapper.toOffer(i)).collect(Collectors.toList()));
        order.setJobDescription(dto.getJobDescription());
        order.setDateOfWork(dto.getDateOfWork());
        order.setProposedPrice(dto.getProposedPrice());
        order.setSituation(dto.getSituation());
        order.setSubService(serviceMapper.convertToSubService(dto.getSubService()));
        order.setAddress(addressMapper.toAddress(dto.getAddress()));
        if (dto.getExpert() != null) {
            order.setExpert(expertMapper.toExpert(dto.getExpert()));
        }
        return order;
    }


    @Override
    public OrderDto toOrderDto(Orders order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCustomer(customerMapper.toCustomerDto(order.getCustomer()));
        dto.setDateOfOrderRegistration(order.getDateOfOrderRegistration());
//        dto.setOffers(order.getOffers().stream().map(i->offerMapper.toOfferDto(i)).collect(Collectors.toList()));
        dto.setJobDescription(order.getJobDescription());
        dto.setSubService(serviceMapper.covertToSubServiceDto(order.getSubService()));
        dto.setProposedPrice(order.getProposedPrice());
        dto.setSituation(order.getSituation());
        dto.setDateOfWork(order.getDateOfWork());
        dto.setAddress(addressMapper.toAddressDto(order.getAddress()));
        if (order.getExpert() != null)
            dto.setExpert(expertMapper.toExpertDto(order.getExpert()));
        return dto;
    }
}
