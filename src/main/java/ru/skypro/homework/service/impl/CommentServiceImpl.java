package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.homework.exceptions.ActionForbiddenException;
import ru.skypro.homework.helpers.mappers.CommentMapper;
import ru.skypro.homework.models.domain.CommentDomain;
import ru.skypro.homework.models.dto.Comment;
import ru.skypro.homework.models.dto.Comments;
import ru.skypro.homework.models.dto.CreateOrUpdateComment;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.security.utility.PermissionChecker;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    private final AdService adService;
    private final UserService userService;

    public CommentServiceImpl(CommentRepository repository, AdService adService, UserService userService) {
        this.repository = repository;
        this.adService = adService;
        this.userService = userService;
    }

    @Override
    public Comments getCommentsForAd(Integer adId) {
        var comments = repository.findByAdId(adId).stream().map(CommentMapper::commentDomainToComment).toList();
        return new Comments(comments.size(), comments);
    }

    @Override
    public Comment createOrUpdateComment(CreateOrUpdateComment comment,
                                         Integer commentId,
                                         Integer adId) {
        CommentDomain model;
        if(commentId != null && commentId != 0){
            model = updateComment(comment, commentId);
        } else {
            model = createComment(comment);
        }

        model.setUser(userService.getUserDomain());
        model.setAd(adService.getAdDomain(adId));

        var result = repository.save(model);
        return CommentMapper.commentDomainToComment(result);
    }

    private CommentDomain createComment(CreateOrUpdateComment comment) {
        var result = CommentMapper.createOrUpdateCommentToCommentDomain(comment);
        return result.createdAt(LocalDateTime.now().toEpochSecond(
                ZoneId.systemDefault()
                        .getRules()
                        .getOffset(LocalDateTime.now())) * 1000);
    }

    private CommentDomain updateComment(CreateOrUpdateComment comment, Integer id) {
        var model = repository.findById(id).orElseThrow(() -> {
            log.info("Comment with id {} was not found", id);
            return new NoSuchElementException("Comment not found");
        });

        var user = userService.getUserDomain();
        if (!PermissionChecker.isActionAllowed(user, model)) throw new ActionForbiddenException();

        return model.text(comment.getText());
    }

    @Override
    public void deleteComment(Integer commentId, Integer adId) {
        var comment = repository.findById(commentId).orElseThrow(() -> {
            log.info("Comment with id {} was not found", commentId);
            return new NoSuchElementException("Comment not found");
        });

        var user = userService.getUserDomain();
        if (!PermissionChecker.isActionAllowed(user, comment)) throw new ActionForbiddenException();

        repository.delete(comment);
    }
}
