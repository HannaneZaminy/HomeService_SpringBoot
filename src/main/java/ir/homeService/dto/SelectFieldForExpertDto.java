package ir.homeService.dto;

public class SelectFieldForExpertDto {
    private ExpertDto expertDto;
    private SubServiceDto subServiceDto;

    public ExpertDto getExpertDto() {
        return expertDto;
    }

    public SelectFieldForExpertDto setExpertDto(ExpertDto expertDto) {
        this.expertDto = expertDto;
        return this;
    }

    public SubServiceDto getSubServiceDto() {
        return subServiceDto;
    }

    public SelectFieldForExpertDto setSubServiceDto(SubServiceDto subServiceDto) {
        this.subServiceDto = subServiceDto;
        return this;
    }
}
