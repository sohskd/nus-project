package com.nus.team3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockLiveDto {

  private String stockTicker;
  private Double priceLive;
  private Double priceFixAround;

}
