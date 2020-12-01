package org.paasta.container.platform.common.api.privateRegistry;

import org.paasta.container.platform.common.api.clusters.ClustersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PrivateRegistry Controller 클래스
 *
 * @author minsu
 * @version 1.0
 * @since 2020.12.01
 **/
@RestController
@RequestMapping(value = "/privateregistry")
public class PrivateRegistryController {
    private final PrivateRegistryService privateRegistryService;

    /**
     * Instantiates a new Clusters controller
     *
     * @param PrivateRegistryService the private registry service
     */
    @Autowired
    public PrivateRegistryController(PrivateRegistryService privateRegistryService) {
        this.privateRegistryService = privateRegistryService;
    }

}
