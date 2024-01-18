package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.OwnedEntity;
import ru.skypro.homework.models.domain.UserDomain;
import ru.skypro.homework.models.dto.NewPassword;
import ru.skypro.homework.models.dto.UpdateUser;
import ru.skypro.homework.models.dto.User;

public interface UserService {
    User getUser();
    UserDomain getUserDomain();
    UpdateUser updateUser(UpdateUser user);
    void updateUserPassword(NewPassword password);
    void updateAvatar(MultipartFile file);
    Integer getUserId();
}
