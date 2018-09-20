package org.mgoulene.repository;

import org.mgoulene.domain.Operation;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data repository for the Operation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>, JpaSpecificationExecutor<Operation> {

    @Query("select operation from Operation operation where operation.account.login = ?#{principal.username}")
    List<Operation> findByAccountIsCurrentUser();

    @Query("SELECT operation FROM Operation operation where operation.date = :date AND operation.amount = :amount AND operation.label = :label AND operation.account.id = :accountId AND operation.isUpToDate = false")
    List<Operation> findAllByDateAmountLabelAccountAndNotUpToDate(@Param("date") LocalDate date,
            @Param("amount") float amount, @Param("label") String label, @Param("accountId") Long accountId);

    @Modifying
    @Query("UPDATE Operation operation SET operation.isUpToDate = false WHERE operation.account.id = :accountId")
    int updateIsUpToDate(@Param("accountId") Long accountId);

    @Modifying
    @Query("DELETE FROM Operation operation WHERE operation.account.id = :accountId AND operation.isUpToDate = false")
    int deleteIsNotUpToDate(@Param("accountId") Long accountId);

    @Query("SELECT operation FROM Operation operation where operation.account.id = :accountId AND operation.subCategory.category.id = :categoryId AND ABS((operation.amount - :value)/operation.amount) < 0.2 AND operation.date > :dateFrom AND operation.date < :dateTo ")
    List<Operation> findAllCloseToBudgetItemPeriod(@Param("accountId") Long accountId,
            @Param("categoryId") Long categoryId, @Param("value") float value, @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo);

}
