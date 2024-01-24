package com.fastcampus.projectboard.response;

import com.fastcampus.projectboard.dto.ArticleDto;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
여기서는 DTO와는 다르게 정보의 일부만 들고 있거나
가공된 형태로 들고 있게 한다.
요청을 하면 컨트롤러의 응답으로 내보내는 전용 DTO 라고 보면 된다.
즉, 컨트롤러에서만 사용하며 컨트롤러에서의 일반적인 DTO의 의존성을
Response DTO로 최소한다.

코드를 많이 작성해야 하지만
레이어별로 DTO를 분리시킴으로써 각 레이어의 독립성을 보장하고
조금더 유연한 코드를 만든다.
 */
public record ArticleResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname) {

    public static ArticleResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleResponse(id, title, content, hashtag, createdAt, email, nickname);
    }

    public static ArticleResponse from(ArticleDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return new ArticleResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname
        );
    }

}
