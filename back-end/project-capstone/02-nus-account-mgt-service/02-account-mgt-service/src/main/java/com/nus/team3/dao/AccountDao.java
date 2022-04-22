package com.nus.team3.dao;


import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.nus.team3.dto.User;

@RestController
@RequestMapping("/account")
public class AccountDao {

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    private String rootMapperPath = "com.nus.team3.mapper.AccountServiceMapper";
    private String selectAllQuery = ".getAllUser";
    
    @Autowired
    @Qualifier("mysqlSqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    @GetMapping("/getUserInfo")
    public List<User> getUserInfo(){
        return sqlSessionTemplate.selectList(rootMapperPath + selectAllQuery);
    }

    @PostMapping("/createNewAccount")
    public void createNewAccound(){

    }
    @PostMapping("/updateLoggon_i")
    public void updateLoggon_i(){


    }
    



    @GetMapping("/testing")
    public String testing(){
        return "hello world";
    }


    
}


