package org.mgoulene.repository;

import org.mgoulene.domain.Operation;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data  repository for the Operation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>, JpaSpecificationExecutor<Operation> {

    @Query("select operation from Operation operation where operation.account.login = ?#{principal.username}")
    List<Operation> findByAccountIsCurrentUser();

    @Query("SELECT operation FROM Operation operation where operation.date = :date AND operation.amount = :amount AND operation.label = :label AND operation.account.login = :login AND operation.isUpToDate = false")
    List<Operation> findAllByDateAmountLabelAccountAndNotUpToDate(@Param("date") LocalDate date, @Param("amount") float amount, @Param("label") String label, @Param("login") String login);

    @Modifying
    @Query("UPDATE Operation operation SET operation.isUpToDate = false WHERE operation.account.id = :accountId")
    int updateIsUpToDate(@Param("accountId") Long accountId);

    @Modifying
    @Query("DELETE FROM Operation operation WHERE operation.account.id = :accountId AND operation.isUpToDate = false")
    int deleteIsNotUpToDate(@Param("accountId") Long accountId);

}
