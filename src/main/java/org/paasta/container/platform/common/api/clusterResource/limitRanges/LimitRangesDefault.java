package org.paasta.container.platform.common.api.clusterResource.limitRanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

/**
 * LimitRangesDefault Model 클래스
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
}