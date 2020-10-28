package org.paasta.container.platform.common.api.clusterResource.limitRanges;

import org.paasta.container.platform.common.api.common.CommonService;
import org.paasta.container.platform.common.api.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * LimitRanges Default Service 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.10.26
 **/
@Service
public class LimitRangesDefaultService {
    private final CommonService commonService;
    private final LimitRangesDefaultRepository limitRangesDefaultRepository;

    /**
     * Instantiates a new ResourceQuotasDefault service
     * @param commonService the common service
     * @param limitRangesDefaultRepository the limit ranges default repository
     */
    @Autowired
    public LimitRangesDefaultService(CommonService commonService, LimitRangesDefaultRepository limitRangesDefaultRepository) {
        this.commonService = commonService;
        this.limitRangesDefaultRepository = limitRangesDefaultRepository;
    }


    /**
     * ResourceQuotasDefault 목록 조회
     *
     * @return the ResourceQuotasDefaultList
     */
    public LimitRangesDefaultList getLrDefaultList() {
        List<LimitRangesDefault> lrDefaultList = limitRangesDefaultRepository.findAll();
        LimitRangesDefaultList finalLrList= new LimitRangesDefaultList();
        finalLrList.setItems(lrDefaultList);

        return (LimitRangesDefaultList) commonService.setResultModel(finalLrList, Constants.RESULT_STATUS_SUCCESS);
    }
}
