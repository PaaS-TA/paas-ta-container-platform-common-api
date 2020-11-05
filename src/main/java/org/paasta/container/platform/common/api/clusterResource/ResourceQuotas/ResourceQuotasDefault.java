package org.paasta.container.platform.common.api.clusterResource.ResourceQuotas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.paasta.container.platform.common.api.common.Constants;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * ResourceQuotas Default Model 클래스
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

    @Column(name = "created", nullable = false, updatable = false)
    private String creationTimestamp;

    @PrePersist
    void preInsert() {
        if (this.creationTimestamp == null) {
            this.creationTimestamp = LocalDateTime.now(ZoneId.of(Constants.STRING_TIME_ZONE_ID)).format(DateTimeFormatter.ofPattern(Constants.STRING_DATE_TYPE));
        }
    }
}
