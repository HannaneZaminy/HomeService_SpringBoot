package ir.homeService.service.mapper;

import ir.homeService.data.domain.Address;
import ir.homeService.dto.AddressDto;

public interface AddressMapper {
    AddressDto toAddressDto(Address address);
    Address toAddress(AddressDto addressDto);
}
