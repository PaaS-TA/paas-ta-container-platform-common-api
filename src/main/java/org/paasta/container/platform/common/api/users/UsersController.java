package org.paasta.container.platform.common.api.users;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    private final PasswordEncoder passwordEncoder;
    private final UsersService userService;

    /**
     * Instantiates a new User controller.
     *
     * @param passwordEncoder the password Encoder
     * @param userService the user service
     */
    @Autowired
    public UsersController(PasswordEncoder passwordEncoder, UsersService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;}

    /**
     * 사용자 회원가입
     *
     * @param users the users
     * @return the Users
     */
    @PostMapping(value = "/users")
    public Users createUsers(@RequestBody Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
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
     * 각 Namespace별 사용자 목록 조회
     *
     * @param namespace
     * @return
     */
    @GetMapping(value = "/clusters/cp-cluster/namespaces/{namespace:.+}/users")
    public UsersList getUsersList(@PathVariable(value = "namespace") String namespace) {
        return userService.getUsersList(namespace);
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
     * User  상세 정보를 조회한다.
     *
     * @param userId the user id
     * @return the users
     */
    @ApiOperation(value="User 상세조회", nickname="getUserDetails")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "User 아이디", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("/{userId:.+}")
    public Users getUserDetails(@PathVariable(value = "userId") String userId) {
        return userService.getUsersDetails(userId); }




}























