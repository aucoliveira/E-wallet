package com.e_wallet.demo.controller;

import com.e_wallet.demo.dto.TransferDTO;
import com.e_wallet.demo.dto.WalletOperationDTO;
import com.e_wallet.demo.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallets")
public class TransferController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferDTO dto) {
        walletService.transfer(dto.getSenderId(), dto.getReceiverId(), dto.getAmount());
        return ResponseEntity.ok("Transferência realizada com sucesso!");
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@Valid @RequestBody WalletOperationDTO dto) {
        walletService.deposit(dto.getUserId(), dto.getAmount());
        return ResponseEntity.ok("Depósito realizado com sucesso!");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@Valid @RequestBody WalletOperationDTO dto) {
        walletService.withdraw(dto.getUserId(), dto.getAmount());
        return ResponseEntity.ok("Saque realizado com sucesso!");
    }

    @GetMapping("/{userId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long userId) {
        BigDecimal balance = walletService.getBalance(userId);
        return ResponseEntity.ok(balance);
    }
}
