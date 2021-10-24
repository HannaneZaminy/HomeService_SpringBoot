package ir.homeService.service.mapper;

import ir.homeService.data.domain.Address;
import ir.homeService.dto.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapperImpl implements AddressMapper {
    @Override
    public AddressDto toAddressDto(Address address) {
        AddressDto addressDto = new AddressDto(
                address.getAddress()
                , address.getPostal_address()
                , address.getAddress_compact()
                , address.getName()
                , address.getPoi()
                , address.getCountry()
                , address.getProvince()
                , address.getCounty()
                , address.getDistrict()
                , address.getRural_district()
                , address.getCity()
                , address.getVillage()
                , address.getRegion()
                , address.getNeighbourhood()
                , address.getLast()
                , address.getPlaque()
                , address.getPostal_code());

        return addressDto;
    }

    @Override
    public Address toAddress(AddressDto addressDto) {
        Address address = new Address(
                addressDto.getAddress()
                , addressDto.getPostal_address()
                , addressDto.getAddress_compact()
                , addressDto.getName()
                , addressDto.getPoi()
                , addressDto.getCountry()
                , addressDto.getProvince()
                , addressDto.getCounty()
                , addressDto.getDistrict()
                , addressDto.getRural_district()
                , addressDto.getCity()
                , addressDto.getVillage()
                , addressDto.getRegion()
                , addressDto.getNeighbourhood()
                , addressDto.getLast()
                , addressDto.getPlaque()
                , addressDto.getPostal_code()
        );


        return address;
    }
}
