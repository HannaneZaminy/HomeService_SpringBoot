package ir.homeService.service;

import ir.homeService.data.domain.Manager;
import ir.homeService.data.repository.ManagerRepository;
import ir.homeService.dto.ManagerDto;
import ir.homeService.service.exception.InvalidPassword;
import ir.homeService.service.exception.NotFoundManagerException;
import ir.homeService.service.mapper.ManagerMapper;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepository repository;
    private final ManagerMapper mapper;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;

    public ManagerServiceImpl(ManagerRepository repository, ManagerMapper mapper, MessageSource messageSource, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.messageSource = messageSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveNewManager(ManagerDto dto) {
        repository.save(mapper.toManager(dto));
    }

    @Override
    public void deleteManager(ManagerDto dto) {
        repository.delete(mapper.toManager(dto));
    }

    @Override
    public void updateManager(ManagerDto dto) {
        repository.save(mapper.toManager(dto));
    }

    @Override
    public List<ManagerDto> fetchAllManagers() {
        return repository.findAll()
                .stream().map(mapper::toManagerDto)
                .collect(Collectors.toList());
    }

    @Override
    public ManagerDto findByUserName(String userName) {
        Optional<Manager> manager = repository.findByUserName(userName);
        if (manager.isPresent()) {
            return mapper.toManagerDto(manager.get());
        }
        return null;
    }

    @Override
    public ManagerDto loginManager(ManagerDto dto) throws NotFoundManagerException, InvalidPassword {
        Optional<Manager> manager = repository.findByUserName(dto.getUserName());
        if (manager.isPresent()) {
            Manager correctManager = manager.get();

            if (passwordEncoder.matches(correctManager.getPassword(), dto.getPassword())) {
                return mapper.toManagerDto(correctManager);
            } else {
                throw new InvalidPassword(messageSource.getMessage("invalid.password",null,new Locale("en_us")));
            }
        } else {
            throw new NotFoundManagerException(messageSource.getMessage("not.found.manager",null,new Locale("en_us")));
        }
    }
}

