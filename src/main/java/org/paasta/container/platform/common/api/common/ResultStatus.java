package org.paasta.container.platform.common.api.common;

import lombok.Data;

/**
 * ResultStatus Model 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.08.28
 **/
@Data
public class ResultStatus {
    private String resultCode;
    private String resultMessage;
    private int httpStatusCode;
    private String detailMessage;
    private String nextActionUrl;

    public ResultStatus() {
    }

    /**
     * Instantiates a new ResultStatus
     *
     * @param resultCode the result code
     * @param resultMessage the result message
     * @param httpStatusCode the http status code
     * @param detailMessage the detail message
     */
    public ResultStatus(String resultCode, String resultMessage, int httpStatusCode, String detailMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.httpStatusCode = httpStatusCode;
        this.detailMessage = detailMessage;
    }

    /**
     * Instantiates a new ResultStatus
     *
     * @param resultCode the result code
     * @param resultMessage the result message
     * @param httpStatusCode the http status code
     * @param detailMessage the detail message
     * @param nextActionUrl the next action url
     */
    public ResultStatus(String resultCode, String resultMessage, int httpStatusCode, String detailMessage, String nextActionUrl) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.httpStatusCode = httpStatusCode;
        this.detailMessage = detailMessage;
        this.nextActionUrl = nextActionUrl;
    }

}
