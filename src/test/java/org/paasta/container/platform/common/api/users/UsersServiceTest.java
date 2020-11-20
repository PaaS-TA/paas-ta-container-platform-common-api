package org.paasta.container.platform.common.api.users;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.paasta.container.platform.common.api.common.CommonService;
import org.paasta.container.platform.common.api.common.Constants;
import org.paasta.container.platform.common.api.common.PropertyService;
import org.paasta.container.platform.common.api.common.ResultStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Users Service Test 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.11.17
 **/
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.yml")
public class UsersServiceTest {
    private static final String CLUSTER = "cp-cluster";
    private static final String CLUSTER_API_URL = "111.111.111.111:6443";
    private static final String CLUSTER_ADMIN_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IktNWmgxVXB3ajgwS0NxZjFWaVZJVGVvTXJoWnZ5dG0tMGExdzNGZjBKX00ifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJwYWFzLWYxMGU3ZTg4LTQ4YTUtNGUyYy04Yjk5LTZhYmIzY2ZjN2Y2Zi1jYWFzIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6InN1cGVyLWFkbWluLXRva2VuLWtzbXo1Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6InN1cGVyLWFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMjMwZWQ1OGQtNzc0MC00MDI4LTk0MTEtYTM1MzVhMWM0NjU4Iiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OnBhYXMtZjEwZTdlODgtNDhhNS00ZTJjLThiOTktNmFiYjNjZmM3ZjZmLWNhYXM6c3VwZXItYWRtaW4ifQ.nxnIJCOH_XVMK71s0gF8bgzSxA7g6_y7hGdboLvSqIAGf9J9AgG1DouP29uShK19fMsl9IdbGODPvtuiBz4QyGLPARZldmlzEyFG3k08UMNay1xX_oK-Fe7atMlYgvoGzyM_5-Zp5dyvnxE2skk524htMGHqW1ZwnHLVxtBg8AuGfMwLW1xahmktsNZDG7pRMasPsj73E85lfavMobBlcs4hwVcZU82gAg0SK1QVe7-Uc2ip_9doNo6_9rGW3FwHdVgUNAeCvPRGV0W1dKJv0IX5e_7fIPIznj2xXcZoHf3BnKfDayDIKJOCdsEsy_2NGi1tiD3UvzDDzZpz02T2sg";
    private static final String NAMESPACE = "cp-namespace";
    private static final String ALL_NAMESPACES = "all";
    private static final String USER_ID = "paasta";
    private static final String ROLE = "paas-ta-container-platform-init-role";
    private static final String ADMIN_ROLE = "paas-ta-container-platform-admin-role";
    private static final String SECRET_NAME = "paasta-token-jqrx4";
    private static final String SECRET_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IktNWmgxVXB3ajgwS0NxZjFWaVZJVGVvTXJoWnZ5dG0tMGExdzNGZjBKX00ifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJ0ZW1wLW5hbWVzcGFjZSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJ0ZXN0LXRva2VuLWpxcng0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6InRlc3QiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiI3Y2Q0Nzk4OC01YWViLTQ1ODQtYmNmOS04OTkwZTUzNWEzZGIiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6dGVtcC1uYW1lc3BhY2U6dGVzdCJ9.ZEwhnscTtPW6WrQ5I7fFWcsLWEqnilw7I8i7C4aSXElFHd583OQqTYGk8RUJU7UM6b2T8oKstejkLWE9xP3TchYyG5T-omZBCMe00JZIof4tp0MRZLgBhXizYXGvLb2bcMdlcWg2bCCVRO92Hjik-r-vqfaGbsRGx4dT2dk1sI4RA-XDnMsVFJS94V9P58cBupT1gRMrwWStrqlXrbiwgfIlGbU9GXnA07JUCMy-1wUYdMmRaICdj-Q7eNZ5BmKCNsFBcJKaDl5diNw-gSka2F61sywpezU-30sWAtRHYIYZt6PaAaZ4caAdR8f43Yq1m142RWsr3tunLgQ768UNtQ";
    private static final String PASSWORD = "PaaS-TA@2020";
    private static final String ENCODED_PASSWORD = "$2a$10$escP4RztAu6YnXIv0mEqsu/7o2ma/5eVnRs7RuGS7022CDHQV9s.6";

    private static final int OFFSET = 0;
    private static final int LIMIT = 1;
    private static final String ORDER_BY_CREATED = "created";
    private static final String ORDER_BY_USER_ID = "userId";
    private static final String ORDER = "desc";
    private static final String SEARCH_NAME = "paas";
    private static final String SEARCH_NAME_NULL = null;
    private static final boolean isAdmin = true;
    private static final boolean isSuccess = true;
    private static final String isAdminString = "true";
    private static final String isNotAdmin = "false";

    private static final String USER_TYPE_AUTH_CLUSTER_ADMIN = "administrator";
    private static final String USER_TYPE_AUTH_USER = "user";
    private static final String USER_TYPE_AUTH_NONE = "manager";

    private static Users users = null;
    private static List<Users> usersList = null;
    private static UsersList finalUsersList = null;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    CommonService commonService;

    @Mock
    PropertyService propertyService;

    @Mock
    UsersRepository usersRepository;

    @InjectMocks
    UsersService usersService;


    @Before
    public void setUp() {
        users = new Users();
        users.setUserId(USER_ID);
        users.setPassword(PASSWORD);
        users.setEmail("paasta@gmail.com");
        users.setClusterName(CLUSTER);
        users.setClusterApiUrl(CLUSTER_API_URL);
        users.setClusterToken(CLUSTER_ADMIN_TOKEN);
        users.setCpNamespace(NAMESPACE);
        users.setServiceAccountName(USER_ID);
        users.setSaSecret(SECRET_NAME);
        users.setSaToken(SECRET_TOKEN);
        users.setIsActive("Y");
        users.setRoleSetCode(ROLE);
        users.setUserType("USER");
        users.setDescription("desc");

        usersList = new ArrayList<>();
        usersList.add(users);

        finalUsersList = new UsersList();
        finalUsersList.setItems(usersList);
        finalUsersList.setResultCode(Constants.RESULT_STATUS_SUCCESS);
    }

    @Test
    public void createUsers_Valid() {
        Users createdUsers = users;
        createdUsers.setId(1);
        createdUsers.setResultCode(Constants.RESULT_STATUS_SUCCESS);

        when(passwordEncoder.encode(users.getPassword())).thenReturn(ENCODED_PASSWORD);
        when(usersRepository.save(users)).thenReturn(createdUsers);
        when(commonService.setResultModel(createdUsers, Constants.RESULT_STATUS_SUCCESS)).thenReturn(createdUsers);

        Users finalUser = usersService.createUsers(users);
        assertEquals(finalUser.getResultCode(), Constants.RESULT_STATUS_SUCCESS);
    }


    @Test
    public void modifyUsers() {
        Users modifiedUser = users;
        modifiedUser.setId(1);
        modifiedUser.setResultCode(Constants.RESULT_STATUS_SUCCESS);

        when(usersRepository.save(users)).thenReturn(modifiedUser);
        when(commonService.setResultModel(modifiedUser, Constants.RESULT_STATUS_SUCCESS)).thenReturn(modifiedUser);

        Users finalUser = usersService.modifyUsers(users);
        assertEquals(finalUser.getResultCode(), Constants.RESULT_STATUS_SUCCESS);
    }

    @Test
    public void getUsersListByNamespace_With_Search_Param() {
        UsersList userList = new UsersList();
        userList.setItems(usersList);

        UsersList finalUserList = new UsersList();
        finalUserList.setItems(usersList);
        finalUserList.setResultCode(Constants.RESULT_STATUS_SUCCESS);

        when(usersRepository.findAllByCpNamespaceAndUserIdContainingIgnoreCase(NAMESPACE, SEARCH_NAME, usersService.userSortDirection(ORDER_BY_CREATED, ORDER))).thenReturn(usersList);

        when(commonService.setResultModel(userList, Constants.RESULT_STATUS_SUCCESS)).thenReturn(finalUserList);

        UsersList finalUser = usersService.getUsersListByNamespace(NAMESPACE, ORDER_BY_CREATED, ORDER, SEARCH_NAME);
        assertEquals(finalUser.getResultCode(), Constants.RESULT_STATUS_SUCCESS);
    }

    @Test
    public void getUsersListByNamespace_No_Search_Param() {
        UsersList userList = new UsersList();
        userList.setItems(usersList);

        UsersList finalUserList = new UsersList();
        finalUserList.setItems(usersList);
        finalUserList.setResultCode(Constants.RESULT_STATUS_SUCCESS);

        when(usersRepository.findAllByCpNamespace(NAMESPACE, usersService.userSortDirection(ORDER_BY_CREATED, ORDER))).thenReturn(usersList);

        when(commonService.setResultModel(userList, Constants.RESULT_STATUS_SUCCESS)).thenReturn(finalUserList);

        UsersList finalUser = usersService.getUsersListByNamespace(NAMESPACE, ORDER_BY_CREATED, ORDER, SEARCH_NAME_NULL);
        assertEquals(finalUser.getResultCode(), Constants.RESULT_STATUS_SUCCESS);
    }

//    @Test
//    public void userSortDirection() {
//
//    }

    @Test
    public void getUsersNameList() {
        List<String> list = new ArrayList<>();
        list.add("paasta");
        list.add("test");

        when(usersRepository.getUsersNameList()).thenReturn(list);

        Map<String, List> map = usersService.getUsersNameList();
        assertNotNull(map);

    }

    @Test
    public void getUsersNameListByNamespace() {
        List<String> list = new ArrayList<>();
        list.add("paasta");
        list.add("test");

        when(usersRepository.getUsersNameListByCpNamespaceOrderByCreatedDesc(NAMESPACE)).thenReturn(list);
        Map<String, List> map = usersService.getUsersNameListByNamespace(NAMESPACE);
        assertNotNull(map);
    }

    @Test
    public void getUserDetailsForLogin_Is_Admin() {
        when(usersRepository.getOneUsersDetailByUserIdForAdmin(USER_ID)).thenReturn(users);
        Users users = usersService.getUserDetailsForLogin(USER_ID, isAdminString);
        assertNotNull(users);
    }

    @Test
    public void getUserDetailsForLogin_Is_Not_Admin() {
        when(usersRepository.getOneUsersDetailByUserId(USER_ID)).thenReturn(users);
        Users users = usersService.getUserDetailsForLogin(USER_ID, isNotAdmin);
        assertNotNull(users);
    }

    @Test
    public void getUsersDetails() {
        when(usersRepository.findAllByUserIdOrderByCreatedDesc(USER_ID)).thenReturn(usersList);
        UsersList usersList = usersService.getUsersDetails(USER_ID);
        assertNotNull(usersList);
    }

    @Test
    public void getUsersList() {
        List<Object[]> values = Arrays.asList(new Object[] {"cp-namespace", "paasta", "paasta", "ns-admin-role", "USER", "2020-11-13", "Y"}, new Object[] {"cp-namespace", "test", "test", "ns-init-role", "USER", "2020-11-13", "Y"});
        when(usersRepository.findAllUsers(NAMESPACE)).thenReturn(values);
        List<Users> result = new ArrayList<>();
        UsersList successUsers = new UsersList();

        for (Object[] arrInfo : values) {
            Users users = new Users();
            users.setCpNamespace((String) arrInfo[0]);
            users.setUserId((String) arrInfo[1]);
            users.setServiceAccountName((String) arrInfo[2]);
            users.setRoleSetCode((String) arrInfo[3]);
            users.setUserType((String) arrInfo[4]);
            users.setCreated((String) arrInfo[5]);
            users.setIsActive((String) arrInfo[6]);

            result.add(users);
        }

        successUsers.setItems(result);

        when(commonService.setResultModel(successUsers, Constants.RESULT_STATUS_SUCCESS)).thenReturn(finalUsersList);

        UsersList usersList = usersService.getUsersList(NAMESPACE);
        assertNotNull(usersList);
    }

    @Test
    public void getUsersList_Index_Out_Of_Bound_Ex() {
        List<Object[]> values = Arrays.asList(new Object[] {"cp-namespace", "paasta", "paasta", "ns-admin-role", "USER"}, new Object[] {"cp-namespace", "test", "test", "ns-init-role", "USER"});
        when(usersRepository.findAllUsers(NAMESPACE)).thenReturn(values);

        UsersList failedUsersList = new UsersList();
        failedUsersList.setResultMessage("5");

        UsersList finalFailedUsersList = new UsersList();
        finalFailedUsersList.setResultMessage("5");
        finalFailedUsersList.setResultCode(Constants.RESULT_STATUS_FAIL);

        when(commonService.setResultModel(failedUsersList, Constants.RESULT_STATUS_FAIL)).thenReturn(finalFailedUsersList);

        UsersList usersList = usersService.getUsersList(NAMESPACE);
        assertNotNull(usersList);
    }

    @Test
    public void getUsers() {
        when(usersRepository.findByCpNamespaceAndUserId(NAMESPACE, USER_ID)).thenReturn(users);
        Users users = usersService.getUsers(NAMESPACE, USER_ID);

        assertNotNull(users);
    }

    @Test
    public void updateUsers() {
        when(usersRepository.findAllByUserIdOrderByCreatedDesc(USER_ID)).thenReturn(usersList);

        List<Users> updatedUsers = new ArrayList<>();
        for (Users user : usersList) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEmail(user.getEmail());
            user.setDescription(user.getDescription());

            updatedUsers.add(user);
        }
        when(usersRepository.saveAll(updatedUsers)).thenReturn(usersList);
        UsersList userList = usersService.updateUsers(USER_ID, users);

        assertNotNull(userList);
    }

    @Test
    public void deleteUsers() {
        Long id = Long.valueOf(1);
        usersRepository.deleteById(id);

        ResultStatus resultStatus = usersService.deleteUsers(id);
        assertEquals(resultStatus.getHttpStatusCode(), 200);
    }

    @Test
    public void deleteUsersByOne() {
        usersRepository.deleteByCpNamespaceAndUserId(NAMESPACE, USER_ID);

        ResultStatus resultStatus = usersService.deleteUsersByOne(NAMESPACE, USER_ID);
        assertEquals(resultStatus.getHttpStatusCode(), 200);
    }

    @Test
    public void getUsersByNamespaceAndNsAdmin() {
        when(usersRepository.findAllByClusterNameAndCpNamespace(CLUSTER, NAMESPACE)).thenReturn(users);

        Users users = usersService.getUsersByNamespaceAndNsAdmin(CLUSTER, NAMESPACE);
        assertNotNull(users);
    }

    @Test
    public void getNamespaceListByUserId() {
        String defaultNs ="paas-ta-container-platform-temp-namespace";

        when(propertyService.getDefaultNamespace()).thenReturn(defaultNs);
        when(usersRepository.findAllByClusterNameAndUserId(CLUSTER, USER_ID, defaultNs)).thenReturn(usersList);
        UsersList list = new UsersList();
        list.setItems(usersList);

        UsersList userList = usersService.getNamespaceListByUserId(CLUSTER, USER_ID);
        assertEquals(userList, list);
    }

//    @Test
//    public void getUsersListAllByCluster() {
//    }
}