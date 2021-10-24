package ir.homeService.data.repository;

import ir.homeService.data.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
    //    void saveNewService(Service service);
//    Service getService(Service service);
//    void  deleteService(Service service);
//    void updateService(Service service);
//    List<Service> fetchAllServices();
    Service findByName(String name);
}
