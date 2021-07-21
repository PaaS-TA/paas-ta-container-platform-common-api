package org.paasta.container.platform.broker.serviceInstance;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * ServiceInstance Repository 인터페이스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2021.07.20
 */
@Repository
public interface ServiceInstanceRepository extends JpaRepository<ServiceInstance, Long>, JpaSpecificationExecutor<ServiceInstance> {

   List<ServiceInstance> findAllByServiceInstanceId(String serviceInstanceId);
}
