package ir.homeService.service.mapper;

import ir.homeService.data.domain.SubService;
import ir.homeService.dto.SubServiceDto;
import org.springframework.stereotype.Component;

@Component
public class SubServiceMapperImpl implements SubServiceMapper {
    private final ServiceMapper serviceMapper;
    // private final ExpertMapper expertMapper;

    public SubServiceMapperImpl(ServiceMapper serviceMapper) {
        this.serviceMapper = serviceMapper;
        //this.expertMapper = expertMapper;
    }

//    private final ExpertMapper expertMapper;
//    private final OrderMapper orderMapper;
//
//    public SubServiceMapperImpl(ServiceMapper serviceMapper, ExpertMapper expertMapper, OrderMapper orderMapper) {
//        this.serviceMapper = serviceMapper;
//        this.expertMapper = expertMapper;
//        this.orderMapper = orderMapper;
//    }

    @Override
    public SubServiceDto covertToSubServiceDto(SubService subService) {
        SubServiceDto subServiceDto = new SubServiceDto();
        subServiceDto.setId(subService.getId());
        subServiceDto.setName(subService.getName());
        subServiceDto.setBasePrice(subService.getBasePrice());
        subServiceDto.setDescription(subService.getDescription());
        subServiceDto.setService(serviceMapper.convertToServiceDto(subService.getService()));
        //subServiceDto.setExperts(subService.getExperts().stream().map(i->expertMapper.toExpertDto(i)).collect(Collectors.toList()));
        return subServiceDto;
    }

    @Override
    public SubService convertToSubService(SubServiceDto subServiceDto) {
        SubService subService = new SubService();
        subService.setId(subServiceDto.getId());
        subService.setName(subServiceDto.getName());
        subService.setBasePrice(subServiceDto.getBasePrice());
        subService.setDescription(subServiceDto.getDescription());
        subService.setService(serviceMapper.convertToService(subServiceDto.getService()));
        ///subService.setExperts(subServiceDto.getExperts().stream().map(i->expertMapper.toExpert(i)).collect(Collectors.toList()));
        return subService;
    }
}
