package org.paasta.container.platform.keycloak.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Keycloak User Service 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2021.06.07
 */
@Service
public class KeycloakUsersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakUsersService.class);

    private final KeycloakUsersRepository keycloakUsersRepository;

    /**
     * Instantiates a new Keycloak User service
     *
     * @param keycloakUsersRepository the keycloak user repository
     */
    @Autowired
    public KeycloakUsersService(KeycloakUsersRepository keycloakUsersRepository) {
        this.keycloakUsersRepository = keycloakUsersRepository;
    }



    /**
     * Keycloak Realm 사용자 목록 조회 (Get Keycloak Realm User List)
     *
     * @return the keycloak users
     */
    public List<KeycloakUsers> getKeycloakUserListByRealm(String realmId) {
      return keycloakUsersRepository.findAllByRealmId(realmId);
    }


    /**
     * Keycloak 단건 사용자 조회 (Get Keycloak User)
     *
     * @return the keycloak users
     */
    public List<KeycloakUsers> getKeycloakUser(String realmId, String id, String username) {
        return keycloakUsersRepository.findAllByRealmIdAndIdAndUsername(realmId, id, username);
    }

}
