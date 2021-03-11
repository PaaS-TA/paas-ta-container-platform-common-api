package org.paasta.container.platform.common.api.common;

/**
 * Common Utils 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2021.03.10
 */
public class CommonUtils {

    /**
     * LOGGER 개행문자 제거 (Object)
     *
     * @param obj
     * @return String the replaced string
     */
    public static String loggerReplace(Object obj) {
        return obj.toString().replaceAll("[\r\n]","");
    }

    /**
     * LOGGER 개행문자 제거 (String)
     *
     * @param str
     * @return String the replaced string
     */
    public static String loggerReplace(String str) {
        return str.replaceAll("[\r\n]","");
    }

}
