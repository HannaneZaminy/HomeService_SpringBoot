package ir.homeService.service;

import ir.homeService.dto.ServiceDto;
import ir.homeService.service.exception.DuplicatedDataException;

import java.util.List;

public interface ServiceService {
    void saveNewService(ServiceDto dto) throws DuplicatedDataException;

    ServiceDto getService(ServiceDto dto);

    void deleteService(ServiceDto dto);

    void updateService(ServiceDto dto);

    List<ServiceDto> fetchAllServices();
}
