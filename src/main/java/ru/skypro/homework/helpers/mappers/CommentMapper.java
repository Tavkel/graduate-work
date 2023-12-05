package ru.skypro.homework.helpers.mappers;

import ru.skypro.homework.models.domain.CommentDomain;
import ru.skypro.homework.models.dto.Comment;
import ru.skypro.homework.models.dto.CreateOrUpdateComment;

public class CommentMapper {
    public static CommentDomain createOrUpdateCommentToCommentDomain(CreateOrUpdateComment dto, CommentDomain model) {
        if (dto == null) {
            throw new IllegalArgumentException("Tried to map null to CommentDomain");
        }
        return model
                .text(dto.getText());
    }

    public static Comment commentDomainToComment (CommentDomain model) {
        if (model == null) {
            throw new IllegalArgumentException("Tried to map null to Comment");
        }
        return new Comment()
                .author(model.getUser().getId())
                .authorImage(model.getUser().getImageUrl())
                .authorFirstName(model.getUser().getFirstName())
                .createdAt(model.getCreatedAt())
                .pk(model.getId())
                .text(model.getText());
    }
}
