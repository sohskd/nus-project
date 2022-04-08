package com.nus.team3.dao;

import com.nus.team3.dto.PositionDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/save")
public class TransactionDao {

    @Autowired
    @Qualifier("mysqlSqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    @GetMapping("/testPosition")
    public List<PositionDto> testPosition(){
        return sqlSessionTemplate.selectList("com.nus.team3.mapper.TransactionHistoryMapper.getAllTxnHist");
    }
}
