package ir.homeService.service.mapper;

import ir.homeService.data.domain.Users;
import ir.homeService.dto.UserDto;

public interface UserMapper {
    Users toUser(UserDto dto);

    UserDto toUserDto(Users user);
}

