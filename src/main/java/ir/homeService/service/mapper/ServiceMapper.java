package ir.homeService.service.mapper;

import ir.homeService.data.domain.Service;
import ir.homeService.dto.ServiceDto;

public interface ServiceMapper {

    ServiceDto convertToServiceDto(Service service);

    Service convertToService(ServiceDto serviceDto);
}
