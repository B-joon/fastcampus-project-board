package com.fastcampus.projectboard.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
WebMvcTest 는 슬라이스 테스트여서 컨트롤러 외의 Bean 들을 로드하지 않고
컨트롤러와 연관된 내용만 최소한으로 읽어드린다.
즉, Data REST Autoconfiguration 을 읽지 않다.
방법이 있지만 번거롭고 방법이 힘들다.
그래서 쉬운 방법인 인테그레이션 테스트로 작성하기로 한다.
인테그레이션 테스트는 DB에 영향을 주기 때문에 Transactional 어노테이션을 추가해 준다.
 */
//@WebMvcTest
// webEnvironment의 다양한 옵션이 존재하지만 MOCK를 사용해야 원하는 내용을 불러온다.
// default 값이 MOCK이므로 따로 코드를 작성하지 않는다.
@Disabled("Spring Data REST 통합 테스트는 불필요하므로 제외시킴")
@DisplayName("Data REST - API 테스트")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {

    private MockMvc mvc;

    public DataRestTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[api] 게시글 리스트 조회")
    @Test
    void givenNothing_whenRequestArticles_thenReturnArticlesJsonResponse() throws Exception {
        // given

        // when & then
        mvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                // hal 테스트를 진행하기 때문에 MediaType.APPLICATION_JSON 을 사용하면 테스트가 불가능하다.
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
//                .andDo(print()); << 해당 기능은 테스트 시 데이터가 정상으로 호출 되는지 눈으로 확인할 때 사용.

    }

    @DisplayName("[api] 게시글 단건 조회")
    @Test
    void givenNothing_whenRequestArticle_thenReturnArticleJsonResponse() throws Exception {
        // given

        // when & then
        mvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk())
                // hal 테스트를 진행하기 때문에 MediaType.APPLICATION_JSON 을 사용하면 테스트가 불가능하다.
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

    }

    @DisplayName("[api] 게시글 -> 댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestArticleCommentsFromArticle_thenReturnArticleCommentsFromArticleJsonResponse() throws Exception {
        // given

        // when & then
        mvc.perform(get("/api/articles/1/articleComments"))
                .andExpect(status().isOk())
                // hal 테스트를 진행하기 때문에 MediaType.APPLICATION_JSON 을 사용하면 테스트가 불가능하다.
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

    }

    @DisplayName("[api] 댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestArticleComments_thenReturnArticleCommentsJsonResponse() throws Exception {
        // given

        // when & then
        mvc.perform(get("/api/articleComments"))
                .andExpect(status().isOk())
                // hal 테스트를 진행하기 때문에 MediaType.APPLICATION_JSON 을 사용하면 테스트가 불가능하다.
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

    }

    @DisplayName("[api] 댓글 단건 조회")
    @Test
    void givenNothing_whenRequestArticleComment_thenReturnArticleCommentJsonResponse() throws Exception {
        // given

        // when & then
        mvc.perform(get("/api/articleComments/1"))
                .andExpect(status().isOk())
                // hal 테스트를 진행하기 때문에 MediaType.APPLICATION_JSON 을 사용하면 테스트가 불가능하다.
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

    }

    @DisplayName("[api] 회원 관련 API 는 일체 제공하지 않는다.")
    @Test
    void givenNoting_whenRequestingUserAccounts_thenThrowsException() throws Exception {
        // given

        // when & then
        mvc.perform(get("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(post("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(put("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(patch("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(delete("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(head("/api/userAccounts")).andExpect(status().isNotFound());

    }

}
