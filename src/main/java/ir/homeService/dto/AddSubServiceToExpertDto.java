package ir.homeService.dto;

import javax.validation.constraints.NotNull;

public class AddSubServiceToExpertDto {
    @NotNull(message = "sub.service.id.can.not.null")
    private Integer subServiceId;
    @NotNull(message = "expert.id.can.not.null")
    private Integer expertId;

    public Integer getSubServiceId() {
        return subServiceId;
    }

    public AddSubServiceToExpertDto setSubServiceId(Integer subServiceId) {
        this.subServiceId = subServiceId;
        return this;
    }

    public Integer getExpertId() {
        return expertId;
    }

    public AddSubServiceToExpertDto setExpertId(Integer expertId) {
        this.expertId = expertId;
        return this;
    }
}
