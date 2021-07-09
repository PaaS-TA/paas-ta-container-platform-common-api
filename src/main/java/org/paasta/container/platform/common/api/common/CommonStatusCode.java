package org.paasta.container.platform.common.api.common;

/**
 * Common Status Code 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2021.06.22
 **/
public enum CommonStatusCode {
    OK(200, "정상적으로 처리 되었습니다."),
    BAD_REQUEST(400, "잘못된 요청을 처리할 수 없습니다."),
    UNAUTHORIZED(401, "인증 오류입니다."),
    FORBIDDEN(403, "페이지 접근 허용이 거부 되었습니다."),
    NOT_FOUND(404, "찾을 수 없습니다."),
    CONFLICT(409, "요청을 수행하는 중에 충돌이 발생하였습니다."),
    UNPROCESSABLE_ENTITY(422, "문법 오류로 인하여 요청을 처리할 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "요청 사항을 수행 할 수 없습니다."),
    SERVICE_UNAVAILABLE(503, "서버가 요청을 처리할 준비가 되지 않았습니다."),
    MANDATORY(1000, "Required value."),
    INVALID_FORMAT(1001, "Invalid YAML Format.");

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return (msg);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    CommonStatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
