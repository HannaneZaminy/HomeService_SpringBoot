package ir.homeService.service.mapper;

import ir.homeService.data.domain.Expert;
import ir.homeService.dto.ExpertDto;

public interface ExpertMapper {
    Expert toExpert(ExpertDto dto);

    ExpertDto toExpertDto(Expert expert);
}
