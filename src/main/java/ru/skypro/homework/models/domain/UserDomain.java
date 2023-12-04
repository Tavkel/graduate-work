package ru.skypro.homework.models.domain;

import jakarta.persistence.*;
import lombok.*;
import ru.skypro.homework.models.enums.Roles;

import java.util.List;

@Entity
@ToString
@EqualsAndHashCode
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
}