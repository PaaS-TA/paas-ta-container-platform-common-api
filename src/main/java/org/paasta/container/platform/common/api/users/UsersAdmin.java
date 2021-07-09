package org.paasta.container.platform.common.api.users;


import lombok.Data;

import java.util.List;

/**
 * Users Admin Model 클래스
 *
 * @author kjh
 * @version 1.0
 * @since 2021.06.25
 */


@Data
public class UsersAdmin {


    private String resultCode;
    private String resultMessage;

    private String userId;
    private String userAuthId;
    private String serviceAccountName;
    private String created;
    private String isNsAdmin;

    // Cluster Info
    public String clusterName;
    public String clusterApiUrl;
    public String clusterToken;



    private List<UsersAdminMetaData> items;

    public UsersAdmin(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public UsersAdmin(String userId, String userAuthId, String serviceAccountName, String created) {
        this.userId = userId;
        this.userAuthId = userAuthId;
        this.serviceAccountName = serviceAccountName;
        this.created = created;
    }

    public UsersAdmin(String resultMessage, String userId, String userAuthId, String serviceAccountName, String created, List<UsersAdminMetaData> items) {
        this.resultMessage = resultMessage;
        this.userId = userId;
        this.userAuthId = userAuthId;
        this.serviceAccountName = serviceAccountName;
        this.created = created;
        this.items = items;
    }

    public UsersAdmin(String resultCode, String resultMessage, String userId, String serviceAccountName, String created) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.userId = userId;
        this.serviceAccountName = serviceAccountName;
        this.created = created;
    }
}


