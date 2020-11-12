package org.paasta.container.platform.common.api.adminToken;

import io.swagger.annotations.*;
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
@Api(value = "AdminTokenController v1")
@RestController
public class AdminTokenController {

    private final AdminTokenService adminTokenService;
    private final CommonService commonService;

    /**
     * Instantiates a new AdminToken controller
     *
     * @param adminTokenService the adminToken service
     * @param commonService the common service
     */
    public AdminTokenController(AdminTokenService adminTokenService, CommonService commonService) {
        this.adminTokenService = adminTokenService;
        this.commonService = commonService;
    }


    /**
     * AdminToken 상세 조회(Get AdminToken detail)
     *
     * @param tokenName the tokenName
     * @return the adminToken detail
     */
    @ApiOperation(value="Admin token 상세조회", nickname="getTokenValue")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tokenName", value = "토큰 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.URI_API_ADMIN_TOKEN_DETAIL)
    AdminToken getTokenValue(@PathVariable("tokenName") String tokenName) {
        return adminTokenService.getTokenValue(tokenName);
    }


    /**
     * AdminToken 생성(Create AdminToken)
     *
     * @param adminToken the adminToken
     * @return return is succeeded
     */
    @ApiOperation(value="Admin token 생성", nickname="createAdminToken")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminToken", value = "admin 토큰", required = true, dataType = "AdminToken", paramType = "body")
    })
    @PostMapping(value = Constants.URI_API_ADMIN_TOKEN)
    AdminToken createAdminToken(@RequestBody AdminToken adminToken) {
        return adminTokenService.createAdminToken(adminToken);
    }

}
