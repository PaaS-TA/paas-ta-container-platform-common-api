package org.paasta.container.platform.common.api.clusters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clusters Service 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.11.04
 **/
@Service
public class ClustersService {

    private final ClustersRepository clustersRepository;


    /**
     * Instantiates a new Clusters service
     *
     * @param clustersRepository the cluster repository
     */
    @Autowired
    public ClustersService(ClustersRepository clustersRepository) {
        this.clustersRepository = clustersRepository;
    }

    /**
     * Clusters 정보 저장(Create Clusters Info)
     *
     * @param clusters the clusters
     * @return the clusters
     */
    public Clusters createClusters(Clusters clusters) {
        return clustersRepository.save(clusters);
    }

    /**
     * Clusters 정보 조회(Get Clusters Info)
     *
     * @param clusterName the cluster name
     * @return the clusters
     */
    public Clusters getClusters(String clusterName) {
        return clustersRepository.findAllByClusterName(clusterName).get(0);
    }
}
