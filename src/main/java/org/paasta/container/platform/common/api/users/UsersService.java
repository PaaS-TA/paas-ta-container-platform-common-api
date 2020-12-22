package org.paasta.container.platform.common.api.users;

import org.paasta.container.platform.common.api.common.CommonService;
import org.paasta.container.platform.common.api.common.Constants;
import org.paasta.container.platform.common.api.common.PropertyService;
import org.paasta.container.platform.common.api.common.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
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
    @Value("${cpNamespace.defaultNamespace}")
    private String defaultNamespace;

    private final PasswordEncoder passwordEncoder;
    private final CommonService commonService;
    private final UsersRepository userRepository;
    private final PropertyService propertyService;

    /**
     * Instantiates a new User service
     *
     * @param passwordEncoder the password encoder
     * @param commonService   the common service
     * @param userRepository  the user repository
     * @param propertyService the property service
     */
    @Autowired
    public UsersService(PasswordEncoder passwordEncoder, CommonService commonService, UsersRepository userRepository, PropertyService propertyService) {
        this.passwordEncoder = passwordEncoder;
        this.commonService = commonService;
        this.userRepository = userRepository;
        this.propertyService = propertyService;
    }


    /**
     * Users 등록(Create Users)
     *
     * @param users the users
     * @return the users
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
     * Users 권한 변경 저장(Modify Users)
     *
     * @param users the users
     * @return the users
     */
    @Transactional
    public Users modifyUsers(Users users) {
        try {
            users = userRepository.save(users);
        } catch (Exception e) {
            users.setResultMessage(e.getMessage());
            return (Users) commonService.setResultModel(users, Constants.RESULT_STATUS_FAIL);
        }

        return (Users) commonService.setResultModel(users, Constants.RESULT_STATUS_SUCCESS);
    }


    /**
     * 각 Namespace 별 Users 목록 조회(Get Users namespace list)
     *
     * @param namespace  the namespace
     * @param orderBy    the orderBy
     * @param order      the order
     * @param searchName the searchName
     * @return the users list
     */
    public UsersList getUsersListByNamespace(String namespace, String orderBy, String order, String searchName) {
        UsersList usersList = new UsersList();

        if (searchName != null && !searchName.trim().isEmpty()) {
            usersList.setItems(userRepository.findAllByCpNamespaceAndUserIdContainingIgnoreCase(namespace, searchName, userSortDirection(orderBy, order)));
        } else {
            usersList.setItems(userRepository.findAllByCpNamespace(namespace, userSortDirection(orderBy, order)));
        }
        return (UsersList) commonService.setResultModel(usersList, Constants.RESULT_STATUS_SUCCESS);
    }


    /**
     * Users 목록 정렬(Sorting Users List)
     *
     * @param orderBy the orderBy
     * @param order   the order
     * @return the Sort
     */
    public Sort userSortDirection(String orderBy, String order) {
        String properties = null;
        String sort = null;

        //properties
        if (orderBy.toUpperCase().equals(Constants.CP_USER_ID_COLUM.toUpperCase())) {
            properties = Constants.CP_USER_ID_COLUM;
        } else {
            properties = Constants.CP_USER_CREATED_COLUM;
        }

        //sort
        if (order.toUpperCase().equals(Constants.DESC.toUpperCase())) {
            sort = Constants.DESC;
        } else {
            sort = Constants.ASC;
        }

        return new Sort(Sort.Direction.fromString(sort), properties);
    }


    /**
     * 등록 된 Users 목록 조회(Get Registered Users list)
     *
     * @return the map
     */
    public Map<String, List> getUsersNameList() {
        List<String> list = userRepository.getUsersNameList();
        Map<String, List> map = new HashMap<>();
        map.put(Constants.USERS, list);

        return map;
    }


    /**
     * 각 Namespace 별 등록된 Users 목록 조회(Get Registered Users namespace list)
     *
     * @param namespace the namespace
     * @return the map
     */
    public Map<String, List> getUsersNameListByNamespace(String namespace) {
        List<String> list = userRepository.getUsersNameListByCpNamespaceOrderByCreatedDesc(namespace);

        Map<String, List> map = new HashMap<>();
        map.put(Constants.USERS, list);

        return map;
    }


    /**
     * 로그인 기능을 위한 Users 상세 조회(Get Users detail for login)
     *
     * @param userId  the userId
     * @param isAdmin the isAdmin
     * @return the users
     */
    public Users getUserDetailsForLogin(String userId, String isAdmin) {

        Users user;
        if (Constants.IS_ADMIN_TRUE.equals(isAdmin)) {
            user = userRepository.getOneUsersDetailByUserIdForAdmin(userId);
        } else {
            user = userRepository.getOneUsersDetailByUserId(userId);
        }

        return user;

    }


    /**
     * Users 상세 조회(Get Users detail)
     * (Namespace 는 다르나 동일한 User Name 과 Password 를 가진 행이 1개 이상이 존재할 수 있음)
     *
     * @param userId the userId
     * @return the users list
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
     * @param userId    the userId
     * @return the users
     */
    public Users getUsers(String namespace, String userId) {
        return userRepository.findByCpNamespaceAndUserId(namespace, userId);
    }


    /**
     * Users 수정(Update Users)
     * (User 정보 수정 시 해당 정보 모두 바껴야 함)
     *
     * @param userId the userId
     * @param users  the users
     * @return the users list
     */
    @Transactional
    public UsersList updateUsers(String userId, Users users) {
        List<Users> updatedUsers = new ArrayList<>();
        List<Users> userList = userRepository.findAllByUserIdOrderByCreatedDesc(userId);
        for (Users user : userList) {
            user.setPassword(passwordEncoder.encode(users.getPassword()));
            user.setEmail(users.getEmail());
            user.setDescription(users.getDescription());

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
     * Users 단 건 삭제(Delete a User)
     *
     * @param namespace the namespace
     * @param userId    the userId
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
     * @param cluster   the cluster
     * @param namespace the namespace
     * @return the users
     */
    public Users getUsersByNamespaceAndNsAdmin(String cluster, String namespace) {
        return userRepository.findAllByClusterNameAndCpNamespace(cluster, namespace);
    }


    /**
     * 모든 Namespace 중 해당 사용자가 포함된 Users 목록 조회
     *
     * @param cluster the cluster
     * @param userId  the userId
     * @return the users list
     */
    public UsersList getNamespaceListByUserId(String cluster, String userId) {
        String defaultNs = propertyService.getDefaultNamespace();
        List<Users> users = userRepository.findAllByClusterNameAndUserId(cluster, userId, defaultNs);

        UsersList usersList = new UsersList();
        usersList.setItems(users);
        return usersList;
    }


    /**
     * Admin Portal 모든 사용자 목록 조회(Get Users list of admin portal)
     *
     * @param usersSpecification the user specification
     * @param orderBy            the orderBy
     * @param order              the order
     * @return the users list
     */
    public UsersList getUsersListAllByCluster(UsersSpecification usersSpecification, String orderBy, String order) {
        List<Users> result = userRepository.findAll(usersSpecification, userSortDirection(orderBy, order));

        List<Users> distinctUserList = commonService.distinctArray(result);
        List<Users> newResult = new ArrayList<>();
        String created = null;
        for(Users distinctUser : distinctUserList) {
            Users tempUser = userRepository.findByCpNamespaceAndUserId(defaultNamespace, distinctUser.getUserId());
            if(tempUser != null) {
                created = tempUser.getCreated();
            }

            for(Users u : result) {
                if(distinctUser.getUserId().equals(u.getUserId())) {
                    u.setCreated(created);
                    newResult.add(u);
                }
            }
        }

        UsersList usersList = new UsersList();
        usersList.setItems(newResult);

        return (UsersList) commonService.setResultModel(usersList, Constants.RESULT_STATUS_SUCCESS);
    }


    /**
     * CLUSTER_ADMIN 권한을 가진 운영자 상세 조회(Get Cluster Admin's info)
     *
     * @param cluster the cluster
     * @param userId  the user id
     * @return the user detail
     */
    public Users getUsersByClusterNameAndUserIdAndUserType(String cluster, String userId) {
        return userRepository.findByClusterNameAndUserIdAndUserType(cluster, userId);
    }
}
