package com.nus.team3.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

import com.nus.team3.dto.User;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
public interface AccountServiceMapper {
    class Statements {
        static final String USER_GET = "SELECT * FROM user WHERE id=#{id}";
        static final String USER_CREATE = "INSERT INTO user (email,username,password)" +
                "VALUES (#{email}, #{username}, #{password})";
        static final String USER_LOGIN = "UPDATE user " +
                "SET loggon_i = 1 " +
                "WHERE username = #{username} ";
        static final String USER_LOGOFF = "UPDATE user" +
                "SET loggon_i = 0" +
                "WHERE username = #{username}";
    }

    @Select({ Statements.USER_GET })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "loggon_i", column = "loggon_i"),

    })
    List<User> getUserInfo(Integer id);

    @Options(useGeneratedKeys=true, keyProperty="id")
    @Insert({ Statements.USER_CREATE })
    public void createNewAccount(User user);

    @Update({ Statements.USER_LOGIN })
    void userLogon(@Param("username") boolean loggon_i);

    @Update({ Statements.USER_LOGOFF })
    void userLogoff(@Param("username") boolean loggon_i);

}