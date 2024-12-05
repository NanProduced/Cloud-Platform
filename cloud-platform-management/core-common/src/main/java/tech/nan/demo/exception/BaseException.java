package tech.nan.demo.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Data
public class BaseException extends RuntimeException {

    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    private int errorCode;

    private String msg;

    public BaseException(ExceptionEnum exceptionEnum, String msg) {
        super();
        errorCode = exceptionEnum.getCode();
        this.msg = msg;
    }

    public BaseException(ExceptionEnum exceptionEnum, String msg, HttpStatus httpStatus) {
        super();
        errorCode = exceptionEnum.getCode();
        this.msg = msg;
        this.httpStatus = httpStatus;
    }

    public BaseException(Throwable cause, String msg) {
        super(cause);
        errorCode = ExceptionEnum.SERVER_ERROR.getCode();
        this.msg = msg;
    }

    BaseException(ExceptionEnum exceptionEnum, Object[] args) {
        super();
        errorCode = exceptionEnum.getCode();
        this.msg = Arrays.toString(args);
    }


}
