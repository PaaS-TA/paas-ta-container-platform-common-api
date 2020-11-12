package org.paasta.container.platform.common.api.clusters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Clusters Repository 인터페이스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.11.04
 **/
@Repository
@Transactional
public interface ClustersRepository extends JpaRepository<Clusters, Long> {
    List<Clusters> findAllByClusterName(String clusterName);
}
