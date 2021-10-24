package ir.homeService.data.repository;

import ir.homeService.data.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    @Override
    Optional<Manager> findById(Integer integer);

    //    void saveNewManager(Manager manager);
//    void deleteManager(Manager manager);
//    void updateManager(Manager manager);
//    List<Manager> fetchAllManagers();
    Optional<Manager> findByUserName(String userName);
}
