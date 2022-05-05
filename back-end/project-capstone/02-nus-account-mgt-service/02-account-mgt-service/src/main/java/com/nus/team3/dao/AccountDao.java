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
    public String createNewAccount(@RequestBody User user) {
        logger.info("Username is {}", user.getUsername());
        String json = "";
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("data", null);
        resultMap.put("message", "Signup failed!");
        resultMap.put("success", false);

        sqlSessionTemplate.insert(rootMapperPath + createUserQuery, user);

        int userId = createUser(user.getUsername(), user.getEmail());
        if (userId != -1) {
            Map<String, Integer> data = Map.of("userId", userId);
            resultMap.put("data", data);
            resultMap.put("message", "Signup completed!");
            resultMap.put("success", true);
        }

        try {
            json = new ObjectMapper().writeValueAsString(resultMap);
        } catch (JsonProcessingException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        return json;
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
                int userId = getUserId(username);
                if (userId != -1) {
                    Map<String, Integer> data = Map.of("userId", userId);
                    resultMap.put("data", data);
                    resultMap.put("message", "Login successfully!");
                    resultMap.put("success", true);
                }
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

    private int getUserId(String username) {
        int userId = -1;
        try {
            String jdbcUrl = env.getProperty("spring.datasource.url").replace("account_service_db",
                    "order_matching_service_db");
            String jdbcUsername = env.getProperty("spring.datasource.username");
            String jdbcPassword = env.getProperty("spring.datasource.password");
            Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            PreparedStatement stmt = conn.prepareStatement("SELECT id FROM user_account_tab WHERE name = ? LIMIT 1");

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userId = rs.getInt("id");
                // System.out.println("User ID is " + userId);
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return userId;
    }

    private int createUser(String username, String email) {
        int userId = -1;
        try {
            String jdbcUrl = env.getProperty("spring.datasource.url").replace("account_service_db",
                    "order_matching_service_db");
            String jdbcUsername = env.getProperty("spring.datasource.username");
            String jdbcPassword = env.getProperty("spring.datasource.password");
            Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO user_account_tab (name, email, create_time, update_time) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            long timestampNow = Instant.now().getEpochSecond();
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setLong(3, timestampNow);
            stmt.setLong(4, timestampNow);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                System.err.println("Creating user failed, no rows affected.");
            }

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        return userId;
    }
}
