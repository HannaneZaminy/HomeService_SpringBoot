package ir.homeService.data.repository;


import ir.homeService.data.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
//    void saveNewCustomer(CUSTOMER customer);
//    void updateCustomer(CUSTOMER customer);
//    void deleteCustomer(CUSTOMER customer);
//    List<CUSTOMER> fetchAllCustomers();

    Optional<Customer> findByEmail(String email);
}
