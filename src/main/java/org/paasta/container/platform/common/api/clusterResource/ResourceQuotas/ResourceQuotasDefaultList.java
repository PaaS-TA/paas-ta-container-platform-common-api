package org.paasta.container.platform.common.api.clusterResource.ResourceQuotas;

import lombok.Data;

import javax.persistence.Column;
import java.util.List;

/**
 * ResourceQuotas Default List Model 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.10.26
 **/
@Data
public class ResourceQuotasDefaultList {
    private String resultCode;
    private String resultMessage;

    @Column(name = "items")
    private List<ResourceQuotasDefault> items;
}
