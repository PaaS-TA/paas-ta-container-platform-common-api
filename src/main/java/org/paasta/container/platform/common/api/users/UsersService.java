package org.paasta.container.platform.common.api.users;

import org.paasta.container.platform.common.api.common.CommonService;
import org.paasta.container.platform.common.api.common.Constants;
import org.paasta.container.platform.common.api.common.ResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private final CommonService commonService;
    private final UsersRepository userRepository;

    /**
     * Instantiates a new User service.
     *
     * @param commonService  the common service
     * @param userRepository the user repository
     */
    @Autowired
    public UsersService(CommonService commonService, UsersRepository userRepository) {
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
     * @return
     */
    public UsersList getUsersList() {
        UsersList usersList = new UsersList();

        try {
            usersList.setItems(userRepository.findAllByOrderByCreatedDesc());

        } catch (Exception e) {
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
    public UsersList updateUsers(String userId, Users users) {
        List<Users> updatedUsers = null;
        List<Users> userList = userRepository.findAllByUserIdOrderByCreatedDesc(userId);
        for (Users user:userList) {
            user.setPassword(users.getPassword());
            user.setEmail(users.getEmail());

            updatedUsers.add(user);
        }

        UsersList finalUsers = new UsersList();
        finalUsers.setItems(updatedUsers);

        return finalUsers;
    }


    /**
     * 사용자 삭제
     *
     * @param id
     */
    public ResultStatus deleteUsers(Long id) {
        userRepository.deleteById(id);
        return new ResultStatus(Constants.RESULT_STATUS_SUCCESS, "user delete success.", 200, "User number " + id + "is deleted success.");
    }
}
