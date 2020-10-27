package org.paasta.container.platform.common.api.clusterResource.ResourceQuotas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

/**
 * ResourceQuotasDefault Model 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.10.26
 **/
@Entity
@Table(name = "cp_resource_quotas")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ResourceQuotasDefault {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "request_cpu")
    private String requestCpu;

    @Column(name = "request_memory")
    private String requestMemory;

    @Column(name = "limit_cpu")
    private String limitCpu;

    @Column(name = "limit_memory")
    private String limitMemory;

    @Column(name = "status")
    private String status;
}
