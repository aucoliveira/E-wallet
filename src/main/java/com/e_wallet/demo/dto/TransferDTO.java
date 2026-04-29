package com.e_wallet.demo.dto;

import java.math.BigDecimal;

public class TransferDTO {

    private Long senderId;
    private Long receiverId;
    private BigDecimal amount;

    // Construtor vazio (necessário para o Jackson/Spring processar o JSON)
    public TransferDTO() {}

    public TransferDTO(Long senderId, Long receiverId, BigDecimal amount) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
    }

    // Getters e Setters
    public Long getSenderId() { return senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }

    public Long getReceiverId() { return receiverId; }
    public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
