package org.paasta.container.platform.common.api.users;

import org.paasta.container.platform.common.api.common.Constants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User Repository 인터페이스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.09.22
 */
@Repository
@Transactional
public interface UsersRepository extends JpaRepository<Users, Long> {

    //SELECT user_id FROM `cp_users`;
    @Query(value="SELECT user_id " + "FROM cp_users\n", nativeQuery=true)
    List<String> getUsersNameList();

    //SELECT user_id FROM `cp_users`;
    @Query(value="SELECT user_id FROM cp_users WHERE cp_namespace = :namespace", nativeQuery=true)
    List<String> getUsersNameListByCpNamespaceOrderByCreatedDesc(@Param("namespace") String namespace);

    List<Users> findAllByCpNamespaceOrderByCreatedDesc(String namespace);

    List<Users> findAllByUserIdOrderByCreatedDesc(String userId);

    @Query(value="SELECT * FROM cp_users WHERE user_id = :userId AND user_type !='"+Constants.AUTH_CLUSTER_ADMIN+ "'limit 1;", nativeQuery=true)
    Users getOneUsersDetailByUserId(@Param("userId") String userId);

    @Query(value="SELECT * FROM cp_users WHERE user_id = :userId AND user_type ='"+Constants.AUTH_CLUSTER_ADMIN+ "'limit 1;", nativeQuery=true)
    Users getOneUsersDetailByUserIdForAdmin(@Param("userId") String userId);

    @Query(value = "select cp_namespace" +
            "       , user_id AS userId" +
            "       , service_account_name AS serviceAccountName" +
            "       , role_set_code AS roleSetCode" +
            "       , user_type AS userType" +
            "       , created AS created" +
            "       , (select case when count(user_id) > 0 " +
            "                      then 'Y'" +
            "                      else 'N' end " +
            "from cp_users " +
            "where cp_namespace = :namespace" +
            "             and user_id = cu.user_id) as display_yn" +
            "  from cp_users cu" +
            " where id in (select id" +
            "                FROM cp_users cu" +
            "               where cp_namespace = :namespace" +
            "               UNION all" +
            "              SELECT max(id) AS id" +
            "                FROM cp_users cu" +
            "               WHERE NOT EXISTS (SELECT '1'" +
            "                                   FROM cp_users a" +
            "                                  WHERE cp_namespace = :namespace" +
            "                                    AND cu.user_id = a.user_id)" +
            "               GROUP BY user_id) order by created desc;", nativeQuery = true)
    List<Object[]> findAllUsers(@Param("namespace") String namespace);


    Users findByCpNamespaceAndUserId(String namespace, String userId);

    void deleteByCpNamespaceAndUserId(String namespace, String userId);

    @Query(value="SELECT * FROM cp_users WHERE cp_namespace = :namespace AND user_type ='"+Constants.AUTH_NAMESPACE_ADMIN+ "'limit 1;", nativeQuery=true)
    Users findByCpNamespaceAndUserType(String namespace);

    @Query(value="SELECT * FROM cp_users WHERE cluster_name = :cluster AND user_id = :userId AND cp_namespace NOT IN ('" + Constants.DEFAULT_NAMESPACE_NAME + "')", nativeQuery=true)
    List<Users> findAllByClusterNameAndUserId(@Param("cluster") String cluster, @Param("userId") String userId);
}
