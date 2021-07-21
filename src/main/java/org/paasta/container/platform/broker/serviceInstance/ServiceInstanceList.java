package org.paasta.container.platform.broker.serviceInstance;

import lombok.Data;
import java.util.List;

/**
 * ServiceInstance List Model 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2021.07.21
 */
@Data
public class ServiceInstanceList {
    private String resultCode;
    private String resultMessage;
    private List<ServiceInstance> items;

    public ServiceInstanceList(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public ServiceInstanceList(String resultCode, List<ServiceInstance> items) {
        this.resultCode = resultCode;
        this.items = items;
    }
}
