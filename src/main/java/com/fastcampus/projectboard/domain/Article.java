package com.fastcampus.projectboard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false) private String title;   // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 내용

    @Setter @Column private String hashtag; // 해시태그

    /*
    article에 연동되어 있는 comment는 중복을 허용하지 않고 다 여기에서 모아서 Collection 으로 보겠다.
    mappedBy를 이용해서 article과 articleComment의 테이블 명을 합쳐서 테이블을 만들지는 것을 방지
    ----
    @ToString.Exclude 는 Circular Referencing 이슈를 방지하기 위해 사용한다.
    이유는 서로 순환 참조를 하고 있기 때문에 메모리가 om으로 뻗어버리고 시스템이 꺼져버리게 중단될 수 있다.
     */
    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;             // 생성일자
    @CreatedBy @Column(nullable = false, length = 100) private String createdBy;        // 생성자
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;       // 수정일자
    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy;  // 수정자

    protected Article() {
    }

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // 팩토리 메소드로 제
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    /*
    영속화되지 않았다고 하면 동등성 검사 자체가 의미가 없는 걸로 보고
    다 다른 것으로 간주하거나 혹은 처리하지 않겠다는 뜻
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
//        if (o == null || getClass() != o.getClass()) return false;
//        Article article = (Article) o;
        return id != null && id.equals(article.id);
    }

    /*
    동등성 검사는 ID의 hashcode 를 가지고 하겠다
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
