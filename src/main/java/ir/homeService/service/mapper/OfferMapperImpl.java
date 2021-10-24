package ir.homeService.service.mapper;

import ir.homeService.data.domain.Offers;
import ir.homeService.dto.OfferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfferMapperImpl implements OfferMapper {
    private  ExpertMapper expertMapper;
    private  OrderMapper orderMapper;

    public ExpertMapper getExpertMapper() {
        return expertMapper;
    }
    @Autowired
    public void setExpertMapper(ExpertMapper expertMapper) {
        this.expertMapper = expertMapper;
    }

    public OrderMapper getOrderMapper() {
        return orderMapper;
    }


    @Autowired
    public void setOrderMapper( OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public Offers toOffer(OfferDto dto) {
        Offers offer = new Offers();
        offer.setId(dto.getId());
        offer.setOfferPrice(dto.getOfferPrice());
        offer.setSubmitOffer(dto.getSubmitOffer());
        offer.setExpert(expertMapper.toExpert(dto.getExpert()));
        offer.setOrders(orderMapper.toOrder(dto.getOrders()));
        offer.setDurationOfWork(dto.getDurationOfWork());
        offer.setStartTime(dto.getStartTime());
        offer.setOfferSituation(dto.getOfferSituation());
        return offer;
    }

    @Override
    public OfferDto toOfferDto(Offers offer) {
        OfferDto dto = new OfferDto();
        dto.setExpert(expertMapper.toExpertDto(offer.getExpert()));
        dto.setOfferPrice(offer.getOfferPrice());
        dto.setSubmitOffer(offer.getSubmitOffer());
        dto.setDurationOfWork(offer.getDurationOfWork());
        dto.setId(offer.getId());
        dto.setStartTime(offer.getStartTime());
        dto.setOrders(orderMapper.toOrderDto(offer.getOrders()));
        dto.setOfferSituation(offer.getOfferSituation());
        return dto;
    }
}
