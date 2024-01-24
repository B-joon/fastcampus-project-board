package com.fastcampus.projectboard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @ManyToOne(optional = false) @JoinColumn(name = "userId") private UserAccount userAccount; // 유저 정보 (ID)

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
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    protected Article() {
    }

    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // 팩토리 메소드로 제
    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }

    /*
    영속화되지 않았다고 하면 동등성 검사 자체가 의미가 없는 걸로 보고
    다 다른 것으로 간주하거나 혹은 처리하지 않겠다는 뜻
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article that)) return false;
//        if (o == null || getClass() != o.getClass()) return false;
//        Article article = (Article) o;
        return id != null && id.equals(that.getId());
    }

    /*
    동등성 검사는 ID의 hashcode 를 가지고 하겠다
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
