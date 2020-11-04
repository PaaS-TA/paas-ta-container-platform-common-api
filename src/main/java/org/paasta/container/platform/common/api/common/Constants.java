package org.paasta.container.platform.common.api.common;

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

    // API URI
    public static final String URI_API_ADMIN_TOKEN = "/adminToken";
    public static final String URI_API_ADMIN_TOKEN_DETAIL = "/adminToken/{tokenName:.+}";

    public static final String DEFAULT_NAMESPACE_NAME = "temp-namespace";

    public static final String AUTH_CLUSTER_ADMIN = "CLUSTER_ADMIN";
    public static final String AUTH_NAMESPACE_ADMIN = "NAMESPACE_ADMIN";
    public static final String AUTH_USER = "USER";

    private Constants() {
        throw new IllegalStateException();
    }
}
