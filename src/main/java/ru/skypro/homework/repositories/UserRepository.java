package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.models.domain.UserDomain;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDomain, Integer> {
    Optional<UserDomain> findByEmail(String email);
    boolean existsByEmail(String email);
}
