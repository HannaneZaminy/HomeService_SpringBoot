package ir.homeService.service;

import ir.homeService.data.domain.Customer;
import ir.homeService.data.domain.Users;
import ir.homeService.data.repository.CustomerRepository;
import ir.homeService.data.repository.UserRepository;
import ir.homeService.dto.CustomerDto;
import ir.homeService.dto.OrderDto;
import ir.homeService.service.exception.DuplicatedEmailAddressException;
import ir.homeService.service.exception.InvalidPassword;
import ir.homeService.service.exception.NotFoundCustomerException;
import ir.homeService.service.mapper.OrderMapper;
import ir.homeService.service.mapper.CustomerMapper;
import net.bytebuddy.utility.RandomString;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final OrderMapper orderMapper;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final UserRepository repository;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, OrderMapper orderMapper, MessageSource messageSource, PasswordEncoder passwordEncoder, JavaMailSender mailSender, UserRepository repository) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.orderMapper = orderMapper;
        this.messageSource = messageSource;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.repository = repository;
    }

    @Override
    public CustomerDto registerCustomer(CustomerDto dto, String siteURL) throws DuplicatedEmailAddressException, UnsupportedEncodingException, MessagingException {
        Optional<Customer> customer = customerRepository.findByEmail(dto.getEmail());
        if (customer.isPresent()) {
            throw new DuplicatedEmailAddressException(
                    messageSource.getMessage("duplicated.email.address",null,new Locale("en_us")));
        }
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);

        String randomCode = RandomString.make(64);
        dto.setVerificationCode(randomCode);
        dto.setEnabled(false);

        customerRepository.save(customerMapper.toCustomer(dto));
        sendVerificationEmail(dto, siteURL);
        return dto;
    }

    @Override
    public void sendVerificationEmail(CustomerDto dto, String siteURL)
            throws UnsupportedEncodingException, MessagingException {
        String toAddress = dto.getEmail();
        String fromAddress = "zahra.asgr1996@gmail.com";
        String senderName = "zahra asgari";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "zahra asgari...";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", dto.getName());
        String verifyURL = siteURL + "/customer/verify?code=" + dto.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public boolean verify(String verificationCode) {
        Users user = repository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            repository.save(user);
            return true;
        }
    }

    @Override
    public void updateCustomer(CustomerDto dto) {
        customerRepository.save(customerMapper.toCustomer(dto));

    }

    @Override
    public void deleteCustomer(CustomerDto dto) {
        customerRepository.delete(customerMapper.toCustomer(dto));

    }

    @Override
    public List<CustomerDto> fetchAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto findByEmail(String email) throws NotFoundCustomerException {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            return customerMapper.toCustomerDto(customer.get());
        }
        throw new NotFoundCustomerException(messageSource.getMessage("not.found.customer",null,new Locale("en_us")));
    }

    @Override
    public CustomerDto loginCustomer(CustomerDto dto) throws InvalidPassword, NotFoundCustomerException {
        Optional<Customer> customer = customerRepository.findByEmail(dto.getEmail());
        if (customer.isPresent()) {
            if (passwordEncoder.matches(customer.get().getPassword(), dto.getPassword())) {
                return customerMapper.toCustomerDto(customer.get());
            } else {
                throw new InvalidPassword(messageSource.getMessage("invalid.password",null,new Locale("en_us")));
            }
        } else {
            throw new NotFoundCustomerException(messageSource.getMessage("not.found.customer",null,new Locale("en_us")));
        }
    }

    @Override
    public void changePassword(CustomerDto dto) {
        Optional<Customer> customer = customerRepository.findByEmail(dto.getEmail());
        Customer customer1 = customer.get();
        customer1.setPassword(passwordEncoder.encode(dto.getPassword()));
        customerRepository.save(customer1);
    }

    @Override
    public List<OrderDto> showOrders(CustomerDto dto) {
        Optional<Customer> customer = customerRepository.findByEmail(dto.getEmail());
        return customer.get().getOrders().stream().map(i->orderMapper.toOrderDto(i)).collect(Collectors.toList());
    }

    @Override
    public Double getBalance(CustomerDto user) {
        Optional<Customer> customer = customerRepository.findByEmail(user.getEmail());
        return customer.get().getCredit();
    }
}
