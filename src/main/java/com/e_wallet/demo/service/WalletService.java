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

        // 2. Executar a operação
        sender.debit(amount);
        receiver.credit(amount);

        // 3. Guardar alterações
        walletRepository.save(sender);
        walletRepository.save(receiver);
    }

}
