package com.nus.team3.utils.threads;

import com.nus.team3.dto.StockLiveDto;
import com.nus.team3.mapper.PositionMapper;
import com.nus.team3.mapper.StockLiveMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockThreadUpdate {

  public static ScheduledExecutorService scheduledExecutorService;
  public static Random random;
  public static Integer variance = 20;
  public final StockLiveMapper stockLiveMapper;

  @Autowired
  public StockThreadUpdate(StockLiveMapper stockLiveMapper) {
    this.stockLiveMapper = stockLiveMapper;
  }

  public void start() {

    Runnable r = () -> {
      List<StockLiveDto> stockTickers = stockLiveMapper.findAll();
      stockTickers.forEach(
          s -> {
            var max = s.getPriceFixAround() + variance;
            var min = s.getPriceFixAround() - variance;
            double priceNow = random.nextInt((int) (max - min)) + min;
            s.setPriceLive(priceNow);
            stockLiveMapper.updateStockLivePriceByStock(s.getStockTicker(), priceNow);
          });
    };
    scheduledExecutorService.scheduleAtFixedRate(r, 0, 1000, TimeUnit.MILLISECONDS);
  }

  @PostConstruct
  public void init() {
    scheduledExecutorService = Executors.newScheduledThreadPool(1);
    random = new Random();
    start();
  }
}
