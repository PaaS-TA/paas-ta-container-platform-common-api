package org.paasta.container.platform.common.api.privateRegistry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * Private Registry Controller 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.12.01
 */
@Api(value = "PrivateRegistryController v1")
@RestController
@RequestMapping("/clusters/{cluster:.+}/privateRegistry")
public class PrivateRegistryController {

    private final PrivateRegistryService privateRegistryService;

    /**
     * Instantiates a new PrivateRegistry controller
     *
     * @param privateRegistryService the privateRegistry Service
     */
    public PrivateRegistryController(PrivateRegistryService privateRegistryService) {
        this.privateRegistryService = privateRegistryService;
    }


    /**
     * PrivateRegistry 상세 조회 (Get PrivateRegistry detail)
     *
     * @param repositoryName the repositoryName
     * @return the private registry detail
     */
    @ApiOperation(value = "PrivateRegistry 상세 조회 (Get PrivateRegistry detail)", nickname = "getPrivateRegistryDetails")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "repositoryName", value = "레파지토리 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = "/{repositoryName:.+}")
    PrivateRegistry getPrivateRegistry(@PathVariable(value = "repositoryName") String repositoryName) {
        return privateRegistryService.getPrivateRegistry(repositoryName);
    }


    /**
     * PrivateRegistry 생성(Create PrivateRegistry)
     *
     * @param privateRegistry the privateRegistry
     * @return return is succeeded
     */
    @ApiOperation(value="PrivateRegistry 생성", nickname="createPrivateRegistry")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "privateRegistry", value = "privateRegistry 정보", required = true, dataType = "PrivateRegistry", paramType = "body")
    })
    @PostMapping
    PrivateRegistry createPrivateRegistry(@RequestBody PrivateRegistry privateRegistry) {
        return privateRegistryService.createPrivateRegistry(privateRegistry);
    }

}
