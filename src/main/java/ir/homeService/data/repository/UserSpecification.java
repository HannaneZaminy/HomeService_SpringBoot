package ir.homeService.data.repository;

import ir.homeService.data.domain.Customer;
import ir.homeService.data.domain.Expert;
import ir.homeService.data.domain.Users;
import ir.homeService.dto.FilterUsersBaseOnNumOfOrdersDto;
import ir.homeService.dto.FilterUsersDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

public interface UserSpecification {
    static Specification<Users> filterUsers(FilterUsersDto dto) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            CriteriaQuery<Users> query = criteriaBuilder.createQuery(Users.class);
            List<Predicate> predicates = new ArrayList<>();
            Root<Expert> expertRoot = criteriaBuilder.treat(root, Expert.class);
            if (dto.getName() != null) {
                predicates.add(criteriaBuilder.equal(root.get("name"), dto.getName()));
            }
            if (dto.getLastName() != null) {
                predicates.add(criteriaBuilder.equal(root.get("lastName"), dto.getLastName()));
            }
            if (dto.getEmail() != null) {
                predicates.add(criteriaBuilder.equal(root.get("email"), dto.getEmail()));
            }
            if (dto.getRole() != null) {
                predicates.add(criteriaBuilder.equal(root.get("userRole"), dto.getRole()));
            }
            if (dto.getRate() != null) {
                predicates.add(criteriaBuilder.greaterThan(expertRoot.get("rate"), dto.getRate()));
            }
//            if (dto.getField()!=null || dto.getRate()!=null){
//                query.select(expertRoot).where(predicates.toArray(new Predicate[0]));
//            }if (dto.getName()!=null || dto.getLastName()!=null || dto.getEmail()!=null || dto.getRole()!=null){
//                query.select(root).where(predicates.toArray(new Predicate[0]));
//            }
//            else {
//                query.select(root);
//            }
            query.select(root).where(predicates.toArray(new Predicate[0]));
            return query.getRestriction();

        };
    }

    static Specification<Users> userHistory(FilterUsersBaseOnNumOfOrdersDto dto) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            CriteriaQuery<Users> query = criteriaBuilder.createQuery(Users.class);
            List<Predicate> predicates = new ArrayList<>();
            Subquery<Customer> customerSubquery = query.subquery(Customer.class);
            Subquery<Expert> expertSubquery = query.subquery(Expert.class);
            Root<Customer> customerRoot = customerSubquery.from(Customer.class);
            Root<Expert> expertRoot = expertSubquery.from(Expert.class);
            Subquery<Customer> customerSelect = customerSubquery.select(customerRoot.get("id"));
            Subquery<Expert> expertSelect = expertSubquery.select(expertRoot.get("id"));
            List<Predicate> customerPredicate = new ArrayList<>();
            List<Predicate> expertPredicate = new ArrayList<>();

            if (dto.getStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), dto.getStartDate()));
            }
            if (dto.getEndDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), dto.getEndDate()));
            }
            if (dto.getMaxNumberOfOrders() != null) {
                customerPredicate.add(criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.size(customerRoot.get("orders")), dto.getMaxNumberOfOrders()));

            }
            if (dto.getMinNumberOfOrders() != null) {

                customerPredicate.add(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.size(customerRoot.get("orders")), dto.getMinNumberOfOrders()));
            }
            if (dto.getMaxNumberOfOffers() != null) {

                expertPredicate.add(criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.size(expertRoot.get("offers")), dto.getMaxNumberOfOffers()));
            }
            if (dto.getMinNumberOfOffers() != null) {

                expertPredicate.add(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.size(expertRoot.get("offers")), dto.getMinNumberOfOffers()));
            }
            if (customerPredicate.size() > 0) {
                customerSelect.where(customerPredicate.toArray(new Predicate[0]));
                predicates.add(root.get("id").in(customerSubquery));
            }
            if (expertPredicate.size() > 0) {
                expertSelect.where(expertPredicate.toArray(new Predicate[0]));
                predicates.add(root.get("id").in(expertSubquery));
            }
            query.select(root).where(predicates.toArray(new Predicate[0]));
            return query.getRestriction();

        };
    }




}
