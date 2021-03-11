package org.paasta.container.platform.common.api.exception;

import org.paasta.container.platform.common.api.common.CommonUtils;
import org.paasta.container.platform.common.api.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Iterator;

/**
 * Global Exception Handler 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.08.27
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler({HttpClientErrorException.class})
    @ResponseBody
    public ErrorMessage handleException(HttpClientErrorException ex) {
        logger.info("ContainerPlatformException >>> " +  CommonUtils.loggerReplace(ex.getStatusText()));
        return new ErrorMessage(Constants.RESULT_STATUS_FAIL, ex.getStatusText(), ex.getRawStatusCode(), ex.getResponseBodyAsString());
    }

    @ExceptionHandler({CpRuntimeException.class})
    @ResponseBody
    public ErrorMessage handleException(CpRuntimeException ex) {
        logger.info("CpCommonAPIException >>> " +  CommonUtils.loggerReplace(ex.getErrorMessage()));
        return new ErrorMessage(ex.getErrorCode(), ex.getErrorMessage());
    }

    // 500
    @ExceptionHandler({Exception.class})
    public ErrorMessage handleAll(final Exception ex) {
        return new ErrorMessage(Constants.RESULT_STATUS_FAIL, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        String message = "Missing required fields:";

        FieldError error;
        for(Iterator var5 = result.getFieldErrors().iterator(); var5.hasNext(); message = message + " " + error.getField()) {
            error = (FieldError)var5.next();
        }

        return this.getErrorResponse(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({NullPointerException.class})
    @ResponseBody
    public ResponseEntity<ErrorMessage> nullException(NullPointerException ex) {
        logger.info("NullPointerException >>> " +  CommonUtils.loggerReplace(ex));
        return this.getErrorResponse(ex.toString(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IndexOutOfBoundsException.class})
    @ResponseBody
    public String indexOutOfBoundsException(IndexOutOfBoundsException ex) {
        logger.info("indexOutOfBoundsException >>> " +  CommonUtils.loggerReplace(ex.getMessage()));
        return ex.getMessage();
    }

    public ResponseEntity<ErrorMessage> getErrorResponse(String message, HttpStatus status) {
        return new ResponseEntity(new ErrorMessage(status.value(), message), status);
    }
}
