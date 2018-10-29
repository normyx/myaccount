package org.mgoulene.repository;

import org.mgoulene.domain.Operation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Operation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>, JpaSpecificationExecutor<Operation> {

    @Query("select operation from Operation operation where operation.account.login = ?#{principal.username}")
    List<Operation> findByAccountIsCurrentUser();

}
