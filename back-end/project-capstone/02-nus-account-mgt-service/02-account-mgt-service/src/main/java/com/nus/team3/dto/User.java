package com.nus.team3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

  //private Integer id;

  private String email;

  private String username;

  private String password;

  private Boolean loggon_i;  

 

  public User(String username, String email, String password, Boolean loggon_i) {
  
    this.email = email;
    this.username = username;
    this.password = password;
    this.loggon_i = loggon_i;
  }

  // public Integer getId(){
  //  return id;
  // }

  public String getEmail(){
    return email;
  }
  
  public String getUsername(){
    return username;
  }

  public String getPassword(){
    return password;
  }

  public Boolean getLoggon_i(){
    return loggon_i;
  }

  
  public void setEmail(String email){
     this.email = email;
  }
  
  public void setUsername(String username){
    this.username = username;
  }
  
  public void setPassword(String password){
    this.password = password;
  }

  public void setLoggon_i(Boolean loggon_i){
    this.loggon_i = loggon_i;
  }
}











