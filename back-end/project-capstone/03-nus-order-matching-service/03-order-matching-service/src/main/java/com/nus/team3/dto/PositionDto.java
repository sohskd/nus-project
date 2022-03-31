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

  private String stockTicker;

  private String status;

  private String userId;

  private String createTime;

  private String updateTime;
}
