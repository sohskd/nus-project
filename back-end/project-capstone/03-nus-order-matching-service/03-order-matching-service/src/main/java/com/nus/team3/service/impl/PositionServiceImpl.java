package com.nus.team3.service.impl;

import com.nus.team3.dto.PositionDto;
import com.nus.team3.dto.StockLiveDto;
import com.nus.team3.mapper.PositionMapper;
import com.nus.team3.mapper.StockLiveMapper;
import com.nus.team3.mapper.UserAccountMapper;
import com.nus.team3.service.PositionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionServiceImpl implements PositionService {

  private final PositionMapper positionMapper;
  private final UserAccountMapper userAccountMapper;
  private final StockLiveMapper stockLiveMapper;

  @Autowired
  public PositionServiceImpl(PositionMapper positionMapper,
      UserAccountMapper userAccountMapper, StockLiveMapper stockLiveMapper) {
    this.positionMapper = positionMapper;
    this.userAccountMapper = userAccountMapper;
    this.stockLiveMapper = stockLiveMapper;
  }

  @Override
  public List<PositionDto> getAllPosition() {
    return positionMapper.getPosition(null);
  }

  @Override
  public List<PositionDto> getAllPositionByUserId(Integer userId) {
    List<PositionDto> stocksHeld = positionMapper.getPosition(userId);

    List<PositionDto> positionDtoList = stocksHeld.stream()
        .filter(s -> s.getStatus().equals("MATCHED")).collect(Collectors.toList());
    Set<String> stocks = positionDtoList.stream().map(PositionDto::getStockTicker)
        .collect(Collectors.toSet());

    Map<String, List<Double>> m = new HashMap<>();
    for (PositionDto positionDto : positionDtoList) {

      String stock = positionDto.getStockTicker();

      if (positionDto.getSide().equalsIgnoreCase("BUY")) {
        String keyBuy = stock + ":" + "BUY";
        List<Double> listOfPrice = m.getOrDefault(keyBuy, new ArrayList<>());
        for (int i = 0; i < positionDto.getQuantity(); i++) {
          listOfPrice.add(positionDto.getPrice());
        }
        m.put(keyBuy, listOfPrice);
      } else if (positionDto.getSide().equalsIgnoreCase("SELL")) {
        String keySell = stock + ":" + "SELL";
        List<Double> listOfPrice = m.getOrDefault(stock + ":" + "SELL", new ArrayList<>());
        for (int i = 0; i < positionDto.getQuantity(); i++) {
          listOfPrice.add(positionDto.getPrice());
        }
        m.put(keySell, listOfPrice);
      }
    }

    cancelOutStocks(m, stocks);

    // construct the position dto
    List<StockLiveDto> stockLiveDtoList = stockLiveMapper.findAll();
    List<PositionDto> ans = new ArrayList<>();
    for (Map.Entry<String, List<Double>> e : m.entrySet()) {

      PositionDto positionDto = new PositionDto();
      positionDto.setStatus("MATCHED");
      String[] stockAndSide =  e.getKey().split(":");
      String stockName = stockAndSide[0];
      positionDto.setStockTicker(stockName);
      positionDto.setSide(stockAndSide[1]);
      double avgPrice = e.getValue().stream().mapToDouble(d -> d).average().orElse(0.0);
      positionDto.setPrice(avgPrice);
      int qty = e.getValue().size();
      positionDto.setQuantity(qty);

      Optional<StockLiveDto> o = stockLiveDtoList.stream()
          .filter(stockLiveDto -> stockLiveDto.getStockTicker().equals(stockName)).findFirst();
      double priceLive = o.get().getPriceLive();
      double livePriceTotal = qty * priceLive;
      double transactedPriceTotal = qty * avgPrice;
      positionDto.setPriceLive(priceLive);
      positionDto.setIsProfit(livePriceTotal >= transactedPriceTotal);
      positionDto.setProfitOrLoss(Math.abs(livePriceTotal - transactedPriceTotal));

      ans.add(positionDto);
    }

    return ans;
  }

  @Override
  public List<StockLiveDto> getStockLive() {
    return this.stockLiveMapper.findAll();
  }

  private static void cancelOutStocks(Map<String, List<Double>> m, Set<String> stocks) {

    for (String stock : stocks) {
      String keyBuy = stock + ":" + "BUY";
      String keySell = stock + ":" + "SELL";

      List<Double> a = m.get(keyBuy);
      List<Double> b = m.get(keySell);

      if (a.size() > b.size()) {
        // more buy than sell.
        for (int i = 0; i < b.size(); i++) {
          a.remove(0);
        }
        m.put(keyBuy, a);
        m.remove(keySell);
      } else if (b.size() > a.size()) {
        // more sell than buy.
        for (int i = 0; i < a.size(); i++) {
          b.remove(0);
        }
        m.put(keySell, b);
        m.remove(keyBuy);
      } else {
        // equal
        m.remove(keyBuy);
        m.remove(keySell);
      }
    }

  }
}