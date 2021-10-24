package ir.homeService.web;

import ir.homeService.configuration.LastViewInterceptor;
import ir.homeService.dto.ExpertDto;
import ir.homeService.dto.OfferDto;
import ir.homeService.dto.OrderDto;
import ir.homeService.service.exception.LessOfferPriceException;
import ir.homeService.service.exception.NotFoundExpertException;
import ir.homeService.service.exception.NotFoundOrderException;
import ir.homeService.service.exception.NotSubServiceInExpertsListException;
import ir.homeService.service.OfferService;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("offer")
@SessionAttributes({"newOffer"})
public class OfferController {
    private final OfferService offerService;
    private final MessageSource messageSource;

    public OfferController(OfferService offerService, MessageSource messageSource) {
        this.offerService = offerService;
        this.messageSource = messageSource;
    }


    @GetMapping("/sendOffer/{id}")
    @PreAuthorize("hasRole('EXPERT')")
    public ModelAndView sendOffer(@PathVariable("id") Integer id) {
        OfferDto offerDto = new OfferDto();
        OrderDto dto = new OrderDto();
        dto.setId(id);
        offerDto.setOrders(dto);
        offerDto.setExpert(getUser());
        return new ModelAndView("/offer/createNewOfferPage", "newOffer", offerDto);
    }


    @PostMapping("/createOffer")
    @PreAuthorize("hasRole('EXPERT')")
    public String createOffer(@ModelAttribute("newOffer") @Valid OfferDto dto, HttpServletRequest request)
            throws LessOfferPriceException,
            NotSubServiceInExpertsListException,
            NotFoundExpertException,
            NotFoundOrderException {

        HttpSession session = request.getSession(false);
        OfferDto newOffer = (OfferDto) session.getAttribute("newOffer");
        dto.setExpert(newOffer.getExpert());
        dto.getOrders().setId(newOffer.getOrders().getId());
        offerService.saveNewOffer(dto);
        return "expert/expertHomePage";
    }


    @GetMapping("/selectOffer/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String changeSituation(@PathVariable("id") Integer id)
            throws NotFoundOrderException {
        offerService.changeSituation(id);
        return "/customer/customerHomePage";

    }


    @ExceptionHandler({LessOfferPriceException.class, NotSubServiceInExpertsListException.class
            , NotFoundExpertException.class, NotFoundOrderException.class})
    public ModelAndView errorHandler(Exception e, HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        model.put("errorAlert", e.getLocalizedMessage());
        model.put("newOffer", new OfferDto());
        String lastView = (String) request.getSession().getAttribute(LastViewInterceptor.LAST_VIEW_ATTRIBUTE);
        System.out.println(lastView);
        return new ModelAndView(lastView, model);
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
}
