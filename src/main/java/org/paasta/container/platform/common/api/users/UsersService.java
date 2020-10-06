package org.paasta.container.platform.common.api.users;

import org.paasta.container.platform.common.api.common.CommonService;
import org.paasta.container.platform.common.api.common.Constants;
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
        this.userRepository = userRepository;}


    /**
     * 사용자 회원가입
     *
     * @param users the users
     * @return the Users
     */
    @Transactional
    public Users createUsers(Users users) {
        Users createdUsers = new Users();

        try{
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
    public UsersList getUsersList(String namespace) {
        UsersList usersList = new UsersList();
        usersList.setItems(userRepository.findAllByCpNamespace(namespace));
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
        List<String> list = userRepository.getUsersNameListByCpNamespace(namespace);

        Map<String, List> map = new HashMap<>();
        map.put("users", list);

        return map;
    }

    /**
     * User 상세 정보를 조회한다.
     *
     * @param userId the user id
     * @return the users
     */
    public Users getUsersDetails(String userId) { return userRepository.findByUserId(userId); }
}
