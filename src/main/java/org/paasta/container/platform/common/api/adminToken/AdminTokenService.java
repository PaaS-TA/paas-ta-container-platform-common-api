package org.paasta.container.platform.common.api.adminToken;

import org.paasta.container.platform.common.api.common.CommonService;
import org.paasta.container.platform.common.api.common.Constants;
import org.paasta.container.platform.common.api.exception.CommonErrCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AdminToken Service 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.09.01
 */
@Service
public class AdminTokenService {

    private final CommonService commonService;
    private final AdminTokenRepository adminTokenRepository;

    /**
     * Instantiates AdminToken Service
     *
     * @param commonService        the common service
     * @param adminTokenRepository the adminToken repository
     */
    @Autowired
    public AdminTokenService(CommonService commonService, AdminTokenRepository adminTokenRepository) {
        this.commonService = commonService;
        this.adminTokenRepository = adminTokenRepository;
    }


    /**
     * AdminToken 상세 조회(Get AdminToken)
     *
     * @param tokenName the tokenName
     * @return the adminToken detail
     */
    AdminToken getTokenValue(String tokenName) {
        AdminToken token = adminTokenRepository.findByTokenName(tokenName);
        AdminToken nullObject = new AdminToken();

        if(token.getTokenValue() == null || "".equals(token.getTokenValue())) {
            nullObject.setTokenName(tokenName);
            nullObject.setResultMessage(CommonErrCode.NOT_FOUND.getMsg(tokenName));
            nullObject.setStatusCode(CommonErrCode.NOT_FOUND.getErrCode());
            return (AdminToken) commonService.setResultModel(nullObject, Constants.RESULT_STATUS_FAIL);
        }

        return (AdminToken) commonService.setResultModel(token, Constants.RESULT_STATUS_SUCCESS);
    }


    /**
     * AdminToken 정보 등록(Create AdminToken)
     *
     * @param adminToken the adminToken
     * @return return is succeeded
     */
    AdminToken createAdminToken(AdminToken adminToken) {
        String result = commonService.procValidator(adminToken);

        if (result.equals(Constants.RESULT_STATUS_SUCCESS)) {
            return adminTokenRepository.save(adminToken);
        } else {
            return (AdminToken) commonService.setResultModel(AdminToken.class, Constants.RESULT_STATUS_FAIL);
        }
    }

}
