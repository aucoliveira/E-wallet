package com.e_wallet.demo.service;

import com.e_wallet.demo.config.JwtTokenService;
import com.e_wallet.demo.dto.AuthResponseDTO;
import com.e_wallet.demo.dto.LoginDTO;
import com.e_wallet.demo.dto.RegisterDTO;
import com.e_wallet.demo.entity.User;
import com.e_wallet.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "test@example.com", "encodedPassword", null);
    }

    @Test
    @DisplayName("Should successfully register a new user")
    void testRegisterSuccess() {
        RegisterDTO registerDTO = new RegisterDTO("test@example.com", "password123");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        });
        when(tokenService.generateToken("test@example.com")).thenReturn("jwt-token");

        AuthResponseDTO response = authService.register(registerDTO);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("test@example.com", response.getEmail());
        assertEquals(1L, response.getUserId());

        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when registering with existing email")
    void testRegisterDuplicateEmail() {
        RegisterDTO registerDTO = new RegisterDTO("test@example.com", "password123");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                authService.register(registerDTO)
        );

        assertEquals("E-mail já cadastrado", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should successfully login user")
    void testLoginSuccess() {
        LoginDTO loginDTO = new LoginDTO("test@example.com", "password123");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(tokenService.generateToken("test@example.com")).thenReturn("jwt-token");

        AuthResponseDTO response = authService.login(loginDTO);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("test@example.com", response.getEmail());
        assertEquals(1L, response.getUserId());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
