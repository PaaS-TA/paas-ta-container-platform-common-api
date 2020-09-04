package org.paasta.container.platform.common.api.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Property Service 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.08.25
 */
@Service
@Data
public class PropertyService {

    @Value("${cpApi.url}")
    private String cpApiUrl;
}
