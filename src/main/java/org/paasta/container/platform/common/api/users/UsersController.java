package org.paasta.container.platform.common.api.users;

import io.swagger.annotations.Api;
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
@RequestMapping(value = "/users")
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
    @PostMapping
    public Users createUsers(@RequestBody Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return userService.createUsers(users);
    }

//    @GetMapping
//    public UsersList getUsersList() {
//        return userService.getUsersList();
//    }


    /**
     * 등록돼있는 사용자들의 이름 목록 조회
     *
     * @return the Map
     */
    @GetMapping
    public Map<String, List> getUsersNameList() {
        return userService.getUsersNameList();
    }

}























