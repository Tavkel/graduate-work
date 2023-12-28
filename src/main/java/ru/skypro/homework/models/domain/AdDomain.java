package ru.skypro.homework.models.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.skypro.homework.models.AuditableEntity;

import java.util.List;

@Entity
@ToString
@EqualsAndHashCode
@Setter
@Getter
@Table(name = "ads")
public class AdDomain implements AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDomain user;
    @OneToMany(mappedBy = "ad")
    private List<CommentDomain> comments;
    private String imageUrl;
    private Integer price;
    private String title;
    private String description;

    public AdDomain() {
    }

    public AdDomain id(Integer id) {
        this.id = id;
        return this;
    }

    public AdDomain user(UserDomain user) {
        this.user = user;
        return this;
    }

    public AdDomain comments(List<CommentDomain> comments) {
        this.comments = comments;
        return this;
    }

    public AdDomain imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public AdDomain price(Integer price) {
        this.price = price;
        return this;
    }

    public AdDomain title(String title) {
        this.title = title;
        return this;
    }

    public AdDomain description(String description) {
        this.description = description;
        return this;
    }

    public Integer getOwnerId() {
        return user.getId();
    }
}
