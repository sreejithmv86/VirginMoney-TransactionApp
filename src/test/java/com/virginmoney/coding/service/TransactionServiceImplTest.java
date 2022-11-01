package com.virginmoney.coding.service;

import com.virginmoney.coding.entity.Transaction;
import com.virginmoney.coding.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @InjectMocks
    private  TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository mockTransactionRepository;

    @Test
    public void testWhenCategoryEmptyThenReturnEmptyList(){
        Mockito.when(mockTransactionRepository.findByCategoryIgnoreCaseOrderByTransactionDateDesc(""))
                .thenReturn(Arrays.asList());
        assertTrue(transactionService.getAllTransactionsForCategory("").isEmpty());
    }

    @Test
    public void testWhenValidCategoryThenReturnList(){
        List<Transaction> transactionList = getTransactions();
        Mockito.when(mockTransactionRepository.findByCategoryIgnoreCaseOrderByTransactionDateDesc("abcd"))
                .thenReturn(transactionList);
        List<Transaction> transactionListFromDB = transactionService.getAllTransactionsForCategory("abcd");
        assertEquals(transactionList.size(),transactionListFromDB.size());
        assertEquals(1000L,transactionListFromDB.stream().findFirst().get().getTransactionId());
    }

    @Test
    public void testWhenNoRecordsInCategorySpendThenEmptyList(){
        Mockito.when(mockTransactionRepository.findAll())
                .thenReturn(Arrays.asList());
        assertTrue(transactionService.getCategoryWiseTotalSpend().isEmpty());
    }

    @Test
    public void testWhenValidRecordsInCategorySpendThenReturnMap(){
        List<Transaction> transactionList = getTransactions();
        Mockito.when(mockTransactionRepository.findAll())
                .thenReturn(transactionList);
        Map<String, Double> transactionMapFromDB = transactionService.getCategoryWiseTotalSpend();
        assertEquals(1000.23,transactionMapFromDB.get("category1"));
    }

    @Test
    public void testWhenNoRecordsInMonthlyAverageCalculationThenReturnZero(){
        List<Transaction> transactionList = getTransactions();
        Mockito.when(mockTransactionRepository.findByCategoryIgnoreCaseOrderByTransactionDateDesc("category1"))
                .thenReturn(transactionList);
        YearMonth yearMonth = YearMonth.now();
        Map<String, Double> result = transactionService.getMonthlyAverageOnCategory(yearMonth,"category1");
        assertEquals(0.0,result.get("category1"));
    }

    @Test
    public void testWhenSingleRecordInMonthlyAverageCalculationThenReturnAmount(){
        List<Transaction> transactionList = getTransactions();
        transactionList.get(0).setTransactionDate(LocalDate.now());
        Mockito.when(mockTransactionRepository.findByCategoryIgnoreCaseOrderByTransactionDateDesc("category1"))
                .thenReturn(transactionList);
        YearMonth yearMonth = YearMonth.now();
        Map<String, Double> result = transactionService.getMonthlyAverageOnCategory(yearMonth,"category1");
        assertEquals(1000.23,result.get("category1"));
    }

    @Test
    public void testWhenMultipleRecordInMonthlyAverageCalculationThenReturnAverage(){
        List<Transaction> transactionList = getMultipleTransactions();
        Mockito.when(mockTransactionRepository.findByCategoryIgnoreCaseOrderByTransactionDateDesc("category1"))
                .thenReturn(transactionList);
        YearMonth yearMonth = YearMonth.now();
        Map<String, Double> result = transactionService.getMonthlyAverageOnCategory(yearMonth,"category1");
        assertEquals(1500.345,result.get("category1"));
    }

    @Test
    public void testWhenNoTransactionsInHighestSpendThenReturnNull(){
        Mockito.when(mockTransactionRepository.findByCategoryIgnoreCaseOrderByTransactionDateDesc("category1"))
                .thenReturn(Arrays.asList());
        assertNull(transactionService.getHighestSpendInGivenCategoryForYear(Year.now(),"category1"));
    }

    @Test
    public void testWhenMultipleRecordsInHighestSpendReturnMax(){
        List<Transaction> transactionList = getMultipleTransactions();
        Mockito.when(mockTransactionRepository.findByCategoryIgnoreCaseOrderByTransactionDateDesc("category1"))
                .thenReturn(transactionList);
        Year year = Year.now();
        List<Transaction> result = transactionService.getHighestSpendInGivenCategoryForYear(year,"category1");
        assertEquals(2000.46,result.get(0).getAmount());
    }

    @Test
    public void testWhenNoTransactionsInLowestSpendThenReturnNull(){
        Mockito.when(mockTransactionRepository.findByCategoryIgnoreCaseOrderByTransactionDateDesc("category1"))
                .thenReturn(Arrays.asList());
        assertNull(transactionService.getLowestSpendInGivenCategoryForYear(Year.now(),"category1"));
    }

    @Test
    public void testWhenMultipleRecordsInLowestSpendReturnMin(){
        List<Transaction> transactionList = getMultipleTransactions();
        Mockito.when(mockTransactionRepository.findByCategoryIgnoreCaseOrderByTransactionDateDesc("category1"))
                .thenReturn(transactionList);
        Year year = Year.now();
        List<Transaction> result = transactionService.getLowestSpendInGivenCategoryForYear(year,"category1");
        assertEquals(1000.23,result.get(0).getAmount());
    }

    private List<Transaction> getTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        Transaction t1 = new Transaction();
        t1.setTransactionId(1000L);
        t1.setCategory("category1");
        t1.setAmount(1000.23);
        transactionList.add(t1);
        return transactionList;
    }

    private List<Transaction> getMultipleTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        Transaction t1 = new Transaction();
        t1.setTransactionId(1000L);
        t1.setCategory("category1");
        t1.setAmount(1000.23);
        t1.setTransactionDate(LocalDate.now());

        Transaction t2 = new Transaction();
        t2.setTransactionId(2000L);
        t2.setCategory("category1");
        t2.setAmount(2000.46);
        t2.setTransactionDate(LocalDate.now());
        transactionList.add(t1);
        transactionList.add(t2);
        return transactionList;
    }
}
