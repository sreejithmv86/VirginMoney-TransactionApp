package com.virginmoney.coding.controller;

import com.virginmoney.coding.entity.Transaction;
import com.virginmoney.coding.service.TransactionService;
import com.virginmoney.coding.utils.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/save")
    public ResponseEntity<Transaction> saveTransaction(
            @RequestBody Transaction transaction)
    {
        return  ResponseEntity.ok(transactionService.saveTransaction(transaction));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Transaction>> getTransactionsByCategory(@PathVariable("category") String category){
        List<Transaction> transactionList = transactionService.getAllTransactionsForCategory(category);
        return ResponseEntity.ok(transactionList);
    }

    @GetMapping("/category-wise/totalSpend")
    public ResponseEntity<Map<String, Double>> getTotalOutgoingPerCategory(){
        return ResponseEntity.ok(transactionService.getCategoryWiseTotalSpend());
    }

    @GetMapping("/category/{category}/average/{yearMonth}")
    public ResponseEntity<Map<String, Double>> getMonthlyAverageOnCategory(@PathVariable("category")String category,
                                                                           @PathVariable("yearMonth") String yearMonth){
        if(!RequestValidator.isValidYearMonthPattern(yearMonth)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(transactionService.getMonthlyAverageOnCategory(YearMonth.parse(yearMonth,DateTimeFormatter.ofPattern("yyyy-MM")),category));
    }

    @GetMapping("/category/{category}/maxSpend/year/{year}")
    public ResponseEntity<List<Transaction>> getHighestSpendInGivenCategoryForYear(@PathVariable("category") String category,
                                                                                   @PathVariable("year") String year){
        if(!RequestValidator.isValidYearPattern(year)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(transactionService.getHighestSpendInGivenCategoryForYear(Year.parse(year,DateTimeFormatter.ofPattern("yyyy")),category));
    }

    @GetMapping("/category/{category}/minSpend/year/{year}")
    public ResponseEntity<List<Transaction>> getLowestSpendInGivenCategoryForYear(@PathVariable("category") String category,
                                                                   @PathVariable("year") String year){
        if(!RequestValidator.isValidYearPattern(year)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(transactionService.getLowestSpendInGivenCategoryForYear(Year.parse(year, DateTimeFormatter.ofPattern("yyyy")),category));
    }

}
