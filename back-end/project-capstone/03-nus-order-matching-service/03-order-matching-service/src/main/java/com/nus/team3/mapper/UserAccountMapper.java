package com.nus.team3.mapper;

import com.nus.team3.dto.UserAccountDto;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserAccountMapper {

  @Select({"SELECT * FROM user_account_tab WHERE user_id=#{userId}"})
  @Results(value = {
      @Result(property = "id", column = "id"),
      @Result(property = "email", column = "email"),
      @Result(property = "name  ", column = "name"),
      @Result(property = "createTime", column = "create_time"),
      @Result(property = "updateTime", column = "update_time"),
  })
  UserAccountDto findByUserId(Long userId);
}
