package org.paasta.container.platform.broker.serviceInstance;

import org.paasta.container.platform.common.api.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * ServiceInstance Service 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2021.07.20
 */
@Service
public class ServiceInstanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceInstanceService.class);
    private final ServiceInstanceRepository serviceInstanceRepository;

    /**
     * Instantiates a new ServiceInstance service
     *
     * @param serviceInstanceRepository the ServiceInstance repository
     */
    @Autowired
    public ServiceInstanceService(ServiceInstanceRepository serviceInstanceRepository) {
        this.serviceInstanceRepository = serviceInstanceRepository;
    }


    /**
     * ServiceInstance 조회 (Get ServiceInstance)
     *
     * @return the serviceInstance
     */
    public  ServiceInstanceList getServiceInstanceList(String serviceInstanceId) {
        List<ServiceInstance> items = serviceInstanceRepository.findAllByServiceInstanceId(serviceInstanceId);
        ServiceInstanceList serviceInstanceList = new ServiceInstanceList(Constants.RESULT_STATUS_SUCCESS, items);

        return serviceInstanceList;
    }



}
