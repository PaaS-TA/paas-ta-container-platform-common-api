package org.paasta.container.platform.common.api.adminToken;

import org.paasta.container.platform.common.api.common.CommonService;
import org.paasta.container.platform.common.api.common.Constants;
import org.springframework.web.bind.annotation.*;

/**
 * AdminToken Controller 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.08.31
 */
@RestController
public class AdminTokenController {

    private final AdminTokenService adminTokenService;
    private final CommonService commonService;

    /**
     * Instantiates a new Admin token controller
     *
     * @param adminTokenService the adminToken service
     * @param commonService the common service
     */
    public AdminTokenController(AdminTokenService adminTokenService, CommonService commonService) {
        this.adminTokenService = adminTokenService;
        this.commonService = commonService;
    }


    /**
     * Admin token 상세 정보를 조회한다.
     *
     * @param tokenName the tokenName
     * @return the AdminToken
     */
    @GetMapping(value = Constants.URI_API_ADMIN_TOKEN_DETAIL)
    AdminToken getTokenValue(@PathVariable("tokenName") String tokenName) {
        return adminTokenService.getTokenValue(tokenName);
    }


    /**
     * Admin token 정보를 등록한다.
     *
     * @param adminToken the AdminToken
     * @return the AdminToken
     */
    @PostMapping(value = Constants.URI_API_ADMIN_TOKEN)
    AdminToken createAdminToken(@RequestBody AdminToken adminToken) {
        return adminTokenService.createAdminToken(adminToken);
    }

}
