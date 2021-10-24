package ir.homeService.service;

import ir.homeService.data.repository.ServiceRepository;
import ir.homeService.dto.ServiceDto;
import ir.homeService.service.exception.DuplicatedDataException;
import ir.homeService.service.mapper.ServiceMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final MessageSource messageSource;

    public ServiceServiceImpl(ServiceRepository serviceRepository, ServiceMapper serviceMapper, MessageSource messageSource) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
        this.messageSource = messageSource;
    }

    @Override
    public void saveNewService(ServiceDto serviceDto) throws DuplicatedDataException {
        if (serviceRepository.findByName(serviceDto.getName()) != null) {
            throw new DuplicatedDataException(messageSource.getMessage("duplicated.data",null,new Locale("en_us")));
        } else {
            serviceRepository.save(serviceMapper.convertToService(serviceDto));
        }
    }

    @Override
    public ServiceDto getService(ServiceDto dto) {
        return null;
    }

    @Override
    public void deleteService(ServiceDto dto) {
        serviceRepository.delete(serviceMapper.convertToService(dto));
    }

    @Override
    public void updateService(ServiceDto dto) {
        serviceRepository.save(serviceMapper.convertToService(dto));
    }

    @Override
    public List<ServiceDto> fetchAllServices() {
        return serviceRepository.findAll()
                .stream().map(serviceMapper::convertToServiceDto)
                .collect(Collectors.toList());
    }
}
