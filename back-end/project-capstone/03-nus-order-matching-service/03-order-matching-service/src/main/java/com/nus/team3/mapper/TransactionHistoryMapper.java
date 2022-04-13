package com.nus.team3.mapper;

import com.nus.team3.dto.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TransactionHistoryMapper {
    @Select("SELECT * FROM transaction_history_tab")
    @Results({
            @Result(property = "user", column = "user_id"),
            @Result(property = "stockTicker", column = "stock_ticker"),
            @Result(property = "buyOrSell", column = "side"),
            @Result(property = "price", column = "price"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "matchStatus", column = "status"),
            @Result(property = "transactionId", column = "transaction_id"),
            @Result(property = "transactionIdAfterMatch", column = "transaction_id_after_match"),
            @Result(property = "timestamp", column = "create_time"),
    })
    List<Order> getAllTxnHist();

    @Insert("Insert into transaction_history_tab (user_id,stock_ticker,side,price,quantity,status,transaction_id,transaction_id_after_match,create_time)" +
            "values (#{user}," +
            "#{stockTicker}," +
            "#{buyOrSell}," +
            "#{price}," +
            "#{quantity}," +
            "#{matchStatus}," +
            "#{transactionId}," +
            "#{transactionIdAfterMatch}," +
            "#{timestamp})")
    public Integer saveTxn(Order order);
}
