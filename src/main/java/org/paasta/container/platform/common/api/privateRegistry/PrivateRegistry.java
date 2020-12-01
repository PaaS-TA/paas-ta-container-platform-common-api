package org.paasta.container.platform.common.api.privateRegistry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * PrivateRegistry Model 클래스
 *
 * @author minsu
 * @version 1.0
 * @since 2020.12.01
 **/
@Entity
@Table(name = "private_repository")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PrivateRegistry {
    @Id
    @Column(name = "repository_url")
    @NotNull(message = "REPOSITORY URL cannot be null")
    @NotEmpty(message = "REPOSITORY URL mandatory")
    private String repository_url;

    @Column(name = "repository_name")
    @NotNull(message = "REPOSITORY NAME cannot be null")
    @NotEmpty(message = "REPOSITORY NAME is mandatory")
    private String repository_name;

    @Column(name = "image_name")
    @NotNull(message = "IMAGE NAME cannot be null")
    @NotEmpty(message = "IMAGE NAME is mandatory")
    private String image_name;

    @Column(name = "image_version")
    @NotNull(message = "IMAGE VERSION cannot be null")
    @NotEmpty(message = "IMAGE VERSION is mandatory")
    private String image_version;
}
