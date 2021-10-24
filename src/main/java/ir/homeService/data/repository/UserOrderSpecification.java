package ir.homeService.data.repository;

import ir.homeService.data.domain.Expert;
import ir.homeService.data.enums.OrderSituation;
import ir.homeService.data.enums.UserRole;
import ir.homeService.dto.FilterSpecialUserOrdersDto;
import ir.homeService.data.domain.Customer;
import ir.homeService.data.domain.Orders;
import ir.homeService.data.domain.SubService;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public interface UserOrderSpecification {
    static Specification<Orders> filterOrders(FilterSpecialUserOrdersDto dto){
        return (root, criteriaQuery, criteriaBuilder) -> {
            CriteriaQuery<Orders> query = criteriaBuilder.createQuery(Orders.class);
            Join<Orders, Customer> customer = root.join("customer");
            Join<Orders, SubService> subService = root.join("subService");
            List<Predicate> predicates=new ArrayList<>();
            if (dto.getUserId()!=null && dto.getRole().equals(UserRole.EXPERT)){
                Join<Orders, Expert> expert = root.join("expert");
                predicates.add(criteriaBuilder.equal(expert.get("id"),dto.getUserId()));
            }
            if (dto.getUserId()!=null && dto.getRole().equals(UserRole.CUSTOMER)){
                predicates.add(criteriaBuilder.equal(customer.get("id"),dto.getUserId()));
            }
            if (dto.getEndDate()!=null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dateOfWork"),dto.getEndDate()));
            }if (dto.getStartDate()!=null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfWork"),dto.getStartDate()));
            }
            if (dto.getMaxOfferPrice()!=null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(subService.get("basePrice"),dto.getMaxOfferPrice()));
            }
            if (dto.getMinOfferPrice()!=null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(subService.get("basePrice"),dto.getMinOfferPrice()));
            }
            if (!dto.getSituation().equals(OrderSituation.NONE)){
                predicates.add(criteriaBuilder.equal(root.get("situation"),dto.getSituation()));
            }
            query.select(root).where(predicates.toArray(new Predicate[0]));
            return query.getRestriction();
        };
    }
}
