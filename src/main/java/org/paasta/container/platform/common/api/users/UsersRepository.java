package org.paasta.container.platform.common.api.users;

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
    List<String> getUsersNameListByCpNamespace(@Param("namespace") String namespace);

    List<Users> findAllByCpNamespace(String namespace);

    Users findByUserId(String userId);
}
