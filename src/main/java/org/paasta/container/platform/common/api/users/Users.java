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

    @Column(name = "email")
    @NotNull(message = "EMAIL cannot be null")
    @NotEmpty(message = "EMAIL is mandatory")
    private String email;

    @Column(name = "cluster_name", nullable = false)
    private String clusterName;

    @Column(name = "cluster_api_url", nullable = false)
    private String clusterApiUrl;

    @Column(name = "cluster_token", nullable = false)
    private String clusterToken;

    @Column(name = "cp_namespace", nullable = false)
    private String cpNamespace;

    @Column(name = "service_account_name")
    private String serviceAccountName;

    @Column(name = "sa_secret")
    private String saSecret;

    @Column(name = "sa_token")
    private String saToken;

    @Column(name = "isactive")
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


    @PrePersist
    void preInsert() {
        if (this.created == null) {
            this.created = LocalDateTime.now(ZoneId.of(Constants.STRING_TIME_ZONE_ID)).format(DateTimeFormatter.ofPattern(Constants.STRING_DATE_TYPE));
        }

        if (this.lastModified == null) {
            this.lastModified = LocalDateTime.now(ZoneId.of(Constants.STRING_TIME_ZONE_ID)).format(DateTimeFormatter.ofPattern(Constants.STRING_DATE_TYPE));
        }
    }

    @PreUpdate
    void preUpdate() {
        if (this.lastModified != null) {
            this.lastModified = LocalDateTime.now(ZoneId.of(Constants.STRING_TIME_ZONE_ID)).format(DateTimeFormatter.ofPattern(Constants.STRING_DATE_TYPE));
        }
    }


}
