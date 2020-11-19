package org.paasta.container.platform.common.api.clusterResource.resourceQuotas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.paasta.container.platform.common.api.clusterResource.ResourceQuotas.ResourceQuotasDefault;
import org.paasta.container.platform.common.api.clusterResource.ResourceQuotas.ResourceQuotasDefaultList;
import org.paasta.container.platform.common.api.clusterResource.ResourceQuotas.ResourceQuotasDefaultRepository;
import org.paasta.container.platform.common.api.clusterResource.ResourceQuotas.ResourceQuotasDefaultService;
import org.paasta.container.platform.common.api.common.CommonService;
import org.paasta.container.platform.common.api.common.Constants;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * ResourceQuotas Default Service Test 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.11.17
 **/
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.yml")
public class ResourceQuotasDefaultServiceTest {
    private static List<ResourceQuotasDefault> resourceQuotasDefaultList = null;
    private static ResourceQuotasDefault resourceQuotasDefault = null;
    private static ResourceQuotasDefaultList finalRqDefaultList = null;

    @Mock
    CommonService commonService;

    @Mock
    ResourceQuotasDefaultRepository resourceQuotasDefaultRepository;

    @InjectMocks
    ResourceQuotasDefaultService resourceQuotasDefaultService;

    @Before
    public void setUp() {
        resourceQuotasDefaultList = new ArrayList<>();
        resourceQuotasDefault = new ResourceQuotasDefault();
        resourceQuotasDefault.setId(1);
        resourceQuotasDefault.setName("paasta-container-platform-low-limit-range");
        resourceQuotasDefault.setLimitMemory("2Gi");
        resourceQuotasDefault.setLimitCpu("2");
        resourceQuotasDefault.setRequestMemory("0");
        resourceQuotasDefault.setRequestCpu("0");
        resourceQuotasDefault.setStatus("{\"hard\":{\"limits.cpu\":\"2\",\"limits.memory\":\"2Gi\"},\"used\":{\"limits.cpu\":\"200m\",\"limits.memory\":\"1800Mi\"}}");

        resourceQuotasDefaultList.add(resourceQuotasDefault);

        finalRqDefaultList = new ResourceQuotasDefaultList();
        finalRqDefaultList.setItems(resourceQuotasDefaultList);
        finalRqDefaultList.setResultCode(Constants.RESULT_STATUS_SUCCESS);
    }

    @Test
    public void getRqDefaultList() {
        when(resourceQuotasDefaultRepository.findAll()).thenReturn(resourceQuotasDefaultList);
        ResourceQuotasDefaultList finalRaList= new ResourceQuotasDefaultList();
        finalRaList.setItems(resourceQuotasDefaultList);

        when(commonService.setResultModel(finalRaList, Constants.RESULT_STATUS_SUCCESS)).thenReturn(finalRqDefaultList);

        ResourceQuotasDefaultList resourceQuotasDefaultList = resourceQuotasDefaultService.getRqDefaultList();
        assertEquals(Constants.RESULT_STATUS_SUCCESS, resourceQuotasDefaultList.getResultCode());
    }

}
