package ir.homeService.web;

import ir.homeService.configuration.LastViewInterceptor;
import ir.homeService.dto.CustomerDto;
import ir.homeService.dto.FilterSpecialUserOrdersDto;
import ir.homeService.dto.FilterUsersDto;
import ir.homeService.dto.UserDto;
import ir.homeService.service.exception.NotFoundCustomerException;
import ir.homeService.service.exception.NotFoundExpertException;
import ir.homeService.service.exception.NotFoundOrderException;
import ir.homeService.service.exception.NotFoundUserException;
import ir.maktab.dto.*;
import ir.homeService.service.OrderService;
import ir.homeService.service.UserService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
@RequestMapping(value = "user")
@SessionAttributes({"searchUser","filterUserOrders"})
public class UserController {
    private final UserService userService;
    private final MessageSource messageSource;
    private final OrderService orderService;


    public UserController(UserService userService, MessageSource messageSource, OrderService orderService) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.orderService = orderService;
    }

    @InitBinder
    public void allowEmptyDateBinding(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }


    @PostMapping(value = "/searchUser")
    public ModelAndView searchUsers(@ModelAttribute("users") FilterUsersDto dto) {
        return new ModelAndView("manager/filterUsers", "usersList", userService.filterUsers(dto));

    }

    @GetMapping(value = "/searchUser")
    public String searchUsers(Model model) {
        model.addAttribute("users", new FilterUsersDto());
        return "manager/filterUsers";
    }

    @GetMapping(value = "/confirmUser/{id}")
    public String confirmUser(Model model,@PathVariable("id") Integer id) throws NotFoundExpertException {
        UserDto confirmUser = userService.confirmUser(id);
        model.addAttribute("confirmUser",confirmUser);
        model.addAttribute("successAlert",messageSource.getMessage("confirm.user",null,new Locale("en_us")));
        return "manager/managerHomePage";
    }

    @GetMapping(value = "/searchUser/{id}")
    public String searchUser(Model model,@PathVariable("id") Integer id) throws NotFoundUserException, NotFoundOrderException, NotFoundCustomerException {
        UserDto userDto = userService.findById(id);
        FilterSpecialUserOrdersDto dto = new FilterSpecialUserOrdersDto();
        CustomerDto customerDto=new CustomerDto();
        customerDto.setEmail(userDto.getEmail());
        orderService.findByCustomer(customerDto);
        dto.setUserId(userDto.getId());
        dto.setRole(userDto.getUserRole());
        model.addAttribute("filterUserOrders",dto);
        model.addAttribute("situationList",orderService.situations());
        return "manager/filterSpecialUserOrders";
    }


    @ExceptionHandler(value = NotFoundExpertException.class)
    public String handleException(Exception e, Model model, HttpServletRequest request){
        model.addAttribute("errorAlert",e.getLocalizedMessage());
        String lastView= (String) request.getSession().getAttribute(LastViewInterceptor.LAST_VIEW_ATTRIBUTE);
        return lastView;
    }

    @ExceptionHandler(value = NotFoundOrderException.class)
    public String handleNotFoundOrder(Exception e, Model model, HttpServletRequest request){
        model.addAttribute("errorAlert",e.getLocalizedMessage());
        return "manager/managerHomePage";
    }
}
