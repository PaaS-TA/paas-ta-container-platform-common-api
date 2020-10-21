package org.paasta.container.platform.common.api.users;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.paasta.container.platform.common.api.common.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    private final UsersService userService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     */
    @Autowired
    public UsersController(UsersService userService) {
        this.userService = userService;}

    /**
     * 사용자 등록
     *
     * @param users the users
     * @return the Users
     */
    @PostMapping(value = "/users")
    public Users createUsers(@RequestBody Users users) {
        return userService.createUsers(users);
    }

    /**
     * 등록돼있는 사용자들의 이름 목록 조회
     *
     * @return the Map
     */
    @GetMapping(value = "/users/names")
    public Map<String, List> getUsersNameList() {
        return userService.getUsersNameList();
    }


    /**
     * 전체 사용자 목록 조회
     *
     * @return the UsersList
     */
    @GetMapping(value = "/users")
    public UsersList getUsersList() {
        return userService.getUsersList();
    }


    /**
     * 각 Namespace별 사용자 목록 조회
     *
     * @param namespace
     * @return
     */
    @GetMapping(value = "/clusters/cp-cluster/namespaces/{namespace:.+}/users")
    public UsersList getUsersListByNamespace(@PathVariable(value = "namespace") String namespace) {
        return userService.getUsersListByNamespace(namespace);
    }


    /**
     * 각 namespace별 등록돼있는 사용자들의 이름 목록 조회
     *
     * @return the Map
     */
    @GetMapping(value = "/clusters/cp-cluster/namespaces/{namespace:.+}/users/names")
    public Map<String, List> getUsersNameListByNamespace(@PathVariable(value = "namespace") String namespace) {
        return userService.getUsersNameListByNamespace(namespace);
    }


    /**
     * 로그인 기능을 위한 User 상세 조회
     *
     * @return the Map
     */
    @GetMapping("/users/login/{userId:.+}")
    public Users getUserDetailsForLogin(@PathVariable(value = "userId") String userId) {
        return userService.getUserDetailsForLogin(userId); }


    /**
     * User 상세 정보를 조회
     *
     * @param userId the user id
     * @return the users
     */
    @ApiOperation(value="User 상세조회", nickname="getUserDetails")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "User 아이디", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("/users/{userId:.+}")
    public UsersList getUserDetails(@PathVariable(value = "userId") String userId) {
        return userService.getUsersDetails(userId);
    }


    /**
     * namespace와 userId로 사용자 단 건 상세 조회
     *
     * @param namespace
     * @param userId
     * @return
     */
    @GetMapping("/clusters/cp-cluster/namespaces/{namespace:.+}/users/{userId:.+}")
    public Users getUsers(@PathVariable(value = "namespace") String namespace, @PathVariable(value = "userId") String userId) {
        return userService.getUsers(namespace, userId);
    }


    /**
     * 사용자 정보 수정
     *
     * @param userId
     * @param users
     * @return
     */
    @PutMapping(value = "/users/{userId:.+}")
    public UsersList updateUsers(@PathVariable(value = "userId") String userId, @RequestBody Users users) {
        return userService.updateUsers(userId, users);
    }


    /**
     * 사용자 삭제
     *
     * @param id
     */
    @DeleteMapping(value = "/users/{id:.+}")
    public ResultStatus deleteUsers(@PathVariable(value = "id") Long id) {
        return userService.deleteUsers(id);
    }
}

