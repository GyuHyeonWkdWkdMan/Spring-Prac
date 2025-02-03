package me.gyuhyeon.blog.service;

import lombok.RequiredArgsConstructor;
import me.gyuhyeon.blog.domain.RefreshToken;
import me.gyuhyeon.blog.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshtoken) {
        return refreshTokenRepository.findByRefreshToken(refreshtoken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
}
