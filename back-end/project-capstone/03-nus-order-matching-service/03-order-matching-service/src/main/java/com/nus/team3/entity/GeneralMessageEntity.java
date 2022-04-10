package com.nus.team3.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@JsonInclude(Include.NON_NULL)
public class GeneralMessageEntity<T> {

  private T data;
  private String message;
  private Integer code;
  private String sessionId;

  @Getter
  @AllArgsConstructor
  public enum MessageEntityEnum {
    /**
     * Message
     */
    SUCCESS("Success"),
    FAILURE("Failure");

    private String name;
  }

  private GeneralMessageEntity(T data, String sessionId, Integer code, String message) {
    this.data = data;
    this.code = code;
    this.message = message;
    this.sessionId = sessionId;
  }

  private GeneralMessageEntity(Integer code, String message, T data) {
    this.data = data;
    this.code = code;
    this.message = message;
  }

  private GeneralMessageEntity(T data, Integer code) {
    this.data = data;
    this.code = code;
  }

  public GeneralMessageEntity(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public static <T> GeneralMessageEntity<T> ok(T data) {
    return new GeneralMessageEntity<>(data, HttpStatus.OK.value());
  }

  public static <T> GeneralMessageEntity<T> ok(int code, T data, String message) {
    return new GeneralMessageEntity<>(code, message, data);
  }

  public static <T> GeneralMessageEntity<T> ok(int code, String message) {
    return new GeneralMessageEntity<>(code, message);
  }

  public static <T> GeneralMessageEntity<T> ok(int code, T data) {
    return new GeneralMessageEntity<>(data, code);
  }

  public static <T> GeneralMessageEntity<T> badRequest(String message) {
    return new GeneralMessageEntity<>(HttpStatus.BAD_REQUEST.value(), message);
  }

  public static <T> GeneralMessageEntity<T> badRequest(int code, String message) {
    return new GeneralMessageEntity<>(code, message);
  }
}
