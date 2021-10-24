package ir.homeService.web;

import ir.homeService.dto.CustomerDto;
import ir.homeService.dto.OfferDto;
import ir.homeService.dto.OrderDto;
import ir.homeService.service.exception.NotFoundCustomerException;
import ir.homeService.service.exception.NotFoundExpertForSubServiceException;
import ir.homeService.service.exception.NotFoundOrderException;
import ir.homeService.configuration.LastViewInterceptor;
import ir.homeService.service.OrderService;
import ir.homeService.service.ServiceService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/order")
@SessionAttributes({"newOrder"})
public class OrderController {
    private final ServiceService service;
    private final OrderService orderService;
    private final MessageSource messageSource;
    private final RestTemplate template;


    public OrderController(ServiceService service, OrderService orderService, MessageSource messageSource, RestTemplate template) {
        this.service = service;
        this.orderService = orderService;
        this.messageSource = messageSource;
        this.template = template;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    @GetMapping("/createOrder")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String createOrder(Model model, HttpServletRequest request) {
        model.addAttribute("newOrder", new OrderDto());
        model.addAttribute("serviceList", service.fetchAllServices());
        model.addAttribute("selectedService", "select");
        HttpSession session = request.getSession();
        session.setAttribute("serviceList", service.fetchAllServices());
        session.setAttribute("newOrder", model.getAttribute("newOrder"));
        return "order/createNewOrderPage";
    }

    @PostMapping("/createOrder")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Object createNewOrder(@ModelAttribute("newOrder") @Valid OrderDto dto, Model model, @RequestParam("lat") String lat,
                                 @RequestParam("lng") String lng)
            throws NotFoundCustomerException, NotFoundExpertForSubServiceException {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail(getUser().getEmail());
        dto.setCustomer(customerDto);
        orderService.saveNewOrder(dto,lat,lng);
        model.addAttribute("successAlert", messageSource.getMessage("order.created", null, new Locale("en_us")));
        return "/customer/customerHomePage";
    }

    @GetMapping("/endOfWork/{id}")
    @PreAuthorize("hasRole('EXPERT')")
    public String endOfWork(@PathVariable("id") Integer id, Model model) throws NotFoundOrderException {
        orderService.endOfWork(id);
        model.addAttribute("successAlert", messageSource.getMessage("end.of.work", null, new Locale("en_us")));
        return "expert/expertHomePage";
    }

    @GetMapping("/confirmPay/{id}")
    @PreAuthorize("hasRole('EXPERT')")
    public String ConfirmPay(@PathVariable("id") Integer id, Model model) throws NotFoundOrderException {
        orderService.confirmPay(id);
        model.addAttribute("successAlert", messageSource.getMessage("confirm.pay", null, new Locale("en_us")));
        return "expert/expertHomePage";
    }

    @GetMapping("/startWork/{id}")
    @PreAuthorize("hasRole('EXPERT')")
    public String startWork(@PathVariable("id") Integer id, Model model) throws NotFoundOrderException {
        orderService.startWork(id);
        model.addAttribute("successAlert", messageSource.getMessage("start.work", null, new Locale("en_us")));
        return "expert/expertHomePage";
    }


    @ExceptionHandler({NotFoundCustomerException.class, NotFoundOrderException.class})
    public ModelAndView errorHandler(Exception e, HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        model.put("errorAlert", e.getLocalizedMessage());
        model.put("newOffer", new OfferDto());
        String lastView = (String) request.getSession().getAttribute(LastViewInterceptor.LAST_VIEW_ATTRIBUTE);
        System.out.println(lastView);
        return new ModelAndView(lastView, model);
    }

    @ExceptionHandler(NotFoundExpertForSubServiceException.class)
    public ModelAndView notFoundExpertForSubService(Exception e) {
        Map<String, Object> model = new HashMap<>();
        model.put("errorAlert", e.getLocalizedMessage());
        return new ModelAndView("customer/customerHomePage", model);
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

}
