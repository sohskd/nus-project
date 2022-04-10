package com.nus.team3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionPageEntity {

  private String stock;
  private Double profitLoss;
  private Double currentValue;
  private Double change;
}
