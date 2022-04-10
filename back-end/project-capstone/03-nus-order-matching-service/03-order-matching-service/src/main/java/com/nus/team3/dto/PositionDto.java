package com.nus.team3.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {

  private Integer id;
  private Integer userId;
  private String stockTicker;
  private String side;
  private Double price;
  private Integer quantity;
  private String status;
  private String transactionId;
  private String transactionIdAfterMatch;
  private String createTime;
  private String updateTime;

  private Double priceLive;

  private Double profitOrLoss;
  private Boolean isProfit;
}
