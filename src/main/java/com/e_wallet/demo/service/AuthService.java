package com.e_wallet.demo.service;

import com.e_wallet.demo.config.JwtTokenService;
import com.e_wallet.demo.dto.AuthResponseDTO;
import com.e_wallet.demo.dto.LoginDTO;
import com.e_wallet.demo.dto.RegisterDTO;
import com.e_wallet.demo.entity.User;
import com.e_wallet.demo.entity.Wallet;
import com.e_wallet.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponseDTO register(RegisterDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Wallet wallet = new Wallet(null, BigDecimal.ZERO, user);
        user.setWallet(wallet);

        User savedUser = userRepository.save(user);
        String token = tokenService.generateToken(savedUser.getEmail());

        return new AuthResponseDTO(token, savedUser.getId(), savedUser.getEmail());
    }

    public AuthResponseDTO login(LoginDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = tokenService.generateToken(user.getEmail());

        return new AuthResponseDTO(token, user.getId(), user.getEmail());
    }
}
