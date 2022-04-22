package com.nus.team3.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

import com.nus.team3.dto.User;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
public interface AccountServiceMapper {
    @Select({"SELECT * FROM user_account_tab WHERE id=#{id}"})
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
    public void createUserAccount(User user);
     
    @Update({
            "<script>",
            "UPDATE user_account_tab",
            "<set>",
            "<loggon_i = 1>",
            "</set>",
            "</script>"

    })
    void updateLoggin_i (User loggon_i);

}