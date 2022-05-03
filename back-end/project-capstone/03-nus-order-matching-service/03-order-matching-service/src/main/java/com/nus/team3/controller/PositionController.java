package com.nus.team3.controller;

import com.nus.team3.dto.PositionDto;
import com.nus.team3.entity.GeneralMessageEntity;
import com.nus.team3.entity.PositionPageEntity;
import com.nus.team3.service.PositionService;
import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.annotations.Api;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// NOTE: CORS origins MUST be exact match!
@CrossOrigin(origins = {"https://www.omni-trade.xyz"})
@Api
@Slf4j
@RestController
@RequestMapping("/api")
public class PositionController {

  private final PositionService positionService;

  @Autowired
  public PositionController(PositionService positionService) {
    this.positionService = positionService;
  }

  @GetMapping("/position")
  public GeneralMessageEntity<List<PositionDto>> getAllPositions(
      @RequestParam(required = false) Integer userId) {
    if (!Optional.ofNullable(userId).isPresent()) {
      return GeneralMessageEntity.ok(this.positionService.getAllPosition());
    }

    return GeneralMessageEntity.ok(this.positionService.getAllPositionByUserId(userId));
  }

  @GetMapping("/position/summary")
  public GeneralMessageEntity<List<PositionPageEntity>> getAllPositionSummary(
      @RequestParam(required = false) Integer userId) {

    if (null == userId) {
      throw new RuntimeException("Please provide a userId");
    }

    return null;
  }

}
