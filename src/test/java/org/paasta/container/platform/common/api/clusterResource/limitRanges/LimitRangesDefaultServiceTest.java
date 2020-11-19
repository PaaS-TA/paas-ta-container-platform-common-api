package org.paasta.container.platform.common.api.clusterResource.limitRanges;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.paasta.container.platform.common.api.common.CommonService;
import org.paasta.container.platform.common.api.common.Constants;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * LimitRanges Default Service Test 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.11.17
 **/
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.yml")
public class LimitRangesDefaultServiceTest {
    private static List<LimitRangesDefault> lrDefaultList = null;
    private static LimitRangesDefault limitRangesDefault = null;
    private static LimitRangesDefaultList finalLrDefaultList = null;

    @Mock
    CommonService commonService;

    @Mock
    LimitRangesDefaultRepository limitRangesDefaultRepository;

    @InjectMocks
    LimitRangesDefaultService limitRangesDefaultService;

    @Before
    public void setUp() {
        lrDefaultList = new ArrayList<>();
        limitRangesDefault = new LimitRangesDefault();
        limitRangesDefault.setId(1);
        limitRangesDefault.setName("paasta-container-platform-low-limit-range");
        limitRangesDefault.setType("Container");
        limitRangesDefault.setResource("memory");
        limitRangesDefault.setDefaultLimit("500Mi");
        limitRangesDefault.setDefaultRequest(null);
        limitRangesDefault.setMax(null);
        limitRangesDefault.setMin(null);

        lrDefaultList.add(limitRangesDefault);

        finalLrDefaultList = new LimitRangesDefaultList();
        finalLrDefaultList.setItems(lrDefaultList);
        finalLrDefaultList.setResultCode(Constants.RESULT_STATUS_SUCCESS);
    }


    @Test
    public void getLrDefaultList() {
        when(limitRangesDefaultRepository.findAll()).thenReturn(lrDefaultList);
        LimitRangesDefaultList finalLrList= new LimitRangesDefaultList();
        finalLrList.setItems(lrDefaultList);

        when(commonService.setResultModel(finalLrList, Constants.RESULT_STATUS_SUCCESS)).thenReturn(finalLrDefaultList);

        LimitRangesDefaultList limitRangesDefaultList = limitRangesDefaultService.getLrDefaultList();
        assertEquals(Constants.RESULT_STATUS_SUCCESS, limitRangesDefaultList.getResultCode());
    }
}
