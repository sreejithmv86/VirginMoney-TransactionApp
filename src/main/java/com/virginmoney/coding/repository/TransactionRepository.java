package com.virginmoney.coding.repository;

import com.virginmoney.coding.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCategoryOrderByTransactionDateDesc(String category);
}
