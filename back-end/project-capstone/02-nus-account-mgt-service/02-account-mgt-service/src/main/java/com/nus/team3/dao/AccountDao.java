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

import com.nus.team3.dto.User;

// NOTE: CORS origins MUST be exact match!
@CrossOrigin(origins = {"https://www.omni-trade.xyz"})
@RestController
@RequestMapping("/account")
public class AccountDao {

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    private String rootMapperPath = "com.nus.team3.mapper.AccountServiceMapper";
    private String selectUserQuery = ".getUserInfo";
    private String createUserQuery = ".createNewAccount";
    private String updateLoggon_i_1 = ".userLogon";
    private String updateLoggon_i_0 = ".userLogoff";
    private String retrievePassword = ".validatePassword";

    
    @Autowired
    @Qualifier("mysqlSqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    @PostMapping(value = "/getUserInfo", produces = "application/json")
    public List<User> getUserInfo(@RequestBody String username){
        logger.info("Username is {}", username);
        return sqlSessionTemplate.selectList(rootMapperPath + selectUserQuery, username);
    }

    @PostMapping(value="/createNewAccount", consumes = "application/json")
    public int createNewAccount(@RequestBody User user){
        logger.info("Username is {}", user.getUsername());
        sqlSessionTemplate.insert(rootMapperPath + createUserQuery, user);
        return 1;
    }

    @PostMapping("/userLogon")
    public String userLogon(@RequestBody String messageBody){
        String[] messageBodyList = messageBody.split("#");
        String username = messageBodyList[0];
        String password = messageBodyList[1];
        
        List<String> retrievedPasswords = sqlSessionTemplate.selectList(rootMapperPath + retrievePassword, username);
        if (password.equals(retrievedPasswords.get(0)) ) {
            sqlSessionTemplate.update(rootMapperPath + updateLoggon_i_1, username);                       
            return "Login successfully!";
        }
        else {
            return "Login failed!";
        }            
    }

    @PutMapping("/userLogoff/{username}")
    public int userLogoff(@PathVariable(value = "username") String username){
        logger.info("Username is {}", username);
        sqlSessionTemplate.update(rootMapperPath + updateLoggon_i_0, username);
        return 1;
    }

    @GetMapping("/testing")
    public String testing(){
        return "hello world";
    }


    
}


