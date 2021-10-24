package ir.homeService.service;

import ir.homeService.dto.CustomerDto;
import ir.homeService.dto.OrderDto;
import ir.homeService.service.exception.DuplicatedEmailAddressException;
import ir.homeService.service.exception.InvalidPassword;
import ir.homeService.service.exception.NotFoundCustomerException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface CustomerService {
    CustomerDto registerCustomer(CustomerDto dto, String siteURL) throws DuplicatedEmailAddressException, UnsupportedEncodingException, MessagingException;
    public void sendVerificationEmail(CustomerDto dto, String siteURL) throws UnsupportedEncodingException, MessagingException;
    public boolean verify(String verificationCode);

    void updateCustomer(CustomerDto dto);

    void deleteCustomer(CustomerDto dto);

    List<CustomerDto> fetchAllCustomers();

    CustomerDto findByEmail(String email) throws NotFoundCustomerException;

    CustomerDto loginCustomer(CustomerDto dto) throws InvalidPassword, NotFoundCustomerException;

    void changePassword(CustomerDto dto);

    List<OrderDto> showOrders(CustomerDto dto);

    Double getBalance(CustomerDto user);
}
