package org.paasta.container.platform.common.api.users;

import lombok.Data;

/**
 * Users Admin MetaData Model 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2021.06.25
 */
@Data
public class UsersAdminMetaData {


    private String cpNamespace;
    private String userType;
    private String roleSetCode;
    private String saSecret;

    public UsersAdminMetaData(String cpNamespace, String userType, String roleSetCode) {
        this.cpNamespace = cpNamespace;
        this.userType = userType;
        this.roleSetCode = roleSetCode;
    }

    public UsersAdminMetaData(String cpNamespace, String userType, String roleSetCode, String secrets) {
        this.cpNamespace = cpNamespace;
        this.userType = userType;
        this.roleSetCode = roleSetCode;
        this.saSecret = secrets;
    }
}
