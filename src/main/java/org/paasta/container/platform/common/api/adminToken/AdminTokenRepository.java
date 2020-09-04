package org.paasta.container.platform.common.api.adminToken;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Service Instance JPA Repository 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.08.31
 */
@Repository
public interface AdminTokenRepository extends CrudRepository<AdminToken, String> {

    AdminToken findByTokenName(String tokenName);

}
