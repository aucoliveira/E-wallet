package com.e_wallet.demo.service;

import com.e_wallet.demo.entity.Wallet;
import com.e_wallet.demo.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Transactional
    public void transfer(Long senderId, Long receiverId, BigDecimal amount) {
        Wallet sender = walletRepository.findByUserId(senderId)
                .orElseThrow(() -> new RuntimeException("Remetente não encontrado"));

        Wallet receiver = walletRepository.findByUserId(receiverId)
                .orElseThrow(() -> new RuntimeException("Destinatário não encontrado"));

        sender.debit(amount);
        receiver.credit(amount);

        walletRepository.save(sender);
        walletRepository.save(receiver);
    }

    @Transactional
    public void deposit(Long userId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        wallet.credit(amount);
        walletRepository.save(wallet);
    }

    @Transactional
    public void withdraw(Long userId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        wallet.debit(amount);
        walletRepository.save(wallet);
    }

    public BigDecimal getBalance(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return wallet.getBalance();
    }
}
