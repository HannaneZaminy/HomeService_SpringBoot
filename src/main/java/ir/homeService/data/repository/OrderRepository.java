package ir.homeService.data.repository;

import ir.homeService.data.domain.Expert;
import ir.homeService.data.domain.Customer;
import ir.homeService.data.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer>, JpaSpecificationExecutor<Orders> {


    @Query("select o from Orders as o where o.subService in (select s from SubService s where :expert member s.experts)")
    List<Orders> findOrdersBaseOnExpertSubServices(@Param("expert") Expert expert);


    @Query("select e.services from Expert as e where e.id=:id")
    List<Orders> size(@Param("id") Integer id);

    List<Orders> findByExpert(Expert expert);

    List<Orders> findByCustomer(Customer customer);


}
