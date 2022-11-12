package ru.practicum.shareit.item.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.Comment;

@Component
public class CommentMapperImpl implements CommentMapper {
    public CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setCreated(comment.getCreated());
        dto.setAuthorName(comment.getAuthor().getName());

        return dto;
    }

    public Comment fromDto(CommentDto dto) {
        Comment comment = new Comment();
        comment.setText(dto.getText());

        return comment;
    }

    public Comment fromDtoCreate(CommentDtoCreate dto) {
        Comment comment = new Comment();
        comment.setText(dto.getText());

        return comment;
    }
}
