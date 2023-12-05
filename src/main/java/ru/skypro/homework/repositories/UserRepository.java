package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.models.domain.UserDomain;

public interface UserRepository extends JpaRepository<UserDomain, Integer> {
}
