package com.fastcampus.projectboard.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("비즈니스 로직 - 페이지네이션")
// spring boot test의 무게를 줄이기 위해 webEnvironment의 옵션을 none으로 한다.
// 한번더 무게를 줄이기 위해 classes를 PaginationService 이것읋 바라보게 한다.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = PaginationService.class)
class PaginationServiceTest {

    private final PaginationService sut;

    public PaginationServiceTest(@Autowired PaginationService paginationService) {
        this.sut = paginationService;
    }

    @DisplayName("현재 페이지 번호와 총 페이지 수를 주면, 페이지 바 리스트를 만들어 준다.")
    @MethodSource
    @ParameterizedTest(name = "[{index}] 현재 페이지: {0}, 총 페이지: {1} ==> {2}") // 표현식을 보기 좋게 바꾸기
    void givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnPaginationBarNumbers(int currentPageNumber, int totalPages, List<Integer> expected) {
        // given


        // when
        List<Integer> actual = sut.getPaginationBarNumbers(currentPageNumber, totalPages);

        // then
        assertThat(actual).isEqualTo(expected);


    }

    // 위 테스트 메서드와 동일한 이름으로 메서드를 만들면 인식한다.
    static Stream<Arguments> givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnPaginationBarNumbers() {
        return Stream.of(
                arguments(0, 13, List.of(0, 1, 2, 3, 4)),
                arguments(1, 13, List.of(0, 1, 2, 3, 4)),
                arguments(2, 13, List.of(0, 1, 2, 3, 4)),
                arguments(3, 13, List.of(1, 2, 3, 4, 5)),
                arguments(4, 13, List.of(2, 3, 4, 5, 6)),
                arguments(5, 13, List.of(3, 4, 5, 6, 7)),
                arguments(6, 13, List.of(4, 5, 6, 7, 8)),
                arguments(10, 13, List.of(8, 9, 10, 11, 12)),
                arguments(11, 13, List.of(9, 10, 11, 12)),
                arguments(12, 13, List.of(10, 11, 12))
        );
    }

    // 스펙의 명세를 바로 코드에 드러내기 위한 목적으로 테스트를 작성한다.
    @DisplayName("현재 설정되어 있는 페이지네이션 바의 길이를 알려준다.")
    @Test
    void givenNothing_whenCalling_thenReturnsCurrentBarLength() {
        // given


        // when
        int barLength = sut.currentBarLength();

        // them
        assertThat(barLength).isEqualTo(5);

    }

}
