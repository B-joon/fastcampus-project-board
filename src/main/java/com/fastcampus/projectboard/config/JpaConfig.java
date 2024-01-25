package com.fastcampus.projectboard.config;

import com.fastcampus.projectboard.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        // getContext() 를 해서 security context 를 가지고 온다.
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                // Authentication 의 정보를 불러온다.
                .map(SecurityContext::getAuthentication)
                // Authenticated 가 됐는지, 로그인 되었는지 검사
                .filter(Authentication::isAuthenticated)
                // 로그인이 됐다면 principal 인증 정보를 가지고 온다.
                // 어떤 형태의 인증 정보인지 모르는 하나의 인증 정보가 들어있는데 이것을
                // principal 인터페이스로 리턴하는 것이 아닌 Object 로 리턴한다.
                // 그래서 getPrincipal 에서 받아들이는 것은 UserDetail 을 받아들이게 된다.
                // 그렇기 때문에 아래에서 BoardPrincipal 을 불러올 수 있다.
                .map(Authentication::getPrincipal)
                // 직접 만든 인증 정보를 가져오는데 람다식으로 타입 캐스팅을 한다.
                // .map(x -> (BoardPrincipal) x) 이런 방식으로 조금더 똑독하게 타입 캐스팅을 한것이 아래 코드이다.
                .map(BoardPrincipal.class::cast)
                // 인증에 사용할 정도를 불러 옴
                .map(BoardPrincipal::username);
    }

}
