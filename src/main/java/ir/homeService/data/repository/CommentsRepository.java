package ir.homeService.data.repository;

import ir.homeService.data.domain.Comments;
import ir.homeService.data.domain.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {
//    void saveNewComment(Comments comments);
//    void deleteComment(Comments comments);
//    void updateComment(Comments comments);
//    List<Comments> fetchAllComments();
    List<Comments> findByExpert(Expert expert);

}
