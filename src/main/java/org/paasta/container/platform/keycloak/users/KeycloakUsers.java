package org.paasta.container.platform.keycloak.users;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User Model 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2021.06.07
 */
@Entity
@Table(name = "USER_ENTITY")
@Data
public class KeycloakUsers {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "realm_id")
    private String realmId;

    @Column(name = "username")
    private String username;
}
