package org.paasta.container.platform.common.api.users;

import org.paasta.container.platform.common.api.common.CommonService;
import org.paasta.container.platform.common.api.common.Constants;
import org.paasta.container.platform.common.api.common.ResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User Service 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.09.22
 */
@Service
public class UsersService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersService.class);
    private final PasswordEncoder passwordEncoder;
    private final CommonService commonService;
    private final UsersRepository userRepository;

    /**
     * Instantiates a new User service.
     *
     * @param passwordEncoder the password encoder
     * @param commonService  the common service
     * @param userRepository the user repository
     */
    @Autowired
    public UsersService(PasswordEncoder passwordEncoder, CommonService commonService, UsersRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.commonService = commonService;
        this.userRepository = userRepository;
    }


    /**
     * 사용자 등록
     *
     * @param users the users
     * @return the Users
     */
    @Transactional
    public Users createUsers(Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users createdUsers = new Users();

        try {
            createdUsers = userRepository.save(users);
        } catch (Exception e) {
            createdUsers.setResultMessage(e.getMessage());
            return (Users) commonService.setResultModel(createdUsers, Constants.RESULT_STATUS_FAIL);
        }

        return (Users) commonService.setResultModel(createdUsers, Constants.RESULT_STATUS_SUCCESS);
    }


    /**
     * 각 namespace별 사용자 목록 조회
     *
     * @param namespace
     * @return
     */
    public UsersList getUsersListByNamespace(String namespace) {
        UsersList usersList = new UsersList();
        usersList.setItems(userRepository.findAllByCpNamespaceOrderByCreatedDesc(namespace));
        return (UsersList) commonService.setResultModel(usersList, Constants.RESULT_STATUS_SUCCESS);
    }


    /**
     * 등록돼있는 사용자들의 이름 목록 조회
     *
     * @return the Map
     */
    public Map<String, List> getUsersNameList() {
        List<String> list = userRepository.getUsersNameList();

        Map<String, List> map = new HashMap<>();
        map.put("users", list);

        return map;
    }

    /**
     * 각 namespace별 등록돼있는 사용자들의 이름 목록 조회
     *
     * @return the Map
     */
    public Map<String, List> getUsersNameListByNamespace(String namespace) {
        List<String> list = userRepository.getUsersNameListByCpNamespaceOrderByCreatedDesc(namespace);

        Map<String, List> map = new HashMap<>();
        map.put("users", list);

        return map;
    }

    /**
     * 로그인을 위한 User 상세 정보를 조회
     *
     * @param userId the user id
     * @return the users
     */
    public Users getUserDetailsForLogin(String userId) {

        Users user = userRepository.getOneUsersDetailByUserId(userId);
        return user;

    }


    /**
     * User 상세 정보를 조회
     * namespace는 다르나 동일한 user name과 password를 가진 행이 1개 이상이 존재할 수 있다.
     *
     * @param userId the user id
     * @return the users
     */
    public UsersList getUsersDetails(String userId) {

        UsersList usersList = new UsersList();
        usersList.setItems(userRepository.findAllByUserIdOrderByCreatedDesc(userId));

        return usersList;

    }


    /**
     * 전체 사용자 목록 조회
     *
     * @return the UsersList
     */
    public UsersList getUsersList(String namespace) {
        UsersList usersList = new UsersList();

        try {
            List<Object[]> values = userRepository.findAllUsers(namespace);
            List<Users> result = new ArrayList<>();

            if (values != null && !values.isEmpty()) {
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
            }

            usersList.setItems(result);

        } catch (IndexOutOfBoundsException e) {
            usersList.setResultMessage(e.getMessage());
            return (UsersList) commonService.setResultModel(usersList, Constants.RESULT_STATUS_FAIL);
        }

        return (UsersList) commonService.setResultModel(usersList, Constants.RESULT_STATUS_SUCCESS);
    }


    /**
     * namespace와 userId로 사용자 단 건 상세 조회
     *
     * @param namespace
     * @param userId
     * @return
     */
    public Users getUsers(String namespace, String userId) {
        return userRepository.findByCpNamespaceAndUserId(namespace, userId);
    }


    /**
     * User 정보를 수정 시 패스워드, 이메일 모두 바껴야....
     *
     * @param userId
     * @param users
     * @return
     */
    @Transactional
    public UsersList updateUsers(String userId, Users users) {
        List<Users> updatedUsers = new ArrayList<>();
        List<Users> userList = userRepository.findAllByUserIdOrderByCreatedDesc(userId);
        for (Users user:userList) {
            user.setPassword(passwordEncoder.encode(users.getPassword()));
            user.setEmail(users.getEmail());

            updatedUsers.add(user);
        }

        userRepository.saveAll(updatedUsers);

        UsersList finalUsers = new UsersList();
        finalUsers.setItems(updatedUsers);

        return finalUsers;
    }


    /**
     * 사용자 삭제
     *
     * @param id
     */
    @Transactional
    public ResultStatus deleteUsers(Long id) {
        userRepository.deleteById(id);
        return new ResultStatus(Constants.RESULT_STATUS_SUCCESS, "user delete success.", 200, "User number " + id + "is deleted success.");
    }


    /**
     * 사용자 단 건 삭제
     *
     * @param namespace the namespace
     * @param userId the userId
     * @return the resultStatus
     */
    @Transactional
    public ResultStatus deleteUsersByOne(String namespace, String userId) {
        userRepository.deleteByCpNamespaceAndUserId(namespace, userId);
        return new ResultStatus(Constants.RESULT_STATUS_SUCCESS, "user delete success.", 200, "User" + userId + "is deleted success in " + namespace + " namespace.");
    }
}
