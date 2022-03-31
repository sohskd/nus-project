package com.nus.team3.mapper;

import com.nus.team3.dto.PositionDto;
import java.util.List;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface PositionMapper {

  @Select("SELECT * FROM position_summary_tab")
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "stockTicker", column = "stock_ticker"),
      @Result(property = "status", column = "status"),
      @Result(property = "userId", column = "user_id"),
      @Result(property = "createTime", column = "create_time"),
      @Result(property = "updateTime", column = "update_time")
  })
  List<PositionDto> getPosition();
}
