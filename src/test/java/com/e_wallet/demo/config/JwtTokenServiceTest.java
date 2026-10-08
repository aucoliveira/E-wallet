package com.e_wallet.demo.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenServiceTest {

    private JwtTokenService tokenService;
    private final String secret = "skywalletsecretkeyfortokengeneration1234567890";
    private final long expiration = 3600000; // 1 hour

    @BeforeEach
    void setUp() {
        tokenService = new JwtTokenService(secret, expiration);
    }

    @Test
    @DisplayName("Should generate and validate JWT token successfully")
    void testGenerateAndValidateToken() {
        String token = tokenService.generateToken("user@example.com");

        assertNotNull(token);
        assertTrue(tokenService.validateToken(token));
        assertEquals("user@example.com", tokenService.getEmailFromToken(token));
    }

    @Test
    @DisplayName("Should return false for invalid token")
    void testInvalidToken() {
        assertFalse(tokenService.validateToken("invalid.token.structure"));
    }
}
