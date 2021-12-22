package org.paasta.container.platform.broker.serviceInstance;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.paasta.container.platform.common.api.common.Constants;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.yml")
public class ServiceInstanceServiceTest {

    private static final String SERVICE_INSTANCE_ID ="9f102f4c-05bc-4bc7-8c8c-adfcfokbcb27e";
    private static final String SERVICE_ORG_ID ="1f102f4c-05bc-4bc7-8c8c-adfcfokbcb27e";
    private static final String NAMESPACE = "cp-namespace";
    private static final String USER_ID = "paasta";

    private static List<ServiceInstance> serviceInstanceList = null;
    private static  ServiceInstance serviceInstance = null;
    private static  ServiceInstanceList finalServiceInstanceList = null;

    @Mock
    ServiceInstanceRepository serviceInstanceRepository;

    @InjectMocks
    ServiceInstanceService serviceInstanceService;

    @Before
    public void setUp() {
        serviceInstance = new ServiceInstance();
        serviceInstance.setUserId(USER_ID);
        serviceInstance.setServiceInstanceId(SERVICE_INSTANCE_ID);
        serviceInstance.setOrganizationGuid(SERVICE_ORG_ID);
        serviceInstance.setNamespace(NAMESPACE);

        serviceInstanceList = new ArrayList<>();
        serviceInstanceList.add(serviceInstance);

        finalServiceInstanceList = new ServiceInstanceList(Constants.RESULT_STATUS_SUCCESS, serviceInstanceList);
    }


    @Test
    public void getServiceInstanceList() {
        when(serviceInstanceRepository.findAllByServiceInstanceId(SERVICE_INSTANCE_ID)).thenReturn(serviceInstanceList);
        ServiceInstanceList result = serviceInstanceService.getServiceInstanceList(SERVICE_INSTANCE_ID);
        assertNotNull(result);

    }
}