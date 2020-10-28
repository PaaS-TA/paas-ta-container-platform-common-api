package org.paasta.container.platform.common.api.clusterResource.limitRanges;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * ResourceQuotasDefault Repository 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.10.26
 **/
@Repository
@Transactional
public interface LimitRangesDefaultRepository extends JpaRepository<LimitRangesDefault, Long> {

}
