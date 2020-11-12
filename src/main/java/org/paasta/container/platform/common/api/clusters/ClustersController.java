package org.paasta.container.platform.common.api.clusters;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Clusters Controller 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.11.04
 **/
@RestController
@RequestMapping(value = "/clusters")
public class ClustersController {

    private final ClustersService clustersService;

    /**
     * Instantiates a new Clusters controller
     *
     * @param clustersService the clusters service
     */
    @Autowired
    public ClustersController(ClustersService clustersService) {
        this.clustersService = clustersService;
    }


    /**
     * Clusters 정보 저장(Create Clusters Info)
     *
     * @param clusters the clusters
     * @return the clusters
     */
    @ApiOperation(value="Clusters 정보 저장(Create Clusters Info)", nickname="createClusters")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clusters", value = "클러스터 명", required = true, dataType = "string", paramType = "body")
    })
    @PostMapping
    public Clusters createClusters(@RequestBody Clusters clusters) {
        return clustersService.createClusters(clusters);
    }


    /**
     * Clusters 정보 조회(Get Clusters Info)
     *
     * @param clusterName the cluster name
     * @return the Clusters
     */
    @ApiOperation(value="Clusters 정보 조회(Get Clusters Info)", nickname="getClusters")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clusters", value = "클러스터 명", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping(value = "{clusterName:.+}")
    public Clusters getClusters(@PathVariable String clusterName) {
        return clustersService.getClusters(clusterName);
    }
}
