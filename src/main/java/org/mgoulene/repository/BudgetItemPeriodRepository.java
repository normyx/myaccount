package org.mgoulene.repository;

import java.time.LocalDate;
import java.util.List;

import org.mgoulene.domain.BudgetItemPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the BudgetItemPeriod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BudgetItemPeriodRepository
        extends JpaRepository<BudgetItemPeriod, Long>, JpaSpecificationExecutor<BudgetItemPeriod> {

    @Modifying(clearAutomatically = true)
    @Query("DELETE BudgetItemPeriod bip WHERE bip.budgetItem.id =:budgetItemId AND bip.month >=:month ")
    void deleteWithNext(@Param("budgetItemId") Long budgetItemId, @Param("month") LocalDate month);

    @Query("SELECT bip FROM BudgetItemPeriod bip WHERE bip.budgetItem.id =:budgetItemId AND bip.month = (SELECT MAX(bip2.month) FROM BudgetItemPeriod bip2 WHERE bip2.budgetItem.id =:budgetItemId)")
    BudgetItemPeriod findLastBudgetItemPeriod(@Param("budgetItemId") Long budgetItemId);

    //@Query("SELECT bip FROM BudgetItemPeriod bip WHERE bip.budgetItem.id =:budgetItemId AND bip.order = (SELECT MAX(bip2.order) FROM BudgetItemPeriod bip2 WHERE bip2.budgetItem.id =:budgetItemId AND bip2.order < :order)")
    //List<BudgetItemPeriod> findPreviousOrderBudgetItemPeriod(@Param("budgetItemId") Long budgetItemId, @Param("order") Integer order);
}
