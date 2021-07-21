package org.paasta.container.platform.broker.serviceInstance;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ServiceInstance Controller 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2021.07.20
 **/
@RestController
@RequestMapping(value = "/serviceInstance")
public class ServiceInstanceController {

    private final ServiceInstanceService serviceInstanceService;

    /**
     * Instantiates a new ServiceInstance controller
     *
     * @param serviceInstanceService the serviceInstanceService
     */
    @Autowired
    public ServiceInstanceController(ServiceInstanceService serviceInstanceService) {
        this.serviceInstanceService = serviceInstanceService;
    }


    /**
     * ServiceInstance 정보 조회(Get ServiceInstance Info)
     *
     * @param serviceInstanceId the serviceInstance id
     * @return serviceInstance
     */
    @ApiOperation(value="ServiceInstance 정보 조회(Get ServiceInstance Info)", nickname="getServiceInstance")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceInstanceId", value = "serviceInstance 아이디", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = "{serviceInstanceId:.+}")
    public ServiceInstanceList getServiceInstance(@PathVariable String serviceInstanceId) {
        return serviceInstanceService.getServiceInstanceList(serviceInstanceId);
    }
}
