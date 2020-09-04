package org.paasta.container.platform.common.api.exception;

/**
 * CpRuntimeException 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.08.28
 */
public class CpRuntimeException extends BaseBizException {
	private static final long serialVersionUID = -1288712633779609678L;

	public CpRuntimeException(int errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public CpRuntimeException(String errorMessage) {
		super(errorMessage);
	}
}
