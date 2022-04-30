package com.nus.team3.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

import com.nus.team3.dto.User;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
public interface AccountServiceMapper {
    class Statements {
        static final String USER_GET = "SELECT * FROM user WHERE username=#{username}";
        static final String USER_CREATE = "INSERT INTO user (email,username,password,loggon_i)" +
                "VALUES (#{email}, #{username}, #{password}, #{loggon_i})";
        static final String USER_LOGIN = "UPDATE user " +
                "SET loggon_i = 1 " +
                "WHERE username = #{username}";
        static final String USER_LOGOFF = "UPDATE user " +
                "SET loggon_i = 0 " +
                "WHERE username = #{username}";
        static final String USER_GET_PASSWORD = "SELECT password FROM user WHERE username=#{username}";
    }

    @Select({ Statements.USER_GET })
    @Results({
            @Result(property = "email", column = "email"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "loggon_i", column = "loggon_i"),

    })
    List<User> getUserInfo(@Param("username") String username);


    @Insert({ Statements.USER_CREATE })
    public int createNewAccount(User user);

    @Update({ Statements.USER_LOGIN })
    int userLogon(@Param("username") String username);

    @Update({ Statements.USER_LOGOFF })
    int userLogoff(@Param("username") String username);

    @Select({ Statements.USER_GET_PASSWORD})
    @Results({
        @Result(property = "password", column = "password")
    })
    List<String> validatePassword(@Param("username") String username);
    

}