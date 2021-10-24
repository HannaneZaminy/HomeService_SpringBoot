package ir.homeService.service.mapper;

import ir.homeService.data.domain.Comments;
import ir.homeService.dto.CommentDto;
import org.springframework.stereotype.Component;

@Component
public class CommentMapperImpl implements CommentMapper {
    private final CustomerMapper customerMapper;
    private final ExpertMapper expertMapper;
    private final OrderMapper orderMapper;

    public CommentMapperImpl(CustomerMapper customerMapper, ExpertMapper expertMapper, OrderMapper orderMapper) {
        this.customerMapper = customerMapper;
        this.expertMapper = expertMapper;
        this.orderMapper = orderMapper;
    }

    @Override
    public Comments toComment(CommentDto dto) {
        Comments comments = new Comments();
        comments.setId(dto.getId());
        comments.setRate(dto.getRate());
        comments.setComment(dto.getComment());
        comments.setCustomer(customerMapper.toCustomer(dto.getCustomer()));
        comments.setExpert(expertMapper.toExpert(dto.getExpert()));
        comments.setOrder(orderMapper.toOrder(dto.getOrderDto()));
        return comments;

    }

    @Override
    public CommentDto toCommentDto(Comments comments) {
        CommentDto dto = new CommentDto();
        dto.setId(comments.getId());
        dto.setComment(comments.getComment());
        dto.setRate(comments.getRate());
        dto.setCustomer(customerMapper.toCustomerDto(comments.getCustomer()));
        dto.setOrderDto(orderMapper.toOrderDto(comments.getOrder()));
        dto.setExpert(expertMapper.toExpertDto(comments.getExpert()));
        return dto;
    }
}
