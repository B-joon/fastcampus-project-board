package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.type.SearchType;
import com.fastcampus.projectboard.dto.ArticleDto;
import com.fastcampus.projectboard.dto.ArticleUpdateDto;
import com.fastcampus.projectboard.reopsitory.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;


@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks private ArticleService sut;

    @Mock private ArticleRepository articleRepository;

    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnArticleList() {
        // given


        // when
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 내용, ID, 닉네임, 해시태그

        // then
        assertThat(articles).isNotNull();

    }

    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenArticleID_whenSearchingArticle_thenReturnArticle() {
        // given


        // when
        ArticleDto articles = sut.searchArticle(1L); // 제목, 내용, ID, 닉네임, 해시태그

        // then
        assertThat(articles).isNotNull();

    }

    // 아래 태스트 방식은 온전한 유닛 테스트, 일종의 solitary 테스트
    // 값을 넣은 것을 실제로 저장되었는지 DB에 값이 저장 되었는지를 검사하는것이 아니다.
    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
        // given
        given(articleRepository.save(any(Article.class))).willReturn(null);

        // when
        sut.saveArticle(ArticleDto.of(LocalDateTime.now(), "Bong", "title", "content", "#java"));

        //then
        // save를 한번 호출 하였는가를 검사하는 코드
        then(articleRepository).should().save(any(Article.class));

    }

    @DisplayName("게시글 ID와 수정 정보를 입력하면, 게시글을 수정한다.")
    @Test
    void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdatesArticle() {
        // given
        given(articleRepository.save(any(Article.class))).willReturn(null);

        // when
        sut.updateArticle(1L, ArticleUpdateDto.of("title", "content", "#java"));

        //then
        // save를 한번 호출 하였는가를 검사하는 코드
        then(articleRepository).should().save(any(Article.class));

    }

    @DisplayName("게시글 ID를 입력하면, 게시글을 삭제한다.")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle() {
        // given
        willDoNothing().given(articleRepository).delete(any(Article.class));

        // when
        sut.deleteArticle(1L);

        //then
        // save를 한번 호출 하였는가를 검사하는 코드
        then(articleRepository).should().delete(any(Article.class));

    }

}