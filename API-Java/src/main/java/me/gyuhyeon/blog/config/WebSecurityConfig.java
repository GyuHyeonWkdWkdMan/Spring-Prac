package me.gyuhyeon.blog.config;

import lombok.RequiredArgsConstructor;
import me.gyuhyeon.blog.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String[] WHITE_LIST_URL = {
            "/login",
            "/signup",
            "/user"};


    private final UserDetailService userService;

    // 스프링 시큐리티 기능 비활성화 (정적 리소스 - 이미지, HTML파일)
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성 (인증/인가 및 로그인, 로그아웃 관련 설정)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth     // autorizeRequests -> authorizeHttpRequests
                        .requestMatchers(WHITE_LIST_URL
//                                new AntPathRequestMatcher("/login"),
//                                new AntPathRequestMatcher("/signup"),
//                                new AntPathRequestMatcher("/user")
                        ).permitAll()       //누구나 접근 가능하게 설정
                        .anyRequest().authenticated())  // 위 url 이외의 요청에 대해 설정.인증이 성공된 상태여야 접근 가능
                .formLogin(formLogin -> formLogin   // 폼 기반 로그인 설정
                        .loginPage("/login")    // 로그인 페이지 경로 설정
                        .defaultSuccessUrl("/articles") // 로그인이 완료되었을 때 이동할 경로 설정
                )
                .logout(logout -> logout    // 로그아웃 설정
                        .logoutSuccessUrl("/login")     // 로그아웃이 완료되었을 때 이동할 경로 설정
                        .invalidateHttpSession(true)    // 로그아웃 이후 세션을 전체 삭제할지 여부 설정
                )
                .csrf(AbstractHttpConfigurer::disable)  //csrf 비활성화 CSRF 공격 방지 위한 코드, 실습을 위해 비활성화.
                .build();
    }

    // 인증 관리자 관련 설정 - 사용자 정보 가져올 서비스 재정의, 인증 방법(LDAP,JDBC 기반 인증) 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider);
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
