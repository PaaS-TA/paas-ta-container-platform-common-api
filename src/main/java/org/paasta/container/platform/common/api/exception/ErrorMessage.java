package org.paasta.container.platform.common.api.exception;

import lombok.Data;

/**
 * ErrorMessage 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.08.28
 */
@Data
public class ErrorMessage {
    private String resultCode;
    private String resultMessage;
    private int detailCode;
    private String detailMessage;

    public ErrorMessage(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public ErrorMessage(int detailCode, String detailMessage) {
        this.detailCode = detailCode;
        this.detailMessage = detailMessage;
    }

    public ErrorMessage(String resultCode, String resultMessage, int detailCode, String detailMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.detailCode = detailCode;
        this.detailMessage = detailMessage;
    }
}
