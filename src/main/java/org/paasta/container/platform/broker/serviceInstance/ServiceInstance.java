package org.paasta.container.platform.broker.serviceInstance;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ServiceInstance Model 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2021.07.20
 */
@Entity
@Table(name = "service_instance")
@Data
public class ServiceInstance {

    @Id
    @Column(name = "service_instance_id")
    private String serviceInstanceId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "caas_namespace")
    private String namespace;

    @Column(name = "organization_guid")
    private String organizationGuid;
}

