package ru.skypro.homework.models.domain;

import jakarta.persistence.*;
import lombok.*;
import ru.skypro.homework.models.EntityWithImage;
import ru.skypro.homework.models.OwnedEntity;

import java.util.List;
import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ads")
public class AdDomain implements OwnedEntity, EntityWithImage {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdDomain adDomain = (AdDomain) o;
        return Objects.equals(imageUrl, adDomain.imageUrl) && Objects.equals(price, adDomain.price) && Objects.equals(title, adDomain.title) && Objects.equals(description, adDomain.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageUrl, price, title, description);
    }

    @Override
    public String toString() {
        return "AdDomain{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
