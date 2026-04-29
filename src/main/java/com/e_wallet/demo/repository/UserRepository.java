package com.e_wallet.demo.repository;

import com.e_wallet.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
        // O JpaRepository já entrega métodos como save(), findById(), delete() prontos.
}

