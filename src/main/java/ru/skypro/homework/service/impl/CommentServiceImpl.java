package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.homework.helpers.mappers.CommentMapper;
import ru.skypro.homework.models.domain.CommentDomain;
import ru.skypro.homework.models.dto.Comment;
import ru.skypro.homework.models.dto.Comments;
import ru.skypro.homework.models.dto.CreateOrUpdateComment;
import ru.skypro.homework.repositories.CommentRepository;
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

    //todo refactor - too long
    @Override
    public Comment createOrUpdateComment(CreateOrUpdateComment comment, Integer commentId, Integer adId) {
        var ad = adService.getAdDomain(adId);
        CommentDomain model = null;
        if (commentId != null && commentId != 0) {
            model = repository.findById(commentId).orElseThrow(() -> {
                log.info("Comment with id {} was not found", commentId);
                return new NoSuchElementException("Comment not found");
            });
        }
        if (model == null) {
            model = new CommentDomain().createdAt(
                    LocalDateTime.now().toEpochSecond(
                            ZoneId.systemDefault()
                                    .getRules()
                                    .getOffset(LocalDateTime.now())) * 1000
            );
        }
        model.setAd(ad);

        //todo remove stub
        model.setUser(userService.getUserDomain(1));

        model = CommentMapper.createOrUpdateCommentToCommentDomain(comment, model);
        model.setId(adId);
        var result = repository.save(model);
        return CommentMapper.commentDomainToComment(result);
    }

    @Override
    public void deleteComment(Integer commentId, Integer adId) {
        var comment = repository.findById(commentId).orElseThrow(() -> {
            log.info("Comment with id {} was not found", commentId);
            return new NoSuchElementException("Comment not found");
        });
        repository.delete(comment);
    }
}
