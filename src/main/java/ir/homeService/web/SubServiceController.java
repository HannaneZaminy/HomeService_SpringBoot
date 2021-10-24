package ir.homeService.web;

import ir.homeService.configuration.LastViewInterceptor;
import ir.homeService.dto.OfferDto;
import ir.homeService.dto.OrderDto;
import ir.homeService.dto.ServiceDto;
import ir.homeService.dto.SubServiceDto;
import ir.homeService.service.exception.DuplicatedDataException;
import ir.homeService.service.exception.NotFoundServiceException;
import ir.homeService.service.ServiceService;
import ir.homeService.service.SubServiceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("subService")
public class SubServiceController {
    private final SubServiceService subServiceService;
    private final ServiceService service;
    private final MessageSource messageSource;
    private final static Logger logger= LogManager.getLogger(SubServiceController.class);


    public SubServiceController(SubServiceService subServiceService, ServiceService service, MessageSource messageSource) {
        this.subServiceService = subServiceService;
        this.service = service;
        this.messageSource = messageSource;
    }

    public void saveNewSubService(SubServiceDto subServiceDto) throws DuplicatedDataException, NotFoundServiceException {
        subServiceService.saveNewSubService(subServiceDto);
    }

    @GetMapping("/addNewSubService")
    @PreAuthorize("hasRole('MANAGER')")
    public String addNewSubService(Model model) {
        model.addAttribute("newSubService", new SubServiceDto());
        model.addAttribute("serviceList", service.fetchAllServices());
        return "/subservice/createNewSubServicePage";
    }

    @PostMapping("/addNewSubService")
    @PreAuthorize("hasRole('MANAGER')")
    public String addNewSubService(@ModelAttribute("newSubService") @Valid SubServiceDto dto,Model model) throws DuplicatedDataException,
            NotFoundServiceException {
        subServiceService.saveNewSubService(dto);
        model.addAttribute("successAlert",messageSource.getMessage("add.new.sub.service",null,new Locale("en_us")));
        return "manager/managerHomePage";

    }

    @GetMapping("/getSubService")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String getSubServices(@RequestParam("service") String service, Model model,
                                 @SessionAttribute("serviceList") List<ServiceDto> serviceList
            , @SessionAttribute("newOrder") OrderDto dto,
                                 HttpServletRequest request) {
        List<String> subServices = subServiceService.getSubServicesByServiceName(service);
        model.addAttribute("newOrder", dto);
        model.addAttribute("subServiceList", subServices);
        model.addAttribute("serviceList", serviceList);
        model.addAttribute("selectedService", service);
        return "order/createNewOrderPage";
    }

    @ExceptionHandler(value = BindException.class)
    public ModelAndView validationHandler(BindException ex,HttpServletRequest request,Model object) {
        Map<String, Object> model = ex.getBindingResult().getModel();
        String lastView = (String) request.getSession().getAttribute(LastViewInterceptor.LAST_VIEW_ATTRIBUTE);
        ex.getFieldErrors().forEach(
                error -> model.put(error.getField(),
                        messageSource.getMessage(Objects.requireNonNull(error.getDefaultMessage()),
                                null, new Locale("en_us")))
        );
        System.out.println("");
        ex.getFieldErrors().forEach(
                error -> logger.info(
                        messageSource.getMessage(Objects.requireNonNull(error.getDefaultMessage()),
                                null, new Locale("en_us")))
        );
        object.addAttribute("serviceList", service.fetchAllServices());
        return new ModelAndView(lastView,model);
    }

    @ExceptionHandler({DuplicatedDataException.class, NotFoundServiceException.class})
    public ModelAndView errorHandler(Exception e, HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        model.put("errorAlert", e.getLocalizedMessage());
        model.put("newOffer", new OfferDto());
        String lastView = (String) request.getSession().getAttribute(LastViewInterceptor.LAST_VIEW_ATTRIBUTE);
        return new ModelAndView(lastView, model);
    }


}
