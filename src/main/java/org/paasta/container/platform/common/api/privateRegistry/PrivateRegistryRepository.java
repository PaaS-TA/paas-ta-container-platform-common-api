package org.paasta.container.platform.common.api.privateRegistry;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Private Registry Repository 인터페이스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.12.01
 */
@Repository
public interface PrivateRegistryRepository extends CrudRepository<PrivateRegistry, String> {

    PrivateRegistry findByImageName(String imageName);

}
