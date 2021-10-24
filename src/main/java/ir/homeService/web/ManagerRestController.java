package ir.homeService.web;


import ir.homeService.dto.*;
import ir.homeService.service.exception.NotFoundOrderException;
import ir.homeService.service.exception.NotFoundUserException;
import ir.maktab.dto.*;
import ir.homeService.service.OfferService;
import ir.homeService.service.OrderService;
import ir.homeService.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/managerRestController")
@RestController
public class ManagerRestController {
    private final OrderService orderService;
    private final OfferService offerService;
    private final UserService userService;
    private final static Logger logger= LogManager.getLogger(ExceptionControllerAdvise.class);

    public ManagerRestController(OrderService orderService, OfferService offerService, UserService userService) {
        this.orderService = orderService;
        this.offerService = offerService;
        this.userService = userService;
    }
    @PostMapping(value = "/filterOrders",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> filterOrders(@RequestBody @Valid FilterOrdersDto dto)
            throws NotFoundOrderException {
        List<OrderDto> list = orderService.filterOrders(dto);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/filterUserOrders",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> filterUserOrders(@RequestBody @Valid FilterSpecialUserOrdersDto dto, HttpServletRequest request)
            throws NotFoundOrderException {
        FilterSpecialUserOrdersDto filterUserOrders = (FilterSpecialUserOrdersDto) request.getSession(false).getAttribute("filterUserOrders");
        dto.setUserId(filterUserOrders.getUserId());
        dto.setRole(filterUserOrders.getRole());
        List<OrderDto> list = orderService.filterUserOrders(dto);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/filterUsers",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> filterUsers(@RequestBody @Valid FilterUsersBaseOnNumOfOrdersDto dto) throws NotFoundUserException {
        List<UserDto> list = userService.userHistory(dto);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<Object> bindExceptionHandler(BindException ex) {
        List<String> validationErrors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            validationErrors.add(error.getField() + ": " + error.getDefaultMessage());
        });
        ApiErrorDto dto = new ApiErrorDto(HttpStatus.BAD_REQUEST, ex.getClass().getName(), validationErrors);
        return new ResponseEntity<>(dto, dto.getStatus());
    }



}
