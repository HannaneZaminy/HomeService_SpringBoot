package ir.homeService.service;

import ir.homeService.dto.CommentDto;
import ir.homeService.dto.ExpertDto;
import ir.homeService.service.exception.NotFoundExpertException;
import ir.homeService.service.exception.NotFoundOrderException;
import ir.homeService.service.exception.NotFoundRateForThisExpert;

import java.util.List;

public interface CommentService {
    void saveNewComment(CommentDto dto) throws NotFoundOrderException;

    void deleteComment(CommentDto dto);

    void updateComment(CommentDto dto);

    List<CommentDto> fetchAllComments();
    List<CommentDto> findByExpert(ExpertDto expertDto) throws NotFoundExpertException, NotFoundRateForThisExpert;

}
