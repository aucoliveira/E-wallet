package com.e_wallet.demo.controller;

import com.e_wallet.demo.dto.TransferDTO;
import com.e_wallet.demo.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
