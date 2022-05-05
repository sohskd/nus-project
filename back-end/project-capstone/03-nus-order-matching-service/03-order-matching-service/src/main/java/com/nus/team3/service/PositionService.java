package com.nus.team3.service;

import com.nus.team3.dto.PositionDto;
import com.nus.team3.dto.StockLiveDto;
import java.util.List;

public interface PositionService {

    List<PositionDto> getAllPosition();

    List<PositionDto> getAllPositionByUserId(Integer userId);

    List<StockLiveDto> getStockLive();
}
