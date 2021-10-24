package ir.homeService.service.mapper;

import ir.homeService.data.domain.Users;
import ir.homeService.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public Users toUser(UserDto dto) {
        Users user = new Users();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setDate(dto.getDate());
        user.setCredit(dto.getCredit());
        user.setVerificationCode(dto.getVerificationCode());
        user.setUserRole(dto.getUserRole());
        user.setUserSituation(dto.getUserSituation());
        return user;
    }

    @Override
    public UserDto toUserDto(Users user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setDate(user.getDate());
        dto.setCredit(user.getCredit());
        dto.setVerificationCode(user.getVerificationCode());
        dto.setEnabled(user.isEnabled());
        dto.setUserSituation(user.getUserSituation());
        dto.setUserRole(user.getUserRole());
        return dto;
    }
}
