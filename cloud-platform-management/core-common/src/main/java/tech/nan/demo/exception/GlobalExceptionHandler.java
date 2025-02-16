package tech.nan.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.nan.demo.common.DynamicResponse;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<DynamicResponse<?>> handleBaseException(BaseException e) {
        log.error("BaseException: {}", e.getMsg(), e);
        return new ResponseEntity<>(DynamicResponse.fail(null, e.getErrorCode(), e.getMsg()), e.getHttpStatus());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DynamicResponse<?>> handleException(Exception e) {
        return handleBaseException(new BaseException(e, e.getMessage()));
    }


}
