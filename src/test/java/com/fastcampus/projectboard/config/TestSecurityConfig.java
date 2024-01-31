package com.fastcampus.projectboard.config;

import com.fastcampus.projectboard.domain.UserAccount;
import com.fastcampus.projectboard.reopsitory.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

/*
컨트롤러 테스트에서 security 인증과 관련된 테스트 피처를 추가하기 위해서 필요한
뇨용들을 전부 한 곳에 모으겠다라는 의도로 만듬

 */
@Import(SecurityConfig.class)
public class TestSecurityConfig {

    /*
    다른 컨트롤러에서는 userAccountRepository 를 쓸 수 있다는 상황에
    감춰져 버리는 부작용이 있다. 이러한 단점이 존재하지만
    userAccountRepository 를 컨트롤러 레이어에서 사용하지 않는다면
    사실 상관이 없다.
     */
    @MockBean private UserAccountRepository userAccountRepository;

    /*
    인증과 관련된 테스트를 할 때 어떤 특정한 주기에 맞춰서 특정한 코드가
    실행되게끔 리스너를 통해서 코드르 잡아 호출되게 만들 수 있는데
    그 중 하나가 @BeforeTestMethod 어노테이션이다.
    각 테스트 메소드가 실행되기 직전에 해당 메서드를 수행해서
    인증 정보를 넣어주도록 함
     */
    @BeforeTestMethod
    public void securitySetUp() {
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(
                "bongTest",
                "pw",
                "bong-test@mail.com",
                "bong-test",
                "test memo"
        )));
    }

}
