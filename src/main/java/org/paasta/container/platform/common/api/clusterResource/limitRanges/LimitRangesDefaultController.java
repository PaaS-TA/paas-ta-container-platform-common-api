package org.paasta.container.platform.common.api.clusterResource.limitRanges;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * LimitRanges Default Controller 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.10.26
 **/
@Api(value = "LimitRangesDefaultController v1")
@RestController
@RequestMapping(value = "/limitRanges")
public class LimitRangesDefaultController {
    private final LimitRangesDefaultService limitRangesDefaultService;

    /**
     * Instantiates a LimitRangesDefault Controller
     *
     * @param limitRangesDefaultService the limitRangesDefault Service
     */
    @Autowired
    public LimitRangesDefaultController(LimitRangesDefaultService limitRangesDefaultService) {
        this.limitRangesDefaultService = limitRangesDefaultService;
    }


    /**
     * LimitRangesDefault 목록 조회(Get LimitRangesDefault list)
     *
     * @return the limitRangesDefault list
     */
    @ApiOperation(value="LimitRangesDefault 목록 조회(Get LimitRangesDefault list)", nickname="getLrDefaultList")
    @GetMapping
    public LimitRangesDefaultList getLrDefaultList() {
        return limitRangesDefaultService.getLrDefaultList();
    }
}
