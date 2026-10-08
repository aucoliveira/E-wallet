package com.e_wallet.demo.service;

import com.e_wallet.demo.entity.User;
import com.e_wallet.demo.entity.Wallet;
import com.e_wallet.demo.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    private User senderUser;
    private User receiverUser;
    private Wallet senderWallet;
    private Wallet receiverWallet;

    @BeforeEach
    void setUp() {
        senderUser = new User(1L, "sender@example.com", null);
        receiverUser = new User(2L, "receiver@example.com", null);

        senderWallet = new Wallet(1L, new BigDecimal("100.00"), senderUser);
        receiverWallet = new Wallet(2L, new BigDecimal("50.00"), receiverUser);

        senderUser.setWallet(senderWallet);
        receiverUser.setWallet(receiverWallet);
    }

    @Test
    @DisplayName("Should successfully transfer amount between two wallets")
    void testTransferSuccess() {
        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(senderWallet));
        when(walletRepository.findByUserId(2L)).thenReturn(Optional.of(receiverWallet));

        walletService.transfer(1L, 2L, new BigDecimal("30.00"));

        assertEquals(new BigDecimal("70.00"), senderWallet.getBalance());
        assertEquals(new BigDecimal("80.00"), receiverWallet.getBalance());

        verify(walletRepository).save(senderWallet);
        verify(walletRepository).save(receiverWallet);
    }

    @Test
    @DisplayName("Should throw exception when sender has insufficient balance")
    void testTransferInsufficientBalance() {
        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(senderWallet));
        when(walletRepository.findByUserId(2L)).thenReturn(Optional.of(receiverWallet));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                walletService.transfer(1L, 2L, new BigDecimal("150.00"))
        );

        assertEquals("Saldo insuficiente", exception.getMessage());
        assertEquals(new BigDecimal("100.00"), senderWallet.getBalance());
        assertEquals(new BigDecimal("50.00"), receiverWallet.getBalance());

        verify(walletRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when sender wallet is not found")
    void testTransferSenderNotFound() {
        when(walletRepository.findByUserId(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                walletService.transfer(1L, 2L, new BigDecimal("10.00"))
        );

        assertEquals("Remetente não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when receiver wallet is not found")
    void testTransferReceiverNotFound() {
        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(senderWallet));
        when(walletRepository.findByUserId(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                walletService.transfer(1L, 2L, new BigDecimal("10.00"))
        );

        assertEquals("Destinatário não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Should deposit amount to user wallet")
    void testDepositSuccess() {
        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(senderWallet));

        walletService.deposit(1L, new BigDecimal("50.00"));

        assertEquals(new BigDecimal("150.00"), senderWallet.getBalance());
        verify(walletRepository).save(senderWallet);
    }

    @Test
    @DisplayName("Should withdraw amount from user wallet")
    void testWithdrawSuccess() {
        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(senderWallet));

        walletService.withdraw(1L, new BigDecimal("40.00"));

        assertEquals(new BigDecimal("60.00"), senderWallet.getBalance());
        verify(walletRepository).save(senderWallet);
    }

    @Test
    @DisplayName("Should throw exception when withdraw amount exceeds balance")
    void testWithdrawInsufficientBalance() {
        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(senderWallet));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                walletService.withdraw(1L, new BigDecimal("200.00"))
        );

        assertEquals("Saldo insuficiente", exception.getMessage());
        assertEquals(new BigDecimal("100.00"), senderWallet.getBalance());
        verify(walletRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should get balance of user wallet")
    void testGetBalanceSuccess() {
        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(senderWallet));

        BigDecimal balance = walletService.getBalance(1L);

        assertEquals(new BigDecimal("100.00"), balance);
    }
}
