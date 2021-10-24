package ir.homeService.service.mapper;

import ir.homeService.data.domain.Offers;
import ir.homeService.dto.OfferDto;

public interface OfferMapper {
    Offers toOffer(OfferDto dto);

    OfferDto toOfferDto(Offers offer);

}
