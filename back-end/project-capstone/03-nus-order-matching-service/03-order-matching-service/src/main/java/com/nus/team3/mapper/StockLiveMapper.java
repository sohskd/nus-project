package com.nus.team3.mapper;

import com.nus.team3.dto.StockLiveDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
public interface StockLiveMapper {

  @Select({"SELECT stock_ticker, price, price_fix_around from stock_live_tab"})
  @Results({
      @Result(property = "stockTicker", column = "stock_ticker"),
      @Result(property = "priceLive", column = "price"),
      @Result(property = "priceFixAround", column = "price_fix_around")
  })
  List<StockLiveDto> findAll();

  @Update({
      "<script>",
      "UPDATE stock_live_tab",
      "<set>",
      "<if test=\"priceLive != null\"> price=#{priceLive} </if>",
      "</set>",
      "<where>",
      "<if test=\"stockTicker != null\"> stock_ticker=#{stockTicker} </if>",
      "</where>",
      "</script>"
  })
  void updateStockLivePriceByStock(@Param("stockTicker") String stockTicker,
      @Param("priceLive") Double priceLive);
}
