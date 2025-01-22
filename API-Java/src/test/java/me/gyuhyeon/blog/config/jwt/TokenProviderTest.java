package me.gyuhyeon.blog.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import me.gyuhyeon.blog.domain.User;
import me.gyuhyeon.blog.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    // generateToken() 검증 테스트
    @DisplayName("generateToken(): 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {
        // given    - 테스트 유저 생성
        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());
        // when - generateToken() 메서드 호출, 토큰 생성
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));
        // then - jjwt라이브러리로 토큰 복호화, claim의 id값이 given단의 유저id와 동일한지 확인
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    // validToken() 검증 테스트
    @DisplayName("ValidToken(): 만료된 토큰인 때에 유효성 검증에 실패한다.")
    @Test
    void validToken_invalidToken() {
        // given 만료 시간 - 1970.01.01~현재까지 시간(밀리초) - 1000밀리초 => 이미 만료됨
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);
        // when - 토큰 제공자의 validToken() 메서드 호출, 유효한 토큰인지 검증한 뒤 결괏값 반환
        boolean result = tokenProvider.validToken(token);
        // then
        assertThat(result).isFalse();
    }

    @DisplayName("validToken(): 유효한 토큰인 때에 유효성 검증에 성공한다.")
    @Test
    void validToken_validToken() {
        // given - 위와 같음, 만료시간: 현재 시간으로부터 14일 후
        String token = JwtFactory.withDefaultValues()
                .createToken(jwtProperties);
        // when - 위와 같음
        boolean result = tokenProvider.validToken(token);
        // then
        assertThat(result).isTrue();
    }

    // getAuthentication() 검증 테스트
    @DisplayName("getAuthentication(): 토큰 기반으로 인증 정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        // given - jjwt 라이브러리를 사용해서 토큰 생성, 토큰 제목(subject) = "user@email.com"
        String userEmail = "user@email.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);
        // when - 토큰 제공자의 getAuthentication() 메서드 호출, 인증 객체 반환
        Authentication authentication = tokenProvider.getAuthentication(token);
        // then
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);
    }

    //getUserId() 검증 테스트
    @DisplayName("gutUserId(): 토큰으로 유저 ID를 가져올 수 있다.")
    @Test
    void getUserId() {
        // given - jjwt 라이브러리를 사용해서 토큰 생성, 클레임 추가 => key:"id", value:1
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id",userId))
                .build()
                .createToken(jwtProperties);
        // when - 토큰 제공자의 getUserId() 메서드를 호출, 유저 ID 반환받음
        Long userIdByToken = tokenProvider.getUserId(token);

        // then - 반환값이 1과 같은지 확인
        assertThat(userIdByToken).isEqualTo(userId);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
