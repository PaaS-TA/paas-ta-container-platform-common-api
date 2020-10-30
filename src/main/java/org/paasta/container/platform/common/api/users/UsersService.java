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
     * Instantiates a new User service
     *
     * @param passwordEncoder the password encoder
     * @param commonService   the common service
     * @param userRepository  the user repository
     */
    @Autowired
    public UsersService(PasswordEncoder passwordEncoder, CommonService commonService, UsersRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.commonService = commonService;
        this.userRepository = userRepository;
    }


    /**
     * Users 등록(Create Users)
     *
     * @param users the users
     * @return return is succeeded
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
     * 각 Namespace 별 Users 목록 조회(Get Users namespace list)
     *
     * @param namespace the namespace
     * @return the users list
     */
    public UsersList getUsersListByNamespace(String namespace) {
        UsersList usersList = new UsersList();
        usersList.setItems(userRepository.findAllByCpNamespaceOrderByCreatedDesc(namespace));
        return (UsersList) commonService.setResultModel(usersList, Constants.RESULT_STATUS_SUCCESS);
    }


    /**
     * 등록 된 Users 목록 조회(Get Registered Users list)
     *
     * @return the users list
     */
    public Map<String, List> getUsersNameList() {
        List<String> list = userRepository.getUsersNameList();

        Map<String, List> map = new HashMap<>();
        map.put("users", list);

        return map;
    }

    /**
     * 각 Namespace 별 등록된 Users 목록 조회(Get Registered Users namespace list)
     *
     * @param namespace the namespace
     * @return the users list
     */
    public Map<String, List> getUsersNameListByNamespace(String namespace) {
        List<String> list = userRepository.getUsersNameListByCpNamespaceOrderByCreatedDesc(namespace);

        Map<String, List> map = new HashMap<>();
        map.put("users", list);

        return map;
    }

    /**
     * 로그인 기능을 위한 Users 상세 조회(Get Users detail for login)
     *
     * @param userId the userId
     * @param isAdmin the isAdmin
     * @return the users detail
     */
    public Users getUserDetailsForLogin(String userId, String isAdmin) {

        Users user = null;
        if (isAdmin.equals("true")) {
            user = userRepository.getOneUsersDetailByUserIdForAdmin(userId);
        } else if(isAdmin.equals("false")) {
            user = userRepository.getOneUsersDetailByUserId(userId);
        }

        return user;

    }


    /**
     * Users 상세 조회(Get Users detail)
     * (Namespace 는 다르나 동일한 User Name 과 Password 를 가진 행이 1개 이상이 존재할 수 있음)
     *
     * @param userId the userId
     * @return the users detail
     */
    public UsersList getUsersDetails(String userId) {

        UsersList usersList = new UsersList();
        usersList.setItems(userRepository.findAllByUserIdOrderByCreatedDesc(userId));

        return usersList;

    }


    /**
     * 전체 Users 목록 조회(Get All Users list)
     *
     * @param namespace the namespace
     * @return the users list
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
     * Namespace 와 UserId로 Users 단 건 상세 조회(Get Users namespace userId detail)
     *
     * @param namespace the namespace
     * @param userId the userId
     * @return the users detail
     */
    public Users getUsers(String namespace, String userId) {
        return userRepository.findByCpNamespaceAndUserId(namespace, userId);
    }


    /**
     * Users 수정(Update Users)
     * (User 정보를 수정 시 패스워드, 이메일 모두 바껴야 함)
     *
     * @param userId the userId
     * @param users the users
     * @return return is succeeded
     */
    @Transactional
    public UsersList updateUsers(String userId, Users users) {
        List<Users> updatedUsers = new ArrayList<>();
        List<Users> userList = userRepository.findAllByUserIdOrderByCreatedDesc(userId);
        for (Users user : userList) {
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
     * Users 삭제(Delete Users)
     *
     * @param id the id
     * @return return is succeeded
     */
    @Transactional
    public ResultStatus deleteUsers(Long id) {
        userRepository.deleteById(id);
        return new ResultStatus(Constants.RESULT_STATUS_SUCCESS, "user delete success.", 200, "User number " + id + "is deleted success.");
    }


    /**
     * Users 단 건 삭제(Delete A User)
     *
     * @param namespace the namespace
     * @param userId the userId
     * @return return is succeeded
     */
    @Transactional
    public ResultStatus deleteUsersByOne(String namespace, String userId) {
        userRepository.deleteByCpNamespaceAndUserId(namespace, userId);
        return new ResultStatus(Constants.RESULT_STATUS_SUCCESS, "user delete success.", 200, "User" + userId + "is deleted success in " + namespace + " namespace.");
    }


    /**
     * Namespace 관리자 상세 조회(Get Namespace Admin Users detail)
     *
     * @param namespace the namespace
     * @return the users detail
     */
    public Users getUsersByNamespaceAndNsAdmin(String namespace) {
        return userRepository.findByCpNamespaceAndUserType(namespace);
    }
}
