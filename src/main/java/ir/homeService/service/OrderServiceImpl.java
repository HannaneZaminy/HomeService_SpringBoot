package ir.homeService.service;

import ir.homeService.data.domain.Customer;
import ir.homeService.data.domain.Expert;
import ir.homeService.data.domain.Orders;
import ir.homeService.data.domain.SubService;
import ir.homeService.data.enums.OfferSituation;
import ir.homeService.data.enums.OrderSituation;
import ir.homeService.data.enums.UserSituation;
import ir.homeService.data.repository.*;
import ir.homeService.dto.*;
import ir.homeService.service.exception.*;
import ir.homeService.service.mapper.OrderMapper;
import ir.maktab.data.repository.*;
import ir.maktab.dto.*;
import ir.maktab.service.exception.*;
import ir.homeService.service.mapper.AddressMapper;
import ir.homeService.service.mapper.CustomerMapper;
import ir.homeService.service.mapper.SubServiceMapper;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final SubServiceRepository subServiceRepository;
    private final SubServiceMapper serviceMapper;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final ExpertRepository expertRepository;
    private final OrderHistoryService orderHistoryService;
    private final MessageSource messageSource;
    private final RestTemplate template;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;



    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper
            , SubServiceRepository subServiceRepository,
                            SubServiceMapper serviceMapper,
                            CustomerRepository customerRepository,
                            CustomerMapper customerMapper,
                            ExpertRepository expertRepository,
                            OrderHistoryService orderHistoryService, MessageSource messageSource, RestTemplate template, AddressRepository addressRepository, AddressMapper addressMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.subServiceRepository = subServiceRepository;
        this.serviceMapper = serviceMapper;
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.expertRepository = expertRepository;
        this.orderHistoryService = orderHistoryService;
        this.messageSource = messageSource;
        this.template = template;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public void saveNewOrder(OrderDto dto, String lat, String lon) throws NotFoundExpertForSubServiceException {
        Optional<SubService> subService = subServiceRepository.findByName(dto.getSubService().getName());
        if (subService.isPresent()) {
            dto.setSubService(serviceMapper.covertToSubServiceDto(subService.get()));
            if (subService.get().getExperts().size()==0){
                throw new NotFoundExpertForSubServiceException(
                        messageSource.getMessage("not.found.expert.for.sub.service",null,new Locale("en_us")));
            }
        }
        Optional<Customer> customer = customerRepository.findByEmail(dto.getCustomer().getEmail());
        if (customer.isPresent()) {
            dto.setCustomer(customerMapper.toCustomerDto(customer.get()));
        }
        AddressDto address = getAddress(lat, lon);
        dto.setSituation(OrderSituation.Waiting_for_expert_suggestions);
        dto.setAddress(address);
        Orders save = repository.save(mapper.toOrder(dto));
        OrderHistoryDto orderHistoryDto = new OrderHistoryDto();
        orderHistoryDto.setOrderDto(mapper.toOrderDto(save));
        orderHistoryDto.setOrderSituation(OrderSituation.Waiting_for_expert_suggestions);
        orderHistoryService.save(orderHistoryDto);

    }

    @Override
    public AddressDto getAddress(String lat, String lon) {
        String url = "https://map.ir/reverse/no?lat="+lat+"&lon="+lon;
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set("x-api-key", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjYxOTE4MGM3MWU1NzExODE0NDAzZTk3MTFiZjcxN2ZhODIwMTM5ZjViM2Y3MDNhYjVlOTcxZmRkZDZmMTMwNGYxOTRiMTliMjgwYjcwOTZjIn0.eyJhdWQiOiIxNDYyNiIsImp0aSI6IjYxOTE4MGM3MWU1NzExODE0NDAzZTk3MTFiZjcxN2ZhODIwMTM5ZjViM2Y3MDNhYjVlOTcxZmRkZDZmMTMwNGYxOTRiMTliMjgwYjcwOTZjIiwiaWF0IjoxNjI0NzM1NzI1LCJuYmYiOjE2MjQ3MzU3MjUsImV4cCI6MTYyNzMyNzcyNSwic3ViIjoiIiwic2NvcGVzIjpbImJhc2ljIl19.F1pDeSlV3lhpispxOjHdX0pwY80XYyzEMmqJYdRNbMm30LMMtWiswYSNjpgC11VOPEG9fJiljF355qmX_ZIq93xaOO2Ff1gNw6FUgackl5TW534wwP678L5zs-xFDncESn90lqKa4OCZh-TH_u5bq7BZMSymZVby-hRVQOd8l1K9Z2RYfgUYF5EoUPxMqffIERbPZJcniu0nSVHijegPaAqiO9uScr5kwhaHOh8Oar4HI-dNz2jvVGOaMU3ozafIhRDIp4vZuFp4LW-zpUbaaMQf2wtBDSzAfYqCd5BfU1Zt4ASPwTnnu-NPayKlvlXC36Ce45GCZ_O7JYZqSVq8fw");
        HttpEntity request = new HttpEntity(headers);


        ResponseEntity<AddressDto> response = template.exchange(
                url,
                HttpMethod.GET,
                request,
                AddressDto.class
        );
        return response.getBody();

    }

    @Override
    public void deleteOrder(OrderDto dto) {
        repository.delete(mapper.toOrder(dto));

    }

    @Override
    public OrderDto updateOrder(OrderDto dto) {
        Orders save = repository.save(mapper.toOrder(dto));
        return mapper.toOrderDto(save);

    }

    @Override
    public List<OrderDto> fetchAllOrders() {
        return repository.findAll()
                .stream().map(i -> mapper.toOrderDto(i))
                .collect(Collectors.toList());
    }

    @Override
    public void selectOffer(OrderDto orderDto, OfferDto dto) throws NotFoundOfferForOrder {
        boolean equals = dto.getOrders().equals(orderDto);
        if (equals) {
            dto.setOfferSituation(OfferSituation.accepted);
            orderDto.setSituation(OrderSituation.Waiting_for_expert_to_come);

        } else {
            throw new NotFoundOfferForOrder(
                    messageSource.getMessage("not.found.offer.for.order",null,new Locale("en_us")));
        }
    }

    @Override
    public List<OrderDto> findOrdersBaseOnExpertSubServicesAndSituation(ExpertDto expertDto) throws AccessException,
            NotFoundOrderForExpertException {
        Optional<Expert> expert = expertRepository.findByEmail(expertDto.getEmail());
        if (!expert.get().getUserSituation().equals(UserSituation.Accepted)){
            throw new AccessException(messageSource.getMessage("access.exception",null,new Locale("en_us")));
        }
        List<Orders> orders = repository.findOrdersBaseOnExpertSubServices(expert.get());
        if (orders.size()==0){
            throw new NotFoundOrderForExpertException(
                    messageSource.getMessage("not.found.order.for.expert",null,new Locale("en_us")));
        }
        return orders.stream().filter(i -> i.getSituation().equals(OrderSituation.Waiting_for_expert_suggestions))
                .map(i -> mapper.toOrderDto(i)).collect(Collectors.toList());
    }

    @Override
    public OrderDto findById(Integer id) throws NotFoundOrderException {
        Optional<Orders> order = repository.findById(id);
        if (order.isPresent()) {
            return mapper.toOrderDto(order.get());
        } else {
            throw new NotFoundOrderException(messageSource.getMessage("not.found.order",null,new Locale("en_us")));
        }
    }

    @Override
    public List<OrderDto> findByExpert(ExpertDto dto) throws NotFoundOrderException {
        Optional<Expert> expert= expertRepository.findByEmail(dto.getEmail());
        List<Orders> orders = repository.findByExpert(expert.get());
        if (orders.size() == 0) {
            throw new NotFoundOrderException(messageSource.getMessage("not.found.order",null,new Locale("en_us")));
        }
        return orders.stream().map(i -> mapper.toOrderDto(i)).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findByCustomer(CustomerDto dto) throws NotFoundOrderException{
        Optional<Customer> customerDto = customerRepository.findByEmail(dto.getEmail());
        List<Orders> orders = repository.findByCustomer(customerDto.get());
        if (orders.size() == 0) {
            throw new NotFoundOrderException(
                    messageSource.getMessage("not.found.order",null,new Locale("en_us")));
        }
        return orders.stream().map(i -> mapper.toOrderDto(i)).collect(Collectors.toList());
    }

    @Override
    public void endOfWork(Integer id) throws NotFoundOrderException {
        Optional<Orders> byId = repository.findById(id);
        if (!byId.isPresent()){
            throw new NotFoundOrderException(
                    messageSource.getMessage("not.found.order",null,new Locale("en_us")));
        }
        byId.get().setSituation(OrderSituation.DONE);
        updateOrder(mapper.toOrderDto(byId.get()));
        OrderHistoryDto orderHistoryDto = new OrderHistoryDto();
        orderHistoryDto.setOrderDto(mapper.toOrderDto(byId.get()));
        orderHistoryDto.setOrderSituation(OrderSituation.DONE);
        orderHistoryService.save(orderHistoryDto);
    }

    @Override
    public void confirmPay(Integer id) throws NotFoundOrderException {
        Optional<Orders> byId = repository.findById(id);
        if (!byId.isPresent()){
            throw new NotFoundOrderException(
                    messageSource.getMessage("not.found.order",null,new Locale("en_us")));
        }
        byId.get().setSituation(OrderSituation.FINISHED);
        OrderDto dto = updateOrder(mapper.toOrderDto(byId.get()));
        OrderHistoryDto orderHistoryDto = new OrderHistoryDto();
        orderHistoryDto.setOrderDto(dto);
        orderHistoryDto.setOrderSituation(OrderSituation.FINISHED);
        orderHistoryService.save(orderHistoryDto);
    }

    @Override
    public void startWork(Integer id) throws NotFoundOrderException {
        Optional<Orders> byId = repository.findById(id);
        if (!byId.isPresent()){
            throw new NotFoundOrderException(
                    messageSource.getMessage("not.found.order",null,new Locale("en_us")));
        }
        byId.get().setSituation(OrderSituation.STARTED);
        OrderDto dto = updateOrder(mapper.toOrderDto(byId.get()));
        OrderHistoryDto orderHistoryDto = new OrderHistoryDto();
        orderHistoryDto.setOrderDto(dto);
        orderHistoryDto.setOrderSituation(OrderSituation.STARTED);
        orderHistoryService.save(orderHistoryDto);
    }

    @Override
    public List<OrderDto> filterOrders(FilterOrdersDto dto) throws NotFoundOrderException {
        List<Orders> all = repository.findAll(Specification.where(OrderSpecification.filterOrders(dto)));

        List<OrderDto> collect = all.stream().map(i -> mapper.toOrderDto(i)).collect(Collectors.toList());
        if(collect.size()==0){
            throw new NotFoundOrderException(
                    messageSource.getMessage("not.found.order",null,new Locale("en_us")));
        }
        return collect;
    }

    @Override
    public List<String> situations() {
        List<String>  situations=new ArrayList<>();
        situations.add("Waiting_for_expert_suggestions");
        situations.add("Waiting_for_expert_selection");
        situations.add("Waiting_for_expert_to_come");
        situations.add("STARTED");
        situations.add("DONE");
        situations.add("paid");
        situations.add("FINISHED");
        return situations;
    }

    @Override
    public List<OrderDto> filterUserOrders(FilterSpecialUserOrdersDto dto) throws NotFoundOrderException {
        List<Orders> all = repository.findAll(Specification.where(UserOrderSpecification.filterOrders(dto)));
        List<OrderDto> collect = all.stream().map(i -> mapper.toOrderDto(i)).collect(Collectors.toList());
        if(collect.size()==0){
            throw new NotFoundOrderException(
                    messageSource.getMessage("not.found.order",null,new Locale("en_us")));
        }
        return collect;
    }




}


