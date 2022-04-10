package com.nus.team3.service.impl;

import com.nus.team3.dto.PositionDto;
import com.nus.team3.mapper.PositionMapper;
import com.nus.team3.mapper.UserAccountMapper;
import com.nus.team3.service.PositionService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionServiceImpl implements PositionService {

  private final PositionMapper positionMapper;
  private final UserAccountMapper userAccountMapper;

  @Autowired
  public PositionServiceImpl(PositionMapper positionMapper,
      UserAccountMapper userAccountMapper) {
    this.positionMapper = positionMapper;
    this.userAccountMapper = userAccountMapper;
  }

  @Override
  public List<PositionDto> getAllPosition() {
    return positionMapper.getPosition(null);
  }

  @Override
  public List<PositionDto> getAllPositionByUserId(Integer userId) {
    List<PositionDto> stocksHeld = positionMapper.getPosition(userId);
    List<PositionDto> ans = stocksHeld.stream().map(s -> {
      double livePriceTotal;
      double transactedPriceTotal;

      switch (s.getSide()) {
        case "BUY":
          if (s.getStatus().equals("MATCHED")) {
            livePriceTotal = s.getQuantity() * s.getPriceLive();
            transactedPriceTotal = s.getQuantity() * s.getPrice();
            s.setIsProfit(livePriceTotal >= transactedPriceTotal);
            s.setProfitOrLoss(Math.abs(livePriceTotal - transactedPriceTotal));
          }
          break;
        case "SELL":
          if (s.getStatus().equals("MATCHED")) {
            livePriceTotal = s.getQuantity() * s.getPriceLive();
            transactedPriceTotal = s.getQuantity() * s.getPrice();
            s.setIsProfit(livePriceTotal < transactedPriceTotal);
            s.setProfitOrLoss(Math.abs(livePriceTotal - transactedPriceTotal));
          }
          break;
        default:
      }

      return s;
    }).collect(Collectors.toList());
    return ans;
  }
}
