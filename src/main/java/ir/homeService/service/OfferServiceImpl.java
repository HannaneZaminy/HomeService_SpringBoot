package ir.homeService.service;

import ir.homeService.data.domain.Expert;
import ir.homeService.data.domain.Offers;
import ir.homeService.data.enums.OfferSituation;
import ir.homeService.data.enums.OrderSituation;
import ir.homeService.dto.*;
import ir.homeService.service.exception.*;
import ir.homeService.service.mapper.OrderMapper;
import ir.homeService.data.repository.OffersRepository;
import ir.maktab.dto.*;
import ir.maktab.service.exception.*;
import ir.homeService.service.mapper.ExpertMapper;
import ir.homeService.service.mapper.OfferMapper;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private final OffersRepository repository;
    private final OfferMapper mapper;
    private final ExpertService expertService;
    private final OrderService orderService;
    private final CustomerService customerService;
    private final OrderMapper orderMapper;
    private final ExpertMapper expertMapper;
    private final OrderHistoryService orderHistoryService;
    private final MessageSource messageSource;

    public OfferServiceImpl(OffersRepository repository, OfferMapper mapper, ExpertService expertService, OrderService orderService, CustomerService customerService, OrderMapper orderMapper1, ExpertMapper expertMapper, OrderHistoryService orderHistoryService, MessageSource messageSource) {
        this.repository = repository;
        this.mapper = mapper;
        this.expertService = expertService;
        this.orderService = orderService;
        this.customerService = customerService;
        this.orderMapper = orderMapper1;
        this.expertMapper = expertMapper;
        this.orderHistoryService = orderHistoryService;
        this.messageSource = messageSource;
    }

    @Override
    public void
    saveNewOffer(OfferDto dto) throws LessOfferPriceException, NotSubServiceInExpertsListException, NotFoundExpertException, NotFoundOrderException {
        ExpertDto expertDto = expertService.findByEmail(dto.getExpert().getEmail());
        OrderHistoryDto orderHistoryDto = new OrderHistoryDto();
        OrderDto orderDto = orderService.findById(dto.getOrders().getId());
        Double basePrice = orderDto.getSubService().getBasePrice();
        dto.setExpert(expertDto);
        dto.setOrders(orderDto);
        if (dto.getOfferPrice() < basePrice) {
            throw new LessOfferPriceException(messageSource.getMessage("less.offer.price", null, new Locale("en_us")));
        }
        if (!expertDto.getServices().contains(orderDto.getSubService())) {
            throw new NotSubServiceInExpertsListException(
                    messageSource.getMessage("not.sub.service.in.expert.list", null, new Locale("en_us")));
        }
        dto.getOrders().setSituation(OrderSituation.Waiting_for_expert_selection);
        OrderDto order = orderService.updateOrder(dto.getOrders());
        orderHistoryDto.setOrderDto(order);
        orderHistoryDto.setOrderSituation(OrderSituation.Waiting_for_expert_selection);
        orderHistoryService.save(orderHistoryDto);
        repository.save(mapper.toOffer(dto));
    }

    @Override
    public void deleteOffer(OfferDto dto) {
        repository.delete(mapper.toOffer(dto));

    }

    @Override
    public void updateOffer(OfferDto dto) {
        repository.save(mapper.toOffer(dto));

    }

    @Override
    public List<OfferDto> fetchAllOffers() {
        return repository.findAll()
                .stream().map(i -> mapper.toOfferDto(i))
                .collect(Collectors.toList());
    }

    public List<OfferDto> getOrderOffersSortByRateAndPrice(CustomerDto dto, Integer id) throws NotFoundCustomerException, NotFoundOrderException, NotFoundOfferForOrder {
        CustomerDto customerDto = customerService.findByEmail(dto.getEmail());
        List<OrderDto> orders = orderService.findByCustomer(customerDto).stream().filter(i -> i.getSituation().equals(
                OrderSituation.Waiting_for_expert_selection)).collect(Collectors.toList());
        List<OfferDto> offers = repository.findAll(Sort.by("expert.rate").and(Sort.by("offerPrice")))
                .stream().map(mapper::toOfferDto).collect(Collectors.toList());
        List<OfferDto> collect = new ArrayList<>();
        for (OrderDto order : orders) {
            for (OfferDto offer : offers) {
                if (order.getId().equals(offer.getOrders().getId())) {
                    collect.add(offer);
                }
            }
        }
        List<OfferDto> list = collect.stream().filter(i -> i.getOfferSituation().equals(OfferSituation.registered))
                .filter(i -> i.getOrders().getId().equals(id)).collect(Collectors.toList());

        if (list.size() == 0) {
            throw new NotFoundOfferForOrder(
                    messageSource.getMessage("not.found.offer.for.order", null, new Locale("en_us")));
        }
        return list;
    }

    @Override
    public void changeSituation(Integer id) throws NotFoundOrderException {
        OrderHistoryDto orderHistoryDto = new OrderHistoryDto();
        Optional<Offers> offer = repository.findById(id);
        offer.get().setOfferSituation(OfferSituation.accepted);
        OrderDto dto = orderService.findById(offer.get().getOrders().getId());
        orderHistoryDto.setOrderDto(dto);
        dto.setExpert(expertMapper.toExpertDto(offer.get().getExpert()));
        dto.setSituation(OrderSituation.Waiting_for_expert_to_come);
        orderHistoryDto.setOrderSituation(OrderSituation.Waiting_for_expert_to_come);
        orderHistoryService.save(orderHistoryDto);
        orderService.updateOrder(dto);
        updateOffer(mapper.toOfferDto(offer.get()));
    }


    @Override
    public OfferDto paymentFromAccountCredit(Integer id, CustomerDto dto)
            throws NotFoundOrderException, NotFoundCustomerException, NotEnoughAccountBalance {
        OrderHistoryDto orderHistoryDto = new OrderHistoryDto();
        OrderDto byId = orderService.findById(id);
        CustomerDto customerDto = customerService.findByEmail(dto.getEmail());
        Optional<Offers> offer = repository.findByOrders(orderMapper.toOrder(byId));
        Expert expert = offer.get().getExpert();
        if (offer.get().getOfferPrice() > customerDto.getCredit()) {
            throw new NotEnoughAccountBalance(
                    messageSource.getMessage("not.enough.balance", null, new Locale("en_us")));
        }
        offer.get().setOfferSituation(OfferSituation.DONE);
        repository.save(offer.get());
        byId.setSituation(OrderSituation.paid);
        orderHistoryDto.setOrderDto(byId);
        orderHistoryDto.setOrderSituation(OrderSituation.paid);
        orderHistoryService.save(orderHistoryDto);
        orderService.updateOrder(byId);
        customerDto.setCredit(customerDto.getCredit() - offer.get().getOfferPrice());
        customerService.updateCustomer(customerDto);
        expert.setCredit(expert.getCredit() + offer.get().getOfferPrice() * 0.7);
        expertService.updateExpert(expertMapper.toExpertDto(expert));
        return null;
    }

    @Override
    public void onlinePayment(OrderDto orderDto) throws NotFoundCustomerException {
        OrderHistoryDto orderHistoryDto = new OrderHistoryDto();
        Optional<Offers> offer = repository.findByOrders(orderMapper.toOrder(orderDto));
        Expert expert = offer.get().getExpert();
        offer.get().setOfferSituation(OfferSituation.DONE);
        repository.save(offer.get());
        orderDto.setSituation(OrderSituation.paid);
        orderHistoryDto.setOrderDto(orderDto);
        orderHistoryDto.setOrderSituation(OrderSituation.paid);
        orderHistoryService.save(orderHistoryDto);
        orderService.updateOrder(orderDto);
        expert.setCredit(expert.getCredit() + offer.get().getOfferPrice() * 0.7);
        expertService.updateExpert(expertMapper.toExpertDto(expert));
    }
}

