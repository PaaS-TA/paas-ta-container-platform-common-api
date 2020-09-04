package org.paasta.container.platform.common.api.adminToken;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * AdminToken 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.08.31
 */
@Entity
@Table(name = "admin_token")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AdminToken {

    @Id
    @Column(name = "token_name")
    private String tokenName;

    @Column(name = "token_value")
    @NotNull(message = "TOKEN VALUE cannot be null")
    @NotEmpty(message = "TOKEN VALUE is mandatory")
    private String tokenValue;

    @Transient
    private String resultCode;

    @Transient
    private int statusCode;

    @Transient
    private String resultMessage;

}
