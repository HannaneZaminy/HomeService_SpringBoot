package ir.homeService.web;

import ir.homeService.dto.AddSubServiceToExpertDto;
import ir.homeService.dto.FilterOrdersDto;
import ir.homeService.dto.ManagerDto;
import ir.homeService.service.*;
import ir.homeService.service.exception.DuplicatedSubServiceException;
import ir.homeService.service.exception.InvalidPassword;
import ir.homeService.service.exception.NotFoundManagerException;
import ir.homeService.configuration.LastViewInterceptor;
import ir.maktab.service.*;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping(value = "/managerPage")
@SessionAttributes({"correctManager"})
public class ManagerController {
    private final ManagerService managerService;
    private final MessageSource messageSource;
    private final SubServiceService subServiceService;
    private final ServiceService serviceService;
    private final OrderService orderService;
    private final ExpertService expertService;
    private final SecurityService securityService;

    public ManagerController(ManagerService managerService, MessageSource messageSource, SubServiceService subServiceService, ServiceService serviceService, OrderService orderService, ExpertService expertService, SecurityService securityService) {
        this.managerService = managerService;
        this.messageSource = messageSource;
        this.subServiceService = subServiceService;
        this.serviceService = serviceService;
        this.orderService = orderService;
        this.expertService = expertService;
        this.securityService = securityService;
    }

    @GetMapping ("/managerHomePage")
    @PreAuthorize("hasRole('MANAGER')")
    public String goToHome(){
        return "manager/managerHomePage";
    }


    @PostMapping("/login")
//    @PreAuthorize("hasRole('MANAGER')")
    public String getSignIn(@ModelAttribute("manager") @Valid ManagerDto managerDto, HttpServletRequest request)
            throws NotFoundManagerException, InvalidPassword {
        HttpSession session = request.getSession(false);
        session.setAttribute("manager",managerDto);
        return "manager/managerHomePage";
    }
    @GetMapping(value = "/orderHistoryPage")
    public String goToSearchInOrdersPage(Model model){
        model.addAttribute("orderHistoryList",new FilterOrdersDto());
        model.addAttribute("subServiceList",subServiceService.fetchAllSubServices());
        model.addAttribute("serviceList",serviceService.fetchAllServices());
        model.addAttribute("situationList",orderService.situations());
        String loggedInUsername = securityService.findLoggedInUsername();
        return "manager/filterOrders";
    }

    @GetMapping(value = "/filterUserBaseOnNumOfOrders")
    public String goToFilterPage(){
        return "manager/filterUsersBaseOnNumOfOrders";
    }


    @GetMapping("/addSubServiceToExert")
    public String addSubServiceToExpert(Model model){
        model.addAttribute("addSubServiceToExpert",new AddSubServiceToExpertDto());
        model.addAttribute("expertList",expertService.fetchAllExperts());
        model.addAttribute("subServiceList",subServiceService.fetchAllSubServices());
        return "manager/addSubServiceToExpert";
    }
    @PostMapping("/addSubServiceToExert")
    public String addSubServiceToExpert(@ModelAttribute("addSubServiceToExpert")@Valid AddSubServiceToExpertDto dto,Model model) throws DuplicatedSubServiceException {
        expertService.addSubServiceToExpertList(dto);
        model.addAttribute("successAlert",
                messageSource.getMessage("sub.Service.added.to.expert.list",null,new Locale("en_us")));
        return "manager/managerHomePage";
    }




    @ExceptionHandler({NotFoundManagerException.class, InvalidPassword.class})
    public ModelAndView errorHandler(Exception e, HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        model.put("errorAlert", e.getLocalizedMessage());
        model.put("manager", new ManagerDto());
        String lastView = (String) request.getSession().getAttribute(LastViewInterceptor.LAST_VIEW_ATTRIBUTE);
        return new ModelAndView(lastView, model);
    }

    @ExceptionHandler(DuplicatedSubServiceException.class)
    public ModelAndView duplicatedSubServiceException(Exception e) {
        Map<String, Object> model = new HashMap<>();
        model.put("errorAlert", e.getLocalizedMessage());
        return new ModelAndView("manager/managerHomePage", model);
    }
}
