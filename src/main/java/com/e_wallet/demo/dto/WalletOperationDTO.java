package com.e_wallet.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class WalletOperationDTO {

    @NotNull(message = "ID do usuário é obrigatório")
    private Long userId;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser maior que zero")
    private BigDecimal amount;

    public WalletOperationDTO() {}

    public WalletOperationDTO(Long userId, BigDecimal amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
