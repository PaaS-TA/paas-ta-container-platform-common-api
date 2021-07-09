package org.paasta.container.platform.keycloak.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Keycloak User Repository 인터페이스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2021.06.07
 */
@Repository
public interface KeycloakUsersRepository extends JpaRepository<KeycloakUsers, Long>, JpaSpecificationExecutor<KeycloakUsers> {

    List<KeycloakUsers> findAllByRealmId (String realmId);

    List<KeycloakUsers> findAllByRealmIdAndIdAndUsername(String realmId, String id, String username);
}
