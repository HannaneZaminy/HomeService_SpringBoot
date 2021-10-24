package ir.homeService.data.repository;

import ir.homeService.data.domain.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Integer> {
    //    void saveNewExpert(EXPERT expert);
//    void deleteExpert(EXPERT expert);
//    void updateExpert(EXPERT expert);
//    List<EXPERT> fetchAllExperts();
    Optional<Expert> findByEmail(String email);
}
