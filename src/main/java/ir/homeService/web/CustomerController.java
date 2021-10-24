package ir.homeService.web;

import ir.homeService.dto.CreditCardInfo;
import ir.homeService.dto.CustomerDto;
import ir.homeService.dto.OrderDto;
import ir.homeService.service.exception.*;
import ir.homeService.service.validation.ChangePasswordValidation;
import ir.homeService.service.validation.RegisterValidation;
import ir.homeService.service.CustomerService;
import ir.homeService.service.OfferService;
import ir.homeService.service.OrderService;
import ir.maktab.service.exception.*;
import org.springframework.context.MessageSource;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Controller
@RequestMapping(value = "/customer")
@SessionAttributes("order")
public class CustomerController {
    private final CustomerService customerService;
    private final OrderService orderService;
    private final OfferService offerService;
    private final MessageSource messageSource;


    public CustomerController(CustomerService customerService, OrderService orderService, OfferService offerService, MessageSource messageSource) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.offerService = offerService;
        this.messageSource = messageSource;
    }

    @GetMapping("/customerHomePage")
    public String goToHomePage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setAttribute("customer",getUser());
        return "/customer/customerHomePage";
    }
    @GetMapping("/balance")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String getBalance(Model model){
        Double balance = customerService.getBalance(getUser());
        model.addAttribute("balance",balance);
        return "/customer/customerHomePage";
    }

    @PostMapping("/register")
    public String saveNewCustomer(@ModelAttribute("customer") @Validated(RegisterValidation.class) CustomerDto customerDto,
                                  Model model, HttpServletRequest request)
            throws DuplicatedEmailAddressException, UnsupportedEncodingException, MessagingException {
        CustomerDto customer = customerService.registerCustomer(customerDto, getSiteURL(request));
        model.addAttribute("credit", customer.getCredit());
        return "alert/register_success";
    }

    @GetMapping("/register")
    public ModelAndView goToRegisterPage() {
        return new ModelAndView("/customer/customerRegisterPage", "customer", new CustomerDto());
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (customerService.verify(code)) {
            return "alert/verify_success";
        } else {
            return "alert/verify_fail";
        }
    }


    @GetMapping("/changePassword")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String changePassword(Model model) {
        model.addAttribute("changePassword", new CustomerDto());
        return "/customer/customerPassChange";
    }


    @PostMapping("/changePassword")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String changePassword(HttpServletRequest request, @ModelAttribute("changePassword") @Validated(ChangePasswordValidation.class) CustomerDto dto, Model model) {
        dto.setEmail(getUser().getEmail());
        customerService.changePassword(dto);
        model.addAttribute("successAlert",messageSource.getMessage("password.changed",null,new Locale("en_us")));
        return "/customer/customerHomePage";
    }

    @GetMapping("/showOrders")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String showOrders(Model model, HttpServletRequest request)
            throws NotFoundOrderException,
            NotFoundCustomerException
    {
        model.addAttribute("ordersList", orderService.findByCustomer(getUser()));
        return "customer/showOrdersForCustomerHomePage";
    }


    @GetMapping("/showOffers/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String showOffers(Model model, @PathVariable("id") Integer id,HttpServletRequest request)
            throws NotFoundCustomerException,
            NotFoundOrderException,
            NotFoundOfferForOrder
    {
        model.addAttribute("offersList", offerService.getOrderOffersSortByRateAndPrice(getUser(), id));
        return "customer/showOffersForCustomerHomePage";
    }

    @GetMapping("/paymentFromAccountCredit/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String paymentFromAccountCredit(@PathVariable("id") Integer id, Model model,HttpServletRequest request)
            throws NotFoundCustomerException,
            NotEnoughAccountBalance,
            NotFoundOrderException
    {
        model.addAttribute("successAlert",messageSource.getMessage("pay.from.account.balance",null,new Locale("en_us")));
        offerService.paymentFromAccountCredit(id, getUser());
        return "/customer/customerHomePage";
    }

    @GetMapping("/onlinePayment/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ModelAndView onlinePayment(@PathVariable("id") Integer id, Model model) throws NotFoundOrderException
    {
        OrderDto orderDto = orderService.findById(id);
        model.addAttribute("order", orderDto);
        return new ModelAndView("customer/onlinePaymentPage", "onlinePayment", new CreditCardInfo());
    }

    @PostMapping("/onlinePayment")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String onlinePayment(HttpServletRequest request,@ModelAttribute("onlinePayment") @Valid CreditCardInfo info, Model model) throws NotFoundCustomerException {
        HttpSession session = request.getSession(false);
        OrderDto orderDto = (OrderDto) session.getAttribute("order");
        String captcha = session.getAttribute("captcha_security").toString();
        String verifyCaptcha = request.getParameter("captcha");
        if (captcha.equals(verifyCaptcha)) {
            model.addAttribute("onlinePayment", info);
            offerService.onlinePayment(orderDto);
        } else {
            model.addAttribute("error", "Captcha Invalid");
            return "customer/onlinePaymentPage";
        }
        model.addAttribute("successAlert",messageSource.getMessage("pay.from.credit",null,new Locale("en_us")));
        return "/customer/customerHomePage";
    }

    @ExceptionHandler(value = {NotFoundOrderException.class, NotFoundOfferForOrder.class})
    public String handleNotFoundOrder(Exception e, Model model) {
        model.addAttribute("errorAlert", e.getLocalizedMessage());
        return "customer/customerHomePage";

    }

    public CustomerDto getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        CustomerDto dto = new CustomerDto();
        dto.setEmail(userName);
        return dto;
    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    public void setSession(HttpServletRequest request,CustomerDto dto){
        HttpSession session = request.getSession(false);
        session.setAttribute("customer",dto);

    }

}
