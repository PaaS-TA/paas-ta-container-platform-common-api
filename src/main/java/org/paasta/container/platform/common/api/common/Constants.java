package org.paasta.container.platform.common.api.common;

import org.paasta.container.platform.common.api.exception.CommonErrCode;
import org.paasta.container.platform.common.api.exception.ErrorMessage;

/**
 * Constants 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.08.25
 */
public class Constants {

    public static final String RESULT_STATUS_SUCCESS = "SUCCESS";
    public static final String RESULT_STATUS_FAIL = "FAIL";
    public static final String STRING_DATE_TYPE = "yyyy-MM-dd HH:mm:ss";
    public static final String STRING_TIME_ZONE_ID = "Asia/Seoul";
    public static final String TARGET_CP_API = "cpApi";

    public static final String IS_ADMIN_TRUE = "true";

    // API URI
    public static final String URI_API_ADMIN_TOKEN = "/adminToken";
    public static final String URI_API_ADMIN_TOKEN_DETAIL = "/adminToken/{tokenName:.+}";


    // authority
    public static final String AUTH_CLUSTER_ADMIN = "CLUSTER_ADMIN";
    public static final String AUTH_NAMESPACE_ADMIN = "NAMESPACE_ADMIN";
    public static final String AUTH_USER = "USER";

    // sort
    public static final String DESC = "desc";
    public static final String ASC ="asc";
    public static final String USERS = "users";


    public static final String CP_USER_ID_COLUM = "userId";
    public static final String CP_USER_CREATED_COLUM ="created";

    public static final String CHECK_Y = "Y";
    public static final String CHECK_N = "N";

    public static final ErrorMessage NOT_FOUND_RESULT_STATUS =
            new ErrorMessage(Constants.RESULT_STATUS_FAIL, CommonErrCode.NOT_FOUND.name(), CommonErrCode.NOT_FOUND.getErrCode(),CommonErrCode.NOT_FOUND.name());

    private Constants() {
        throw new IllegalStateException();
    }
}
