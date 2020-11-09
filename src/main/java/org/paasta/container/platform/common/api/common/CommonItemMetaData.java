package org.paasta.container.platform.common.api.common;

import lombok.Data;

/**
 * Common Item Meta Data Model 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.11.06
 */
@Data
public class CommonItemMetaData {

    private Integer allItemCount;
    private Integer remainingItemCount;

}
