package com.e_wallet.demo.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance = BigDecimal.ZERO;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Wallet() {}

    public Wallet(Long id, BigDecimal balance, User user) {
        this.id = id;
        this.balance = balance != null ? balance : BigDecimal.ZERO;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void debit(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Objects.equals(id, wallet.id) && Objects.equals(balance, wallet.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance);
    }
}
