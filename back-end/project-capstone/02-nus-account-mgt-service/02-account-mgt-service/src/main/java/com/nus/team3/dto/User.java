package com.nus.team3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = User.TABLE_NAME)
public class User {
  public static final String TABLE_NAME = "user_account_tab";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String email;
  private String username;
  private String password;
  private Boolean loggon_i;

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public Boolean getLoggon_i() {
    return loggon_i;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setLoggon_i(Boolean loggon_i) {
    this.loggon_i = loggon_i;
  }
}
