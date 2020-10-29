package org.paasta.container.platform.common.api.clusterResource.ResourceQuotas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * ResourceQuotas Default Repository 인터페이스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.10.26
 **/
@Repository
@Transactional
public interface ResourceQuotasDefaultRepository extends JpaRepository<ResourceQuotasDefault, Long> {

}
