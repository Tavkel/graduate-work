package ru.skypro.homework.models.domain;

import jakarta.persistence.*;
import lombok.*;
import ru.skypro.homework.models.AuditableEntity;
import ru.skypro.homework.models.enums.Roles;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "users")
@AllArgsConstructor
public class UserDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String imageUrl;
    @Enumerated(value = EnumType.STRING)
    private Roles userRole;
    private String passwordHash;
    @OneToMany(mappedBy = "user")
    private List<AdDomain> ads;
    @OneToMany(mappedBy = "user")
    private List<CommentDomain> comments;

    public UserDomain() {
    }
            public UserDomain id(Integer id) {
            this.id = id;
            return this;
        }

        public UserDomain email(String email) {
            this.email = email;
            return this;
        }

        public UserDomain firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserDomain lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDomain phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserDomain imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public UserDomain userRole(Roles userRole) {
            this.userRole = userRole;
            return this;
        }

        public UserDomain passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDomain that = (UserDomain) o;
        return Objects.equals(email, that.email) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(phone, that.phone) && Objects.equals(imageUrl, that.imageUrl) && userRole == that.userRole && Objects.equals(passwordHash, that.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName, phone, imageUrl, userRole, passwordHash);
    }

    @Override
    public String toString() {
        return "UserDomain{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", userRole=" + userRole +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}