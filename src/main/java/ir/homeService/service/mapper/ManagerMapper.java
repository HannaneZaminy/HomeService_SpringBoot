package ir.homeService.service.mapper;

import ir.homeService.data.domain.Manager;
import ir.homeService.dto.ManagerDto;

public interface ManagerMapper {
    Manager toManager(ManagerDto dto);

    ManagerDto toManagerDto(Manager manager);
}
