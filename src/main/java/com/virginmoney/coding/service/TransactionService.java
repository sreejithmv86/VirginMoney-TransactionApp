package com.virginmoney.coding.service;

import com.virginmoney.coding.entity.Transaction;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TransactionService {

    List<Transaction> getAllTransactionsForCategory(String category);

    Transaction saveTransaction(Transaction transaction);

    Map<String, Double> getCategoryWiseTotalSpend();

    Map<String, Double> getMonthlyAverageOnCategory(YearMonth yearMonth, String category);

    List<Transaction> getHighestSpendInGivenCategoryForYear(Year year, String category);

    List<Transaction> getLowestSpendInGivenCategoryForYear(Year year, String category);
}
