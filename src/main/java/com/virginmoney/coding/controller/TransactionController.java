package com.virginmoney.coding.controller;

import com.virginmoney.coding.entity.Transaction;
import com.virginmoney.coding.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<Transaction> saveTransaction(
            @RequestBody Transaction transaction)
    {
        return  ResponseEntity.ok(transactionService.saveTransaction(transaction));
    }

    @GetMapping("/transactions/category/{category}")
    public ResponseEntity<List<Transaction>> getTransactionsByCategory(@PathVariable("category") String category){
        List<Transaction> transactionList = transactionService.getAllTransactionsForCategory(category);
        return ResponseEntity.ok(transactionList);
    }

    @GetMapping("/transactions/totalOutgoing/category")
    public ResponseEntity<Map<String, Double>> getTotalOutgoingPerCategory(){
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/transactions/month/{month}/category/{category}")
    public ResponseEntity<Map<String, Double>> getMonthlyAverageOnCategory(@PathVariable("month") @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
                                              @PathVariable("category")  String category){
        return ResponseEntity.ok(transactionService.getMonthlyAverageOnCategory(yearMonth,category));
    }

    @GetMapping("/transactions/category/{category}/max/year/{year}")
    public ResponseEntity<List<Transaction>> getHighestSpendInGivenCategoryForYear(@PathVariable("category") String category,
                                                                   @PathVariable("year") @DateTimeFormat(pattern = "yyyy") Year year){
        return ResponseEntity.ok(transactionService.getHighestSpendInGivenCategoryForYear(year,category));
    }

    @GetMapping("/transactions/category/{category}/min/year/{year}")
    public ResponseEntity<List<Transaction>> getLowestSpendInGivenCategoryForYear(@PathVariable("category") String category,
                                                                   @PathVariable("year") @DateTimeFormat(pattern = "yyyy") Year year){
        return ResponseEntity.ok(transactionService.getLowestSpendInGivenCategoryForYear(year,category));
    }

}
