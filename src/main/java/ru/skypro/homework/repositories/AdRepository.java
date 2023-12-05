package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.models.domain.AdDomain;

import java.util.List;

public interface AdRepository extends JpaRepository<AdDomain, Integer> {
    List<AdDomain> findByUserId(Integer userId);
}
