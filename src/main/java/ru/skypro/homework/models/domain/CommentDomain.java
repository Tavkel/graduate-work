package ru.skypro.homework.models.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Table(name = "comments")
public class CommentDomain {
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
}
