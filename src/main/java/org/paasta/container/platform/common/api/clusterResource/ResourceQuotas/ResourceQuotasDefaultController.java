package org.paasta.container.platform.common.api.clusterResource.ResourceQuotas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ResourceQuotas Default Controller 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.10.26
 **/
@RestController
@RequestMapping(value = "/resourceQuotas")
public class ResourceQuotasDefaultController {
    private final ResourceQuotasDefaultService resourceQuotasDefaultService;

    /**
     * Instantiates a ResourceQuotasDefault Controller
     *
     * @param resourceQuotasDefaultService the resourceQuotasDefault Service
     */
    @Autowired
    public ResourceQuotasDefaultController(ResourceQuotasDefaultService resourceQuotasDefaultService) {
        this.resourceQuotasDefaultService = resourceQuotasDefaultService;
    }


    /**
     * ResourceQuotasDefault 목록 조회(Get ResourceQuotasDefault list)
     *
     * @return the ResourceQuotasDefault list
     */
    @GetMapping
    public ResourceQuotasDefaultList getRqDefaultList() {
        return resourceQuotasDefaultService.getRqDefaultList();
    }
}
