package ir.homeService.data.repository;

import ir.homeService.data.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>, JpaSpecificationExecutor<Users> {
    //    List<Users> fetchAllUsers();
//    void changePassword(Users user);
//    List<Users> findByProperty(SearchCustomerDto dto);
    @Query("SELECT u FROM Users u WHERE u.verificationCode = ?1")
     Users findByVerificationCode(String code);

    Optional<Users> findByEmail(String email);
}
