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

import java.util.ArrayList;
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
    @Value("${cpNamespace.defaultNamespace}")
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
            @ApiImplicitParam(name = "users", value = "유저 정보", required = true, dataType = "Users", paramType = "body")
    })
    @PostMapping(value = "/users")
    public Users createUsers(@RequestBody Users users) {
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
     * Admin Portal 모든 사용자 목록 조회(Get Users list of admin portal)
     *
     * @param cluster     the cluster
     * @param userType    the userType
     * @param searchParam the searchParam
     * @param orderBy     the orderBy
     * @param order       the order
     * @return the users list
     */
    @ApiOperation(value = "Admin Portal 모든 사용자 목록 조회(Get Users list of admin portal)", nickname = "getUsersListAllByCluster")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "userType", value = "유저 타입", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "searchParam", value = "검색 조건", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "정렬 기준, 기본값 creationTime(생성날짜)", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "정렬 순서, 기본값 desc(내림차순)", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "/clusters/{cluster:.+}/users")
    public UsersList getUsersListAllByCluster(@PathVariable(value = "cluster") String cluster,
                                              @RequestParam(name = "userType") String userType,
                                              @RequestParam(name = "searchParam") String searchParam,
                                              @RequestParam(name = "orderBy") String orderBy,
                                              @RequestParam(name = "order") String order) {

        List<String> userTypeList = new ArrayList<>();

        if (AUTH_CLUSTER_ADMIN.equals(userType)) {
            userTypeList.add(userType);
        } else {
            userTypeList.add(AUTH_NAMESPACE_ADMIN);
            userTypeList.add(AUTH_USER);
        }

        UsersSpecification usersSpecification = new UsersSpecification();

        usersSpecification.setClusterName(cluster);
        usersSpecification.setNameLike(searchParam);
        usersSpecification.setUserTypeIn(userTypeList);
        usersSpecification.setCpNamespace(defaultNamespace);

        return userService.getUsersListAllByCluster(usersSpecification, orderBy, order);
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
                          @PathVariable(value = "userId") String userId) {
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
}

