package com.nus.team3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDto {

  private Integer id;
  private String email;
  private String name;
  private String createTime;
  private String updateTime;
}
