package com.jacobs.exception;

/**
 * Created by lichao on 2017/3/10.
 */
public class HBaseSystemException extends RuntimeException {

  /**
   * Constructor for UncategorizedDataAccessException.
   *
   * @param msg the detail message
   * @param cause the exception thrown by underlying data access API
   */
  public HBaseSystemException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
