package org.paasta.container.platform.common.api.clusterResource.ResourceQuotas;

import org.paasta.container.platform.common.api.common.CommonService;
import org.paasta.container.platform.common.api.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ResourceQuotasDefault Service 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.10.26
 **/
@Service
public class ResourceQuotasDefaultService {
    private final CommonService commonService;
    private final ResourceQuotasDefaultRepository resourceQuotasDefaultRepository;

    /**
     * Instantiates a new ResourceQuotasDefault service
     *
     * @param commonService the common service
     * @param resourceQuotasDefaultRepository the resource quotas default repository
     */
    @Autowired
    public ResourceQuotasDefaultService(CommonService commonService, ResourceQuotasDefaultRepository resourceQuotasDefaultRepository) {
        this.commonService = commonService;
        this.resourceQuotasDefaultRepository = resourceQuotasDefaultRepository;
    }

    /**
     * ResourceQuotasDefault 목록 조회
     *
     * @return the ResourceQuotasDefaultList
     */
    public ResourceQuotasDefaultList getRqDefaultList() {
        List<ResourceQuotasDefault> rqDefaultList = resourceQuotasDefaultRepository.findAll();
        ResourceQuotasDefaultList finalRaList= new ResourceQuotasDefaultList();
        finalRaList.setItems(rqDefaultList);

        return (ResourceQuotasDefaultList) commonService.setResultModel(finalRaList, Constants.RESULT_STATUS_SUCCESS);
    }
}
