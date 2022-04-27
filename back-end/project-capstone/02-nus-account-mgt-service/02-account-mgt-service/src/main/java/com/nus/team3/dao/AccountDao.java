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
    private String updateLoggon_iQuery = ".userLogon";

    
    @Autowired
    @Qualifier("mysqlSqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    @GetMapping("/getUserInfo")
    public List<User> getUserInfo(){
        return sqlSessionTemplate.selectList(rootMapperPath + selectUserQuery);
    }

    @PostMapping("/createNewAccount")
    public int createNewAccount(@RequestBody User user){
        User newUser = new User();
        sqlSessionTemplate.insert(rootMapperPath + createUserQuery, newUser);
        return 1;
    }

    @PostMapping("/userLogon")
    public String userLogon(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Map<String, Object> map, HttpSession session){ //not sure should have one more session class or not
                if(!StringUtils.isNullOrEmpty(username) &&"123456".equals(password)){ //hardcode password
                    session.setAttribute("loginUser",username);
                    sqlSessionTemplate.insert(rootMapperPath + updateLoggon_iQuery, username);
                    return"redirect:main.html";
                }else{
                    return "Loggin Failed";
                }
    }



    @GetMapping("/testing")
    public String testing(){
        return "hello world";
    }


    
}


