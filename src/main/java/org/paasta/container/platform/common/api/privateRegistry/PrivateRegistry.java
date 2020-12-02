package org.paasta.container.platform.common.api.privateRegistry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Private Registry 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.12.01
 */
@Entity
@Table(name = "private_repository")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PrivateRegistry {

    @Transient
    private String resultCode;

    @Transient
    private String resultMessage;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private long seq;

    @Column(name = "repository_url", nullable = false)
    @NotNull(message = "Repository Url cannot be null")
    private String repositoryUrl;

    @Column(name = "repository_name", nullable = false)
    @NotNull(message = "Repository Name cannot be null")
    private String repositoryName;

    @Column(name = "image_name", nullable = false)
    @NotNull(message = "Image Name cannot be null")
    private String imageName;

    @Column(name = "image_version", nullable = false)
    @NotNull(message = "image Version cannot be null")
    private String imageVersion;


}
