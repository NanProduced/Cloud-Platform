package tech.nan.demo.exception.exceptions;

import tech.nan.demo.exception.BaseException;
import tech.nan.demo.exception.ExceptionEnum;

public class UnsupportedException extends BaseException {

    public UnsupportedException(ExceptionEnum exceptionEnum, String msg) {
        super(exceptionEnum, msg);
    }
}
