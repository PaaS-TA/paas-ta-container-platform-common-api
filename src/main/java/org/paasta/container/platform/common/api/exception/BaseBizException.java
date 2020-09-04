package org.paasta.container.platform.common.api.exception;

/**
 * BaseBizException 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.08.28
 */
public class BaseBizException extends RuntimeException {

    private static final long serialVersionUID = 1032826776466587212L;

    private int errorCode;
    private String errorMessage;

    public BaseBizException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public BaseBizException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    };

    public BaseBizException(int errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    };

    public BaseBizException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
    }

    public BaseBizException(Throwable cause) {
        super(cause);
    }

    public int getErrorCode(){
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
