package com.fastcampus.projectboard.reopsitory;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import com.fastcampus.projectboard.reopsitory.querydsl.ArticleRepositoryCustom;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        ArticleRepositoryCustom,
        QuerydslPredicateExecutor<Article>,  // 해당 클래스만 상속 받아도 검색 기능은 사용 가능하다.
        QuerydslBinderCustomizer<QArticle> {

    Page<Article> findByTitleContaining(String title, Pageable pageable);
    Page<Article> findByContentContaining(String content, Pageable pageable);
    Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pageable);
    Page<Article> findByUserAccount_NicknameContaining(String nickname, Pageable pageable);
    Page<Article> findByHashtag(String hashtag, Pageable pageable);

    /*
    검색에 대한 세부적인 규칙을 재구성 하기 위함
    인터페이스라 구현을 넣을 수 없지만
    Java8 버전부터 가능해졌다.
     */
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        /*
        원하지 않는 필드는 검색에서 제외 시키기 위해 사용함
        리스팅 하지 않은 프로퍼티는 검색에서 제외시킨다.
         */
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);
        /*
        exact match로 동작하고 있는 것의 룰 변경
        likeIgnoreCase와 containsIgnoreCase 의 차이는 쿼리가 생성되는 방식이 달라진다.
        like 쿼리에 '%' 를 직접 입력해서 검색하고 싶을 때 likeIgnoreCase를 사용하고
        '%'가 자동으로 쿼리에 생성되게 하고 싶을 때 containsIgnoreCase를 사용하면 된다.
         */
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase);      // like '${v}'
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);  // like '%${v}%'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);

    }

}
