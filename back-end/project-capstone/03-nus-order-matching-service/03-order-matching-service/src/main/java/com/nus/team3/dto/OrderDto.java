package com.nus.team3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String buyOrSell;
    private String transactionId;
    private String transactionIdAfterMatch;
    private String stockTicker;
    private String user;
    private long timestamp;
    private float price;
    private int quantity;
    private String matchStatus;
}
