package com.fastcampus.projectboard.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    private static final int BAR_LENGTH = 5;

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
        // 페이지 선택을 했을 때 중앙 값을 찾아가기 위해
        // 0이랑 비교해서 더 큰 값을 사용하겠다. 음수 방지
        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);
        // 끝 페이지 번호를 처리해주는데 총 페이지의 오버된 숫자가 나오지 않게 하는 코
        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);

        return IntStream.range(startNumber, endNumber).boxed().toList();
    }

    public int currentBarLength() {
        return BAR_LENGTH;
    }

}
