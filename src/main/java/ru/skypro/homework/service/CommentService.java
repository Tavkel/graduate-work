package ru.skypro.homework.service;

import ru.skypro.homework.models.dto.Comment;
import ru.skypro.homework.models.dto.Comments;
import ru.skypro.homework.models.dto.CreateOrUpdateComment;

import java.util.List;

public interface CommentService {
    Comments getCommentsForAd(Integer adId);
    Comment createOrUpdateComment(CreateOrUpdateComment comment, Integer commentId, Integer adId);
    void deleteComment(Integer commentId, Integer adId);
}