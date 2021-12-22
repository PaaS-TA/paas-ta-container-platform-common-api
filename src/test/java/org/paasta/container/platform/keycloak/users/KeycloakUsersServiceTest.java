package org.paasta.container.platform.keycloak.users;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.yml")
public class KeycloakUsersServiceTest {
    private static final String REALM_ID = "testrealm";
    private static final String ID = "1";
    private static final String USER_ID = "testuser";
    KeycloakUsers keycloakUsers = null;
    private static List<KeycloakUsers> keycloakUsersList = null;

    @Mock
    KeycloakUsersRepository keycloakUsersRepository;

    @InjectMocks
    KeycloakUsersService keycloakUsersService;

    @Before
    public void setUp() {
        keycloakUsers = new KeycloakUsers();
        keycloakUsers.setId(ID);
        keycloakUsers.setRealmId(REALM_ID);
        keycloakUsers.setUsername(USER_ID);

        keycloakUsersList = new ArrayList<>();
        keycloakUsersList.add(keycloakUsers);
    }


    @Test
    public void getKeycloakUserListByRealm() {
        when(keycloakUsersRepository.findAllByRealmId(REALM_ID)).thenReturn(keycloakUsersList);
        List<KeycloakUsers> keycloakUsersList = keycloakUsersService.getKeycloakUserListByRealm(REALM_ID);
        assertNotNull(keycloakUsersList);

    }

    @Test
    public void getKeycloakUser() {
        when(keycloakUsersRepository.findAllByRealmIdAndIdAndUsername(REALM_ID, ID, USER_ID)).thenReturn(keycloakUsersList);
        List<KeycloakUsers> keycloakUsersList = keycloakUsersService.getKeycloakUser(REALM_ID, ID, USER_ID);
        assertNotNull(keycloakUsersList);
    }
}