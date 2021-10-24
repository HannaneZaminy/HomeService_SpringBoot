package ir.homeService.service.mapper;

import ir.homeService.data.domain.Customer;
import ir.homeService.dto.CustomerDto;

public interface CustomerMapper {
    Customer toCustomer(CustomerDto dto);

    CustomerDto toCustomerDto(Customer customer);
}
