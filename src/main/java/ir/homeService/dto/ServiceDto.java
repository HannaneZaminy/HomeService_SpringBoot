package ir.homeService.dto;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceDto {
    private Integer id;
    @NotBlank(message = "name")
    private String name;
    private List<SubServiceDto> subServices = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubServiceDto> getSubServices() {
        return subServices;
    }

    public ServiceDto setSubServices(List<SubServiceDto> subServices) {
        this.subServices = subServices;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceDto)) return false;
        ServiceDto that = (ServiceDto) o;
        return getId().equals(that.getId()) && getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
