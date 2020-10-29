package org.paasta.container.platform.common.api.exception;

/**
 * Required Parameter Exception 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.08.27
 */
public class RequiredParameterException extends BaseBizException {
	private static final long serialVersionUID = -1288712633779609678L;

	public RequiredParameterException(String parameterName) {
		super(CommonErrCode.MANDATORY.getErrCode(), CommonErrCode.MANDATORY.getMsg(parameterName));
	}
}
