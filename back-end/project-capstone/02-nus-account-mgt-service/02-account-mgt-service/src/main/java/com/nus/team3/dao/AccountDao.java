package com.nus.team3.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String retrievePassword = ".validatePassword";

    @Autowired
    @Qualifier("mysqlSqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private Environment env;

    @PostMapping(value = "/getUserInfo", produces = "application/json")
    public List<User> getUserInfo(@RequestBody String username) {
        logger.info("Username is {}", username);
        return sqlSessionTemplate.selectList(rootMapperPath + selectUserQuery, username);
    }

    @PostMapping(value = "/createNewAccount", consumes = "application/json")
    public int createNewAccount(@RequestBody User user) {
        logger.info("Username is {}", user.getUsername());
        sqlSessionTemplate.insert(rootMapperPath + createUserQuery, user);
        return 1;
    }

    @PostMapping(value = "/userLogon", produces = "application/json")
    public String userLogon(@RequestBody String messageBody) {
        String json = "";

        try {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("data", null);
            resultMap.put("message", "Login failed!");
            resultMap.put("success", false);

            String[] messageBodyList = messageBody.split("#");
            String username = messageBodyList[0];
            String password = messageBodyList[1];

            List<String> retrievedPasswords = sqlSessionTemplate.selectList(rootMapperPath + retrievePassword,
                    username);
            if (password.equals(retrievedPasswords.get(0))) {
                sqlSessionTemplate.update(rootMapperPath + updateLoggon_i_1, username);

                String jdbcUrl = env.getProperty("spring.datasource.url").replace("account_service_db",
                        "order_matching_service_db");
                String jdbcUsername = env.getProperty("spring.datasource.username");
                String jdbcPassword = env.getProperty("spring.datasource.password");
                Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
                Statement stmt = conn.createStatement();
                ResultSet rs;

                rs = stmt.executeQuery("SELECT id FROM user_account_tab WHERE name = '" + username + "' LIMIT 1");
                while (rs.next()) {
                    String userId = rs.getString("id");
                    System.out.println("User ID is " + userId);

                    Map<String, Integer> data = Map.of("userId", Integer.parseInt(userId));
                    resultMap.put("data", data);
                    resultMap.put("message", "Login successfully!");
                    resultMap.put("success", true);
                }
                conn.close();
            }
            json = new ObjectMapper().writeValueAsString(resultMap);
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return json;
    }

    @PutMapping("/userLogoff/{username}")
    public int userLogoff(@PathVariable(value = "username") String username) {
        logger.info("Username is {}", username);
        sqlSessionTemplate.update(rootMapperPath + updateLoggon_i_0, username);
        return 1;
    }

    @GetMapping("/testing")
    public String testing() {
        return "hello world";
    }

}
