package com.fastcampus.projectboard.dto;

import com.fastcampus.projectboard.domain.Article;

import java.time.LocalDateTime;

public record ArticleDto(
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy) {

    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, String hashtag, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    /*
    아래 두 코드의 이점은 아티클은 dto의 존재를 몰라도 된다.
    오직 dto 만 연관관계 맵핑을 하기 위해서 아티클의 존재를 알고 있다.
    아티클에 변화가 생기면 DTO는 영향을 받지만 DTO의 변경이나
    DTO를 새로 바꾸는데 있어서 Entity는 영향을 받지 않는다.

    도메인 코드와 DTO 코드를 확실하게 분리해서 서비스가 나간 뒤에는
    트랜잭션이 종료하고 레이지로딩을 하거나 다른 이슈가 있어도 아무런 문제가 없게끔
    서비스 코드에서 마무리가 된 데이터를 컨트롤러로 옮기게끔 하는 방식으로
    디자인을 추구한 방식
     */
    /*
    자기 자신을 반환하는데 Entity 로부터 DTO 로 변환 후 반환해준다.
     */
    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    /*
    위 코드의 반대인 DTO 로부터 Entity를 생성하여 코드
     */
    public Article toEntity() {
        return Article.of(
                userAccountDto.toEntity(),
                title,
                content,
                hashtag
        );
    }

}
