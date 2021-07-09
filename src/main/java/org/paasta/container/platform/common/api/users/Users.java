package org.paasta.container.platform.common.api.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.paasta.container.platform.common.api.common.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * User Model 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.09.22
 */
@Entity
@Table(name = "cp_users")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Users {

    @Transient
    private String resultCode;

    @Transient
    private String resultMessage;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id", nullable = false)
    @NotNull(message = "USER ID cannot be null")
    @NotEmpty(message = "USER ID is mandatory")
    private String userId;

    @Column(name = "password", nullable = false)
    @NotNull(message = "PASSWORD cannot be null")
    @NotEmpty(message = "PASSWORD is mandatory")
    private String password;

    @Column(name = "email", nullable = false)
    @NotNull(message = "EMAIL cannot be null")
    @NotEmpty(message = "EMAIL is mandatory")
    private String email;

    @Column(name = "cluster_name")
    private String clusterName;

    @Column(name = "cluster_api_url")
    private String clusterApiUrl;

    @Column(name = "cluster_token", length = 2000)
    private String clusterToken;

    @Column(name = "namespace", nullable = false)
    private String cpNamespace;

    @Column(name = "service_account_name", nullable = false)
    private String serviceAccountName;

    @Column(name = "service_account_secret", nullable = false)
    private String saSecret;

    @Column(name = "service_account_token", nullable = false, length = 2000)
    private String saToken;

    @Column(name = "is_active", nullable = false, columnDefinition = "varchar (1) default 'N'")
    private String isActive;

    @Column(name = "role_set_code", nullable = false)
    private String roleSetCode;

    @Column(name = "user_type", nullable = false)
    private String userType;

    @Column(name = "description")
    private String description;

    @Column(name = "created", nullable = false, updatable = false)
    private String created;

    @Column(name = "last_modified", nullable = false)
    private String lastModified;

    @Column(name = "user_auth_id", nullable = false)
    private String userAuthId;


    @Transient
    private String isNsAdmin;

    @PrePersist
    void preInsert() {
        if (this.created == null) {
            this.created = LocalDateTime.now(ZoneId.of(Constants.STRING_TIME_ZONE_ID)).format(DateTimeFormatter.ofPattern(Constants.STRING_DATE_TYPE));
        }

        if (this.lastModified == null) {
            this.lastModified = LocalDateTime.now(ZoneId.of(Constants.STRING_TIME_ZONE_ID)).format(DateTimeFormatter.ofPattern(Constants.STRING_DATE_TYPE));
        }

        if (this.isActive == null) {
            this.isActive= "N";
        }
    }


    public Users(){
    }

    public Users(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }


    public Users(Object id, Object userId, Object userAuthId, Object serviceAccountName, Object cpNamespace, Object userType, Object roleSetCode, Object created) {
        this.id =  Integer.parseInt(String.valueOf(id));
        this.userId = (String) userId;
        this.userAuthId = (String) userAuthId;
        this.serviceAccountName = (String) serviceAccountName;
        this.cpNamespace = (String) cpNamespace;
        this.userType = (String) userType;
        this.roleSetCode = (String) roleSetCode;
        this.created = (String) created;
    }


    public Users(Object id, Object userId, Object userAuthId, Object serviceAccountName, Object cpNamespace, Object userType, Object roleSetCode, Object saSecret,
                 Object clusterName, Object clusterApiUrl, Object clusterToken, Object created) {
        this.id =  Integer.parseInt(String.valueOf(id));
        this.userId = (String) userId;
        this.userAuthId = (String) userAuthId;
        this.serviceAccountName = (String) serviceAccountName;
        this.cpNamespace = (String) cpNamespace;
        this.userType = (String) userType;
        this.roleSetCode = (String) roleSetCode;
        this.saSecret = (String) saSecret;
        this.clusterName = (String) clusterName;
        this.clusterApiUrl = (String) clusterApiUrl;
        this.clusterToken = (String) clusterToken;
        this.created = (String) created;
    }


    public Users(Object userId, Object userAuthId, Object isNsAdmin) {
        this.userId = (String) userId;
        this.userAuthId = (String) userAuthId;
        this.isNsAdmin = (String) isNsAdmin;
    }


    public Users(Object id, Object userId, Object userAuthId, Object serviceAccountName, Object cpNamespace, Object roleSetCode, Object userType, Object created, Object isActive) {
        this.id =  Integer.parseInt(String.valueOf(id));
        this.userId = (String) userId;
        this.userAuthId = (String) userAuthId;
        this.serviceAccountName = (String) serviceAccountName;
        this.cpNamespace = (String) cpNamespace;
        this.roleSetCode = (String) roleSetCode;
        this.userType = (String) userType;
        this.created = (String) created;
        this.isActive = (String) isActive;
    }

    @PreUpdate
    void preUpdate() {
        if (this.lastModified != null) {
            this.lastModified = LocalDateTime.now(ZoneId.of(Constants.STRING_TIME_ZONE_ID)).format(DateTimeFormatter.ofPattern(Constants.STRING_DATE_TYPE));
        }
    }


    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", userAuthId='" + userAuthId + '\'' +
                ", userId='" + userId + '\'' +
                ", cpNamespace='" + cpNamespace + '\'' +
                ", roleSetCode='" + roleSetCode + '\'' +
                ", userType='" + userType + '\'' +
                '}'+ '\n' ;
    }
}
