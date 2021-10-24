package ir.homeService.service.mapper;

import ir.homeService.data.domain.SubService;
import ir.homeService.dto.SubServiceDto;

public interface SubServiceMapper {

    SubServiceDto covertToSubServiceDto(SubService subService);

    SubService convertToSubService(SubServiceDto subServiceDto);
}
