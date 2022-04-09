package com.nus.team3.mapper;

import com.nus.team3.dto.PositionDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface PositionMapper {

  @Select({"<script>",
      "SELECT * FROM transaction_history_tab",
      "<where>",
      "<if test=\"userId != null\">",
      "user_id=#{userId}",
      "</if>",
      "</where>",
      "</script>"})
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "userId", column = "user_id"),
      @Result(property = "stockTicker", column = "stock_ticker"),
      @Result(property = "side", column = "side"),
      @Result(property = "quantity", column = "quantity"),
      @Result(property = "status", column = "status"),
      @Result(property = "transactionId", column = "transaction_id"),
      @Result(property = "transactionIdAfterMatch", column = "transaction_id_after_match"),
      @Result(property = "createTime", column = "create_time"),
      @Result(property = "updateTime", column = "update_time")
  })
  List<PositionDto> getPosition(@Param("userId") Integer userId);

}
