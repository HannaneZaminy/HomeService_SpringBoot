package ir.homeService.service.mapper;

import ir.homeService.data.domain.Comments;
import ir.homeService.dto.CommentDto;

public interface CommentMapper {
    Comments toComment(CommentDto dto);

    CommentDto toCommentDto(Comments comments);
}
