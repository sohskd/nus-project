package com.nus.team3.service.impl;

import com.nus.team3.dto.PositionDto;
import com.nus.team3.mapper.PositionMapper;
import com.nus.team3.mapper.UserAccountMapper;
import com.nus.team3.service.PositionService;
import java.util.List;
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
    return positionMapper.getPosition(userId);
  }
}
