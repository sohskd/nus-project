package com.nus.team3.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

import com.nus.team3.dto.User;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
public interface AccountServiceMapper {
    @Select({"SELECT * FROM user_account_tab WHERE username=#{username}"})
    @Results({
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "loggon_i", column = "loggon_i"),
           
    })
    List<User> getUserInfo(String username);

    @Insert("Insert into user_account_tab (username,email,password,loggon_i)" +
           "values (#{username}," +
           "#{email}," +
           "#{password}," +
           "#{loggon_i})")
    public Integer createNewAccount(User user);
     
//     @Update({
//             "<script>",
//             "UPDATE user_account_tab",
//             "<set>",
//             "<loggon_i = 1>",
//             "</set>",
//             "</set>",
//             "<where>",
//             "username =#{username}",
//             "</where>",
//             "</script>"

//     })
//     public void userLogon (@Param("username") boolean loggon_i);

//     @Update({
//             "<script>",
//             "UPDATE user_account_tab",
//             "<set>",
//             "<loggon_i = 0>",
//             "</set>",
//             "<where>",
//             "username =#{username}",
//             "</where>",
//             "</script>"

//     })
//     void userLogoff (@Param("username") boolean loggon_i);

}