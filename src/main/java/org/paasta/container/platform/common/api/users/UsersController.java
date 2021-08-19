package org.paasta.container.platform.common.api.users;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.paasta.container.platform.common.api.common.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

import static org.paasta.container.platform.common.api.common.Constants.*;

/**
 * User Controller 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.09.22
 */
@Api(value = "UsersController v1")
@RestController
@RequestMapping
public class UsersController {
    @Value("${cp.defaultNamespace}")
    private String defaultNamespace;

    private final UsersService userService;

    /**
     * Instantiates a new User controller
     *
     * @param userService the user service
     */
    @Autowired
    public UsersController(UsersService userService) {
        this.userService = userService;
    }

    /**
     * Users 등록(Create Users)
     *
     * @param users the users
     * @return return is succeeded
     */
    @ApiOperation(value = "Users 등록(Create Users)", nickname = "createUsers")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "users", value = "유저 정보", required = true, dataType = "Users", paramType = "body"),
            @ApiImplicitParam(name = "isFirst", value = "사용자 첫등록 유무", required = false, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/users")
    public Users createUsers(@RequestBody Users users,
                             @RequestParam(required = false, defaultValue = "false") String isFirst) {
        return userService.createUsers(users);
    }


    /**
     * Users 권한 변경 저장(Modify Users)
     *
     * @param users the users
     * @return return is succeeded
     */
    @ApiOperation(value = "Users 권한 변경 저장(Modify Users)", nickname = "modifyUsers")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "users", value = "유저 정보", required = true, dataType = "Users", paramType = "body")
    })
    @PutMapping(value = "/users")
    public Users modifyUsers(@RequestBody Users users) {
        return userService.modifyUsers(users);
    }


    /**
     * 등록 된 Users 목록 조회(Get Registered Users list)
     *
     * @return the users list
     */
    @ApiOperation(value = "등록 된 Users 목록 조회(Get Registered Users list)", nickname = "getUsersNameList")
    @GetMapping(value = "/users/names")
    public Map<String, List> getUsersNameList() {
        return userService.getUsersNameList();
    }


    /**
     * 전체 Users 목록 조회(Get All Users list)
     *
     * @param namespace the namespace
     * @return the users list
     */
    @ApiOperation(value = "전체 Users 목록 조회(Get All Users list)", nickname = "getUsersList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "/users")
    public UsersList getUsersList(@RequestParam(name = "namespace") String namespace) {
        return userService.getUsersList(namespace);
    }


    /**
     * Admin Portal 활성화 여부에 따른 사용자 목록 조회(Get Users list of admin portal)
     *
     * @param cluster    the cluster
     * @param searchName the searchName
     * @return the users list
     */
    @ApiOperation(value = "Admin Portal 활성화 여부에 따른 사용자 목록 조회(Get Users list of admin portal)", nickname = "getUsersListAllByCluster")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchParam", value = "검색 조건", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "searchParam", value = "검색 조건", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "/clusters/{cluster:.+}/users")
    public UsersAdminList getUsersListAllByCluster(@PathVariable(value = "cluster") String cluster,
                                                   @RequestParam(required = false, defaultValue = "") String searchName,
                                                   @RequestParam(required = false, defaultValue = "true") String isActive){
        if(isActive.equalsIgnoreCase(IS_ADMIN_FALSE)) {
            // 비활성화 사용자인 경우
            return userService.getInActiveUsersList(searchName);
        }

        return userService.getActiveUsersList(searchName);
    }


    /**
     * 각 Namespace 별 Users 목록 조회(Get Users namespace list)
     *
     * @param cluster    the cluster
     * @param namespace  the namespace
     * @param orderBy    the orderBy
     * @param order      the order
     * @param searchName the searchName
     * @return the users list
     */
    @ApiOperation(value = "각 Namespace 별 Users 목록 조회(Get Users namespace list)", nickname = "getUsersListByNamespace")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "orderBy", value = "정렬 기준, 기본값 created(생성날짜)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "정렬 순서, 기본값 desc(내림차순)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "searchName", value = "userId 검색", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/users")
    public UsersList getUsersListByNamespace(@PathVariable(value = "cluster") String cluster,
                                             @PathVariable(value = "namespace") String namespace,
                                             @RequestParam(required = false, defaultValue = "created") String orderBy,
                                             @RequestParam(required = false, defaultValue = "desc") String order,
                                             @RequestParam(required = false, defaultValue = "") String searchName) {
        return userService.getUsersListByNamespace(namespace, orderBy, order, searchName);
    }


    /**
     * 각 Namespace 별 등록된 Users 목록 조회(Get Registered Users namespace list)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @return the users list
     */
    @ApiOperation(value = "각 Namespace 별 등록된 Users 목록 조회(Get Registered Users namespace list)", nickname = "getUsersNameListByNamespace")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/users/names")
    public Map<String, List> getUsersNameListByNamespace(@PathVariable(value = "cluster") String cluster,
                                                         @PathVariable(value = "namespace") String namespace) {
        return userService.getUsersNameListByNamespace(namespace);
    }


    /**
     * 로그인 기능을 위한 Users 상세 조회(Get Users detail for login)
     *
     * @param userId  the userId
     * @param isAdmin the isAdmin
     * @return the users detail
     */
    @ApiOperation(value = "로그인 기능을 위한 Users 상세 조회(Get Users detail for login)", nickname = "getUserDetailsForLogin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "유저 Id", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping("/users/login/{userId:.+}")
    public Users getUserDetailsForLogin(@PathVariable(value = "userId") String userId,
                                        @ApiIgnore @RequestParam(name = "isAdmin", defaultValue = "false") String isAdmin) {
        return userService.getUserDetailsForLogin(userId, isAdmin);
    }


    /**
     * Users 상세 조회(Get Users detail)
     *
     * @param userId the userId
     * @return the users detail
     */
    @ApiOperation(value = "Users 상세 조회(Get Users detail)", nickname = "getUserDetails")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "User 아이디", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping("/users/{userId:.+}")
    public UsersList getUserDetails(@PathVariable(value = "userId") String userId) {
        return userService.getUsersDetails(userId);
    }


    /**
     * Namespace 와 UserId로 Users 단 건 상세 조회(Get Users namespace userId detail)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param userId    the userId
     * @return the users detail
     */
    @ApiOperation(value = "Namespace 와 UserId로 Users 단 건 상세 조회(Get Users namespace userId detail)", nickname = "getUsers")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "User 아이디", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping("/clusters/{cluster:.+}/namespaces/{namespace:.+}/users/{userId:.+}")
    public Users getUsers(@PathVariable(value = "cluster") String cluster,
                          @PathVariable(value = "namespace") String namespace,
                          @PathVariable(value = "userId") String userId,
                          @RequestParam(required = false, name = "isCA", defaultValue = "false") String isCA) {
        if (isCA.equalsIgnoreCase(IS_ADMIN_TRUE)) {
            return userService.getUsersByNamespaceAndUserIdAndUserType(namespace, userId, AUTH_CLUSTER_ADMIN);
        }

        return userService.getUsers(namespace, userId);
    }


    /**
     * 모든 Namespace 중 해당 사용자가 포함된 Users 목록 조회
     *
     * @param cluster the cluster
     * @param userId  the usrId
     * @return the users list
     */
    @GetMapping("/clusters/{cluster:.+}/users/{userId:.+}")
    public UsersList getNamespaceListByUserId(@PathVariable(value = "cluster") String cluster,
                                              @PathVariable(value = "userId") String userId) {
        return userService.getNamespaceListByUserId(cluster, userId);
    }


    /**
     * Users 정보 수정(Update Users)
     *
     * @param userId the userId
     * @param users  the users
     * @return return is succeeded
     */
    @ApiOperation(value = "Users 정보 수정(Update Users Info)", nickname = "updateUsers")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "User 아이디", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "users", value = "User 정보", required = true, dataType = "Users", paramType = "body")
    })
    @PutMapping(value = "/users/{userId:.+}")
    public UsersList updateUsers(@PathVariable(value = "userId") String userId,
                                 @RequestBody Users users) {
        return userService.updateUsers(userId, users);
    }


    /**
     * Users 삭제(Delete Users)
     *
     * @param id the id
     * @return return is succeeded
     */
    @ApiOperation(value = "Users 삭제(Delete Users)", nickname = "deleteUsers")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "User 아이디", required = true, dataType = "Long", paramType = "path")
    })
    @DeleteMapping(value = "/users/{id:.+}")
    public ResultStatus deleteUsers(@PathVariable(value = "id") Long id) {
        return userService.deleteUsers(id);
    }


    /**
     * Users 단 건 삭제(Delete A User)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param userId    the userId
     * @return return is succeeded
     */
    @ApiOperation(value = "Users 단 건 삭제(Delete A User)", nickname = "deleteUsersByOne")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "User 아이디", required = true, dataType = "String", paramType = "path")
    })
    @DeleteMapping("/clusters/{cluster:.+}/namespaces/{namespace:.+}/users/{userId:.+}")
    public ResultStatus deleteUsersByOne(@PathVariable(value = "cluster") String cluster,
                                         @PathVariable(value = "namespace") String namespace,
                                         @PathVariable(value = "userId") String userId) {
        return userService.deleteUsersByOne(namespace, userId);
    }


    /**
     * Namespace 관리자 상세 조회(Get Namespace Admin Users detail)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @return the users detail
     */
    @ApiOperation(value = "해당 Namespace의 Namespace 관리자 상세 조회(Get Namespace Admin Users detail)", nickname = "getUsersByNamespaceAndNsAdmin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping("/clusters/{cluster:.+}/namespaces/{namespace:.+}")
    public Users getUsersByNamespaceAndNsAdmin(@PathVariable(value = "cluster") String cluster,
                                               @PathVariable(value = "namespace") String namespace) {
        return userService.getUsersByNamespaceAndNsAdmin(cluster, namespace);
    }


    /**
     * userRegisterCheck
     * CLUSTER_ADMIN 권한을 가진 운영자 상세 조회(Get Cluster Admin's info)
     *
     * @param cluster the cluster
     * @param userId  the user id
     * @return the users detail
     */
    @ApiOperation(value = "CLUSTER_ADMIN 권한을 가진 운영자 상세 조회(Get Cluster Admin's info)", nickname = "getUsersByClusterNameAndUserIdAndUserType")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "User 아이디", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping("/clusters/{cluster:.+}/users/{userId:.+}/userType")
    public Users getUsersByClusterNameAndUserIdAndUserType(@PathVariable(value = "cluster") String cluster,
                                                           @PathVariable(value = "userId") String userId) {
        return userService.getUsersByClusterNameAndUserIdAndUserType(cluster, userId);
    }


    /**
     * TEMP NAMESPACE 만 속한 사용자 조회 (Get users who belong to Temp Namespace only)
     *
     * @param cluster     the cluster
     * @param searchParam the searchParam
     * @return the users detail
     */
    @ApiOperation(value = "TEMP NAMESPACE 만 속한 사용자 조회 (Get users who belong to Temp Namespace only)", nickname = "getUserListOnlyTempNamesapce")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "searchParam", value = "검색 조건", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/clusters/{cluster:.+}/users/tempNamespace")
    public UsersList getUserListOnlyTempNamesapce(@PathVariable(value = "cluster") String cluster,
                                                  @RequestParam(name = "searchParam", defaultValue = "") String searchParam) {
        return userService.getUserListOnlyTempNamesapce(cluster, searchParam);
    }


    //// keycloak 이후로 추가

    /**
     * 클러스터 관리자 등록여부 조회(Cluster Admin Registration Check)
     *
     * @return the users
     */
    @ApiOperation(value = "CLUSTER ADMIN 등록여부 조회 (Cluster Admin Registration Check)", nickname = "getClusterAdminRegister")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "User ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userAuthId", value = "Keycloak User ID", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/clusterAdminRegisterCheck")
    public UsersList checkClusterAdminRegister(@RequestParam(required = false, defaultValue = "") String userId,
                                               @RequestParam(required = false, defaultValue = "") String userAuthId) {
        return userService.getClusterAdminRegisterCheck(userId, userAuthId);
    }


    /**
     * User 등록여부 조회(User Registration Check)
     *
     * @param userId     the userId
     * @param userAuthId the userAuthId
     * @return the usersList
     */
    @ApiOperation(value = "User 등록여부 조회(User Registration Check)", nickname = "checkUserRegister")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "User ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userAuthId", value = "Keycloak User ID", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/userRegisterCheck")
    public UsersList checkUserRegister(@RequestParam(required = false, defaultValue = "") String userId,
                                       @RequestParam(required = false, defaultValue = "") String userAuthId) {
        return userService.getUserRegisterCheck(userId, userAuthId);
    }


    /**
     * Clsuter Admin 등록(Sign up cluster admin)
     *
     * @param users the users
     * @param param the param
     * @return return is succeeded
     */
    @ApiOperation(value = "Clsuter Admin 등록(Create Cluster Admin)", nickname = "signUpClusterAdmin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "users", value = "유저 정보", required = true, dataType = "Users", paramType = "body")
    })
    @PostMapping(value = "/cluster/all/admin/signUp")
    public ResultStatus signUpClusterAdmin(@RequestBody Users users,
                                           @RequestParam(required = false, name = "param", defaultValue = "") String param) {
        return userService.signUpClusterAdmin(users, param);
    }


    /**
     * User 등록(Sign up user)
     *
     * @param users the users
     * @return return is succeeded
     */
    @ApiOperation(value = "User 등록(Sign up user)", nickname = "signUpUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "users", value = "유저 정보", required = true, dataType = "Users", paramType = "body")
    })
    @PostMapping(value = "/cluster/all/user/signUp")
    public ResultStatus signUpUser(@RequestBody Users users) {
        return userService.signUpUser(users);
    }


    /**
     * 클러스터 관리자 계정 상세 조회(Get cluster admin info)
     *
     * @return the usersList
     */
    @ApiOperation(value = "클러스터 관리자 계정 조회(Get cluster admin info)", nickname = "getClusterAdminInfoDetails")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchName", value = "userId 검색", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "/cluster/all/admin/info")
    public UsersList getClusterAdminInfoDetails(@RequestParam(required = false, defaultValue = "") String searchName) {
        return userService.getClusterAdminInfo(searchName);
    }



    /**
     * 사용자 상세 조회(Get user info details)
     *
     * @return the usersList
     */
    @ApiOperation(value = "사용자 상세 조회(Get user info details)", nickname = "getUserInfoDetails")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 아이디", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userType", value = "사용자 타입", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "/cluster/all/user/details")
    public UsersAdmin getUserInfoDetails(@RequestParam(required = true) String userId,
                                        @RequestParam(required = true) String userType) {

        return userService.getUserInfoDetails(userId, userType);
    }


    /**
     * 네임스페이스 관리자 체크 조회 (Get user list whether user is namespace admin or not)
     * @param namespace the namespace
     * @return the users list
     */
    @ApiOperation(value = "네임스페이스 관리자 체크 조회(Get user list whether user is namespace admin or not)", nickname = "getUserIsNamespaceAdminCheck")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = "/clusters/all/namespaces/{namespace:.+}/adminCheck")
    public UsersList getUserIsNamespaceAdminCheck(@PathVariable(value = "namespace") String namespace) {

        if (namespace.equalsIgnoreCase(ALL_VAL)) {
            namespace = NONE_VAL;
        }

        return userService.getUserIsNamespaceAdminCheck(namespace);
    }

    /**
     * 사용자 아이디, 사용자 인증 아이디, 네임스페이스를 통한 Users 삭제 (Delete Users by userId, userAuthId and namespace)
     *
     * @param userId the userId
     * @param userAuthId the userAuthId
     * @param namespace the namespace
     * @return return is succeeded
     */
    @ApiOperation(value = "사용자 아이디, 사용자 인증 아이디, 네임스페이스를 통한 Users 삭제 (Delete Users by userId, userAuthId and namespace)", nickname = "deleteUsersByUserIdAndUserAuthIdAndNamespace")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 아이디", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userAuthId", value = "사용자 인증 아이디", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "query")
    })
    @DeleteMapping(value = "/cluster/all/user/delete")
    public ResultStatus deleteUsersByUserIdAndUserAuthIdAndNamespace(@RequestParam(required = true) String userId,
                                                                     @RequestParam(required = true) String userAuthId,
                                                                     @RequestParam(required = true) String namespace) {
        return userService.deleteUsersByUserIdAndUserAuthIdAndNamespace(userId, userAuthId, namespace);
    }


    /**
     * 네임스페이스 사용자 전체 삭제 (Delete Namespace All User)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @return return is succeeded
     */
    @ApiOperation(value = "네임스페이스 사용자 전체 삭제 (Delete Namespace All User)", nickname = "deleteAllUsersByNamespace")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path")
    })
    @DeleteMapping("/clusters/{cluster:.+}/namespaces/{namespace:.+}/users")
    public ResultStatus deleteAllUsersByNamespace(@PathVariable(value = "cluster") String cluster,
                                                  @PathVariable(value = "namespace") String namespace) {
        return userService.deleteAllUsersByNamespace(cluster, namespace);

    }


    /**
     * 클러스터 관리자 삭제 (Delete Cluster Admin)
     *
     * @param cluster the cluster
     * @return return is succeeded
     */
    @ApiOperation(value = "클러스터 관리자 삭제 (Delete Cluster Admin User)", nickname = "deleteClusterAdmin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
    })
    @DeleteMapping("/clusters/{cluster:.+}/admin/delete")
    public ResultStatus deleteClusterAdmin(@PathVariable(value = "cluster") String cluster) {
        return userService.deleteClusterAdmin(cluster);

    }

}

