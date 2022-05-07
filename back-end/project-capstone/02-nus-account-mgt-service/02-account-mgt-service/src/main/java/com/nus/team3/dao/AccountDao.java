package com.nus.team3.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.team3.dto.User;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// NOTE: CORS origins MUST be exact match!
@CrossOrigin(origins = { "https://www.omni-trade.xyz", "*" })
@RestController
@RequestMapping("/account")
public class AccountDao {

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    private String rootMapperPath = "com.nus.team3.mapper.AccountServiceMapper";
    private String selectUserQuery = ".getUserInfo";
    private String createUserQuery = ".createNewAccount";
    private String updateLoggon_i_1 = ".userLogon";
    private String updateLoggon_i_0 = ".userLogoff";
    private String validatePassword = ".validatePassword";

    @Autowired
    @Qualifier("mysqlSqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    @PostMapping(value = "/getUserInfo", produces = "application/json")
    public List<User> getUserInfo(@RequestBody String username) {
        logger.info("[getUserInfo] Username is {}", username);
        return sqlSessionTemplate.selectList(rootMapperPath + selectUserQuery, username);
    }

    @PostMapping(value = "/createNewAccount", consumes = "application/json", produces = "application/json")
    public String createNewAccount(@RequestBody User user) {
        logger.info("[createNewAccount] Username is {}", user.getUsername());
        String json = "";
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("data", null);
        resultMap.put("message", "Signup failed!");
        resultMap.put("success", false);

        sqlSessionTemplate.insert(rootMapperPath + createUserQuery, user);

        Map<String, Long> data = Map.of("userId", user.getId());
        resultMap.put("data", data);
        resultMap.put("message", "Signup completed!");
        resultMap.put("success", true);

        try {
            json = new ObjectMapper().writeValueAsString(resultMap);
        } catch (JsonProcessingException e) {
            System.err.println("[createNewAccount] Got an exception!");
            e.printStackTrace();
        }

        return json;
    }

    @PostMapping(value = "/userLogon", produces = "application/json")
    public String userLogon(@RequestBody String messageBody) {
        String json = "";

        try {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("data", null);
            resultMap.put("message", "Login failed - a server error has occurred!");
            resultMap.put("success", false);

            String[] messageBodyList = messageBody.split("#");
            String username = messageBodyList[0];
            String password = messageBodyList[1];
            Map<String, String> authData = Map.of("username", username, "password", password);

            List<User> userList = sqlSessionTemplate.selectList(rootMapperPath + validatePassword,
                    authData);
            Integer userId = -1;
            if (userList.size() > 0) {
                sqlSessionTemplate.update(rootMapperPath + updateLoggon_i_1, username);
                userId = Math.toIntExact(userList.get(0).getId());
                Map<String, Integer> data = Map.of("userId", userId);
                resultMap.put("data", data);
                resultMap.put("message", "Login successfully!");
                resultMap.put("success", true);
            } else {
                resultMap.put("message", "Login failed - Invalid username and/or password!");
            }

            json = new ObjectMapper().writeValueAsString(resultMap);
        } catch (Exception e) {
            System.err.println("[userLogon] Got an exception!");
            e.printStackTrace();
        }
        return json;
    }

    @PutMapping("/userLogoff/{username}")
    public int userLogoff(@PathVariable(value = "username") String username) {
        logger.info("[userLogoff] Username is {}", username);
        sqlSessionTemplate.update(rootMapperPath + updateLoggon_i_0, username);
        return 1;
    }

    @GetMapping("/testing")
    public String testing() {
        return "hello world";
    }
}
