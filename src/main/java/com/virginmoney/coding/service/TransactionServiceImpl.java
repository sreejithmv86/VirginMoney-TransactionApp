package com.virginmoney.coding.service;

import com.virginmoney.coding.entity.Transaction;
import com.virginmoney.coding.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllTransactionsForCategory(String category) {
        return transactionRepository.findByCategoryIgnoreCaseOrderByTransactionDateDesc(category);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Map<String, Double> getCategoryWiseTotalSpend() {
        List<Transaction> transactions = transactionRepository.findAll();
        Map<String, Double> map = transactions.stream().collect(
                Collectors.groupingBy(Transaction::getCategory, Collectors.summingDouble(Transaction::getAmount)));

        return map;
    }

    @Override
    public Map<String, Double> getMonthlyAverageOnCategory(YearMonth yearMonth, String category) {
        List<Transaction> transactions = getAllTransactionsForCategory(category);
//        LocalDate dateFromRequest = yearMonth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Map<String, Double> result = new HashMap<>();
        Double monthlyAverage = transactions.stream().filter(t -> {
            if (Objects.nonNull(t.getTransactionDate())) {
                LocalDate dateFromDB = t.getTransactionDate();
                return yearMonth.getYear() == dateFromDB.getYear() && yearMonth.getMonthValue() == dateFromDB.getMonthValue();
            }
            return false;
        }).collect(Collectors.averagingDouble(Transaction::getAmount));
        result.put(category,monthlyAverage);
        return result;
    }

    @Override
    public List<Transaction> getHighestSpendInGivenCategoryForYear(Year year, String category) {
        List<Transaction> transactions = getAllTransactionsForCategory(category);
//        LocalDate dateFromRequest = year.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        OptionalDouble maxValue = transactions.stream().filter(t -> {
            if (Objects.nonNull(t.getTransactionDate())) {
                LocalDate dateFromDB = t.getTransactionDate();
                return year.getValue() == dateFromDB.getYear();
            }
            return false;
        }).mapToDouble(Transaction::getAmount).max();
        List<Transaction> result = maxValue.isPresent() ? transactions.stream()
                .filter(t -> t.getAmount().doubleValue() == maxValue.getAsDouble())
                .collect(Collectors.toList()) : null;
        return result;
    }

    @Override
    public List<Transaction> getLowestSpendInGivenCategoryForYear(Year year, String category) {
        List<Transaction> transactions = getAllTransactionsForCategory(category);
//        LocalDate dateFromRequest = year.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        OptionalDouble minValue = transactions.stream().filter(t -> {
            if (Objects.nonNull(t.getTransactionDate())) {
                LocalDate dateFromDB = t.getTransactionDate();
                return year.getValue() == dateFromDB.getYear();
            }
            return false;
        }).mapToDouble(Transaction::getAmount).min();
        List<Transaction> result = minValue.isPresent() ? transactions.stream()
                .filter(t -> t.getAmount().doubleValue() == minValue.getAsDouble())
                .collect(Collectors.toList()) : null;
        return result;
    }
}
