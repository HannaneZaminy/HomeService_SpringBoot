package ir.homeService.service;

import ir.homeService.dto.FilterUsersBaseOnNumOfOrdersDto;
import ir.homeService.dto.FilterUsersDto;
import ir.homeService.dto.UserDto;
import ir.homeService.service.exception.NotFoundExpertException;
import ir.homeService.service.exception.NotFoundUserException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    List<UserDto> fetchAllUsers();

    void save(UserDto userDto,String siteURL) throws UnsupportedEncodingException, MessagingException;
    void sendVerificationEmail(UserDto user, String siteURL) throws MessagingException, UnsupportedEncodingException;

    //    void changePassword(UserDto dto);
//    List<UserDto> findByProperty(SearchCustomerDto dto);
    List<UserDto> filterUsers(FilterUsersDto dto);
    List<UserDto> userHistory(FilterUsersBaseOnNumOfOrdersDto dto) throws NotFoundUserException;

    UserDto confirmUser(Integer id) throws NotFoundExpertException;
    UserDto findById(Integer id) throws NotFoundUserException;


}
