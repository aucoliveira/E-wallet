package com.e_wallet.demo.repository;

import com.e_wallet.demo.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    // Método customizado para buscar a carteira pelo ID do usuário
    // O Spring entende o nome do método e gera o SQL automaticamente!
    Optional<Wallet> findByUserId(Long userId);
}