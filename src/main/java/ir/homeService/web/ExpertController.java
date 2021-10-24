package ir.homeService.web;

import ir.homeService.dto.SubServiceDto;
import ir.homeService.service.exception.*;
import ir.homeService.service.validation.RegisterValidation;
import ir.homeService.dto.ExpertDto;
import ir.homeService.service.ExpertService;
import ir.homeService.service.OrderService;
import ir.homeService.service.SubServiceService;
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
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping(value = "/expert")
public class ExpertController {
    private final ExpertService expertService;
    private final SubServiceService subServiceService;
    private final OrderService orderService;
    private final MessageSource messageSource;

    public ExpertController(ExpertService expertService, SubServiceService subServiceService, OrderService orderService, MessageSource messageSource) {
        this.expertService = expertService;
        this.subServiceService = subServiceService;
        this.orderService = orderService;
        this.messageSource = messageSource;
    }

    @GetMapping("/expertHomePage")
    public String goToHomePage(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        session.setAttribute("expert",getUser());
        return "expert/expertHomePage";
    }
    @GetMapping("/balance")
    @PreAuthorize("hasRole('EXPERT')")
    public String getBalance(Model model){
        Double balance = expertService.getBalance(getUser());
        model.addAttribute("balance",balance);
        return "expert/expertHomePage";
    }


    @GetMapping("/register")
    public ModelAndView goToExpertRegisterPage() {
        return new ModelAndView("expert/expertRegisterPage", "expert", new ExpertDto());
    }

    @PostMapping(value = "/register")
    public String registerExpert(@ModelAttribute("expert") @Validated(RegisterValidation.class) ExpertDto expertDto,
                                 Model model,HttpServletRequest request)
            throws DuplicatedEmailAddressException, UnsupportedEncodingException, MessagingException {
        ExpertDto expert = expertService.registerExpert(expertDto,getSiteURL(request));
        model.addAttribute("credit",expert.getCredit());
        return "alert/register_success";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (expertService.verify(code)) {
            return "alert/verify_success";
        } else {
            return "alert/verify_fail";
        }
    }

    @GetMapping("/changePassword")
    @PreAuthorize("hasRole('EXPERT')")
    public String changePassword(Model model) {
        model.addAttribute("changePassword", new ExpertDto());
        return "expert/expertPassChange";
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasRole('EXPERT')")
    public String changePassword(Model model, @ModelAttribute("changePassword") ExpertDto dto,HttpServletRequest request) {
        dto.setEmail(getUser().getEmail());
        expertService.changePassword(dto);
        model.addAttribute("successAlert",messageSource.getMessage("password.changed",null,new Locale("en_us")));
        return "expert/expertHomePage";
    }

    @GetMapping("/selectField")
    @PreAuthorize("hasRole('EXPERT')")
    public String selectField(Model model) {
        model.addAttribute("listOfFields", subServiceService.fetchAllSubServices());
        return "expert/selectFieldForExpert";
    }


    @GetMapping("/selectField/{id}")
    @PreAuthorize("hasRole('EXPERT')")
    public String selectField(Model model, @PathVariable("id") Integer id,HttpServletRequest request)
            throws NotFoundExpertException,
            NotFoundSubServiceException, DuplicatedSubServiceException {

        SubServiceDto subServiceDto = new SubServiceDto();
        subServiceDto.setId(id);
        expertService.addExpertToSubService(subServiceDto, getUser());
        model.addAttribute("successAlert",messageSource.getMessage("field.select",null,new Locale("en_us")));
        return "expert/expertHomePage";
    }

    @GetMapping("/showOrders")
    @PreAuthorize("hasRole('EXPERT')")
    public String showOrders(HttpServletRequest request, Model model) throws AccessException,
            NotFoundOrderForExpertException {
        model.addAttribute("ordersList", orderService.findOrdersBaseOnExpertSubServicesAndSituation(getUser()));
        return "expert/showOrdersForExpertToSendOffer";
    }

    @GetMapping("/showOrdersToClickEndOfWork")
    @PreAuthorize("hasRole('EXPERT')")
    public String showSuggestion(Model model, HttpServletRequest request)
            throws NotFoundOrderException {
        model.addAttribute("ordersList", orderService.findByExpert(getUser()));
        return "expert/showOrdersForExpertToEndOfWork";
    }

    @ExceptionHandler(value = AccessException.class)
    public String accessException(Exception e,Model model){
        model.addAttribute("accessException",e.getLocalizedMessage());
        return "expert/expertHomePage";
    }

    @ExceptionHandler(value = {NotFoundOrderForExpertException.class,NotFoundOrderException.class})
    public String notFoundOrderForExpertException(Exception e,Model model){
        model.addAttribute("errorAlert",e.getLocalizedMessage());
        return "expert/expertHomePage";
    }

    @ExceptionHandler(DuplicatedSubServiceException.class)
    public ModelAndView duplicatedSubServiceException(Exception e) {
        Map<String, Object> model = new HashMap<>();
        model.put("errorAlert", e.getLocalizedMessage());
        return new ModelAndView("expert/expertHomePage", model);
    }

    public ExpertDto getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        ExpertDto dto = new ExpertDto();
        dto.setEmail(userName);
        return dto;
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
