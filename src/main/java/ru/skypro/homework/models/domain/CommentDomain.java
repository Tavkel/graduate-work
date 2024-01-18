package ru.skypro.homework.models.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.skypro.homework.models.OwnedEntity;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class CommentDomain implements OwnedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDomain user;
    @ManyToOne
    @JoinColumn(name = "ad_id", nullable = false)
    private AdDomain ad;
    private Long createdAt;
    private String text;

    public CommentDomain() {
    }

    public CommentDomain id(Integer id) {
        this.id = id;
        return this;
    }

    public CommentDomain user(UserDomain user) {
        this.user = user;
        return this;
    }

    public CommentDomain ad(AdDomain ad) {
        this.ad = ad;
        return this;
    }

    public CommentDomain createdAt(Long createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CommentDomain text(String text) {
        this.text = text;
        return this;
    }

    public Integer getOwnerId() {
        return user.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDomain that = (CommentDomain) o;
        return Objects.equals(createdAt, that.createdAt) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, text);
    }

    @Override
    public String toString() {
        return "CommentDomain{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", text='" + text + '\'' +
                '}';
    }
}
