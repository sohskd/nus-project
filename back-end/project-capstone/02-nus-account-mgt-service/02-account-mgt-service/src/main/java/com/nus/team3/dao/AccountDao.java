package com.nus.team3.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.mysql.cj.util.StringUtils;
import com.nus.team3.dto.User;

@RestController
@RequestMapping("/account")
public class AccountDao {

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    private String rootMapperPath = "com.nus.team3.mapper.AccountServiceMapper";
    private String selectUserQuery = ".getUserInfo";
    private String createUserQuery = ".createNewAccount";
    private String updateLoggon_i_1 = ".userLogon";
    private String updateLoggon_i_0 = ".userLogoff";

    
    @Autowired
    @Qualifier("mysqlSqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    @GetMapping("/getUserInfo")
    public List<User> getUserInfo(Integer id){
        return sqlSessionTemplate.selectList(rootMapperPath + selectUserQuery, id);
    }

    @PostMapping("/createNewAccount")
    public int createNewAccount(@RequestBody User user){
        User newUser = new User();
        sqlSessionTemplate.insert(rootMapperPath + createUserQuery, newUser);
        return 1;
    }

    @PutMapping("/userLogon")
    public void userLogon(@RequestParam("username") String username,
                            @RequestParam("password") String password){
        sqlSessionTemplate.update(rootMapperPath + updateLoggon_i_1, username);                       
                            
    }

    @PutMapping("/userLogoff")
    public void userLogoff(@RequestParam("username") String username){
        sqlSessionTemplate.update(rootMapperPath + updateLoggon_i_0, username);
    }

    @GetMapping("/testing")
    public String testing(){
        return "hello world";
    }


    
}


