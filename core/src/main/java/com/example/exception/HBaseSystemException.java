package com.example.exception;

import org.springframework.dao.UncategorizedDataAccessException;

/**
 * Created by lichao on 2017/3/10.
 */
public class HBaseSystemException extends UncategorizedDataAccessException {
  /**
   * Constructor for UncategorizedDataAccessException.
   * @param msg the detail message
   * @param cause the exception thrown by underlying data access API
   */
  public HBaseSystemException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
