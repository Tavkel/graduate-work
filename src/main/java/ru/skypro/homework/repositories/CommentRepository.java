package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.models.domain.CommentDomain;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentDomain, Integer> {
    List<CommentDomain> findByAdId(Integer adId);
}
