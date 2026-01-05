package com.example.desafio_picpay_simplificado.repository;

import com.example.desafio_picpay_simplificado.model.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
