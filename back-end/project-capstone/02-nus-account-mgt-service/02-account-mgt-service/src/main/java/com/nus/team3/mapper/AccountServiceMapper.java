package com.nus.team3.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

import com.nus.team3.dto.User;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
public interface AccountServiceMapper {
    class Statements {
        static final String USER_LOGIN = "UPDATE user_account_tab " +
                "SET loggon_i = 1 " +
                "WHERE username = #{username} ";
        static final String USER_LOGOFF = "UPDATE user_account_tab" +
                "SET loggon_i = 0" +
                "WHERE username = #{username}";
    }

    @Select({ "SELECT * FROM user_account_tab WHERE id=#{id}" })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "name", column = "name"),
            @Result(property = "password", column = "password"),
            @Result(property = "loggon_i", column = "loggon_i"),

    })
    List<User> getUserInfo(Integer id);

    @Insert("Insert into user_account_tab (id,email,name,password)" +
            "values (#{id}," +
            "#{email}," +
            "#{name}," +
            "#{password})")
    public void createNewAccount(User user);

    @Update({ Statements.USER_LOGIN })
    void userLogon(@Param("username") boolean loggon_i);

    @Update({ Statements.USER_LOGOFF })
    void userLogoff(@Param("username") boolean loggon_i);

}