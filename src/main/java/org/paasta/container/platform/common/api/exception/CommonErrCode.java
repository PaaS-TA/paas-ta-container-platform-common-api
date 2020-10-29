package org.paasta.container.platform.common.api.exception;

/**
 * Common Err Code 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.08.28
 */
public enum CommonErrCode {
    NOT_FOUND(404, " 의 값이 존재하지 않습니다."),
    BAD_REQUEST(400, " 은(는) 잘못된 요청입니다."),
    MANDATORY(1000, " 은(는) 필수 값입니다."),

    // Example
    ERR_0001(10002, "%1이(가) 존재하지 않습니다."),
    ERR_0002(10003, "%1이(가) 불일치합니다.")
    ;

    private int errCode;
    private String msg;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getMsg(String arg) {
        return (arg + msg);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    CommonErrCode(int errCode, String msg) {
        this.errCode = errCode;
        this.msg = msg;
    }
}
