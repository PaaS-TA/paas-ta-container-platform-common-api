package org.paasta.container.platform.common.api.clusterResource.limitRanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.paasta.container.platform.common.api.common.Constants;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * LimitRanges Default Model 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.10.27
 **/
@Entity
@Table(name = "cp_limit_ranges")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LimitRangesDefault {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "resource")
    private String resource;

    @Column(name = "min")
    private String min;

    @Column(name = "max")
    private String max;

    @Column(name = "default_request")
    private String defaultRequest;

    @Column(name = "default_limit")
    private String defaultLimit;

    @Column(name = "created", nullable = false, updatable = false)
    private String creationTimestamp;

    @PrePersist
    void preInsert() {
        if (this.creationTimestamp == null) {
            this.creationTimestamp = LocalDateTime.now(ZoneId.of(Constants.STRING_TIME_ZONE_ID)).format(DateTimeFormatter.ofPattern(Constants.STRING_DATE_TYPE));
        }
    }
}