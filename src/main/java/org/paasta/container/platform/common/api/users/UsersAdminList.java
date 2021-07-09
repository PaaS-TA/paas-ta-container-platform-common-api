package org.paasta.container.platform.common.api.users;

import lombok.Data;
import java.util.List;

/**
 * Users Admin List Model 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2021.06.25
 */
@Data
public class UsersAdminList {
    private String resultCode;
    private String resultMessage;

    public UsersAdminList() {
    }

    public UsersAdminList(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    private List<UsersAdmin> items;
}
