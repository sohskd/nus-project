package com.nus.team3.service;

import com.nus.team3.dto.PositionDto;
import com.nus.team3.mapper.PositionMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionServiceImpl implements PositionService {

  @Autowired
  PositionMapper positionMapper;

  @Override
  public List<PositionDto> getAllPosition() {
    return positionMapper.getPosition();
  }
}
