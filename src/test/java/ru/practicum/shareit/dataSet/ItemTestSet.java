package ru.practicum.shareit.dataSet;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.dto.*;

import java.util.ArrayList;

@Getter
@Setter
public class ItemTestSet {
    private ItemDto dto;
    private ItemDtoCreate dtoCreate;
    private final Long userId = 1L;

    private CommentDto commentDto;
    private CommentDtoCreate commentDtoCreate;

    public ItemTestSet() {
        dto = new ItemDto();
        dto.setId(1L);
        dto.setName("Item1");
        dto.setDescription("Its item1 description");
        dto.setAvailable(true);
        dto.setOwnerId(1L);
        dto.setComments(new ArrayList<>());
        dto.setRequestId(0L);

        dtoCreate = new ItemDtoCreate();
        dtoCreate.setName(dto.getName());
        dtoCreate.setDescription(dto.getDescription());
        dtoCreate.setAvailable(dto.getAvailable());
        dtoCreate.setRequestId(dto.getRequestId());

        commentDto = new CommentDto();
        commentDto.setText("some comment");
        commentDto.setAuthorName("Author Name");

        commentDtoCreate = new CommentDtoCreate();
        commentDtoCreate.setText(commentDto.getText());
    }
}
