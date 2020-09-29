package org.paasta.container.platform.common.api.users;

import org.paasta.container.platform.common.api.common.CommonService;
import org.paasta.container.platform.common.api.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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


    @Transactional
    public Users createUsers(Users users) {
        Users createdUsers = new Users();

        try{
            createdUsers = userRepository.save(users);
        } catch (DataIntegrityViolationException e) {
            createdUsers.setResultMessage(e.getMessage());
            return (Users) commonService.setResultModel(createdUsers, Constants.RESULT_STATUS_FAIL);
        }

        return (Users) commonService.setResultModel(createdUsers, Constants.RESULT_STATUS_SUCCESS);
    }

    public UsersList getUsersList() {
        UsersList usersList = new UsersList();
        usersList.setItems(userRepository.findAll());
        return (UsersList) commonService.setResultModel(usersList, Constants.RESULT_STATUS_SUCCESS);
    }

    public List<String> getUsersNameList() {
        return userRepository.getUsersNameList();
    }
}
