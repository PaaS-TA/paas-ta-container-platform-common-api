package org.paasta.container.platform.common.api.privateRegistry;

import org.paasta.container.platform.common.api.common.CommonService;
import org.paasta.container.platform.common.api.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Private Registry Service 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.12.01
 */
@Service
public class PrivateRegistryService {

    private final CommonService commonService;
    private final PrivateRegistryRepository privateRegistryRepository;

    /**
     * Instantiates AdminToken Service
     *
     * @param commonService             the common service
     * @param privateRegistryRepository the private Registry Repository
     */
    @Autowired
    public PrivateRegistryService(CommonService commonService, PrivateRegistryRepository privateRegistryRepository) {
        this.commonService = commonService;
        this.privateRegistryRepository = privateRegistryRepository;
    }


    /**
     * PrivateRegistry 상세 조회(Get PrivateRegistry)
     *
     * @param repositoryName the repositoryName
     * @return the privateRegistry detail
     */
    PrivateRegistry getPrivateRegistry(String repositoryName) {

        PrivateRegistry privateRegistry = new PrivateRegistry();
        try {
            privateRegistry = privateRegistryRepository.findByRepositoryName(repositoryName);
        } catch (NullPointerException e) {
            privateRegistry.setResultMessage(e.getMessage());
            return (PrivateRegistry) commonService.setResultModel(privateRegistry, Constants.RESULT_STATUS_FAIL);
        }
        return (PrivateRegistry) commonService.setResultModel(privateRegistry, Constants.RESULT_STATUS_SUCCESS);
    }


    /**
     * PrivateRegistry 정보 등록(Create PrivateRegistry)
     *
     * @param privateRegistry the privateRegistry
     * @return return is succeeded
     */
    PrivateRegistry createPrivateRegistry(PrivateRegistry privateRegistry) {

        PrivateRegistry createdPrivateRegistry = new PrivateRegistry();

        try {
            createdPrivateRegistry = privateRegistryRepository.save(privateRegistry);
        } catch (Exception e) {
            createdPrivateRegistry.setResultMessage(e.getMessage());
            return (PrivateRegistry) commonService.setResultModel(createdPrivateRegistry, Constants.RESULT_STATUS_FAIL);
        }

        return (PrivateRegistry) commonService.setResultModel(createdPrivateRegistry, Constants.RESULT_STATUS_SUCCESS);

    }
}
