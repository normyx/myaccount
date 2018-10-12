package org.mgoulene.repository;

import org.mgoulene.domain.BudgetItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data  repository for the BudgetItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BudgetItemRepository extends JpaRepository<BudgetItem, Long>, JpaSpecificationExecutor<BudgetItem> {

    @Query("select budget_item from BudgetItem budget_item where budget_item.account.login = ?#{principal.username}")
    List<BudgetItem> findByAccountIsCurrentUser();

    @Query("SELECT bi FROM BudgetItem as bi inner join bi.budgetItemPeriods as bip WHERE bi.account.login = ?#{principal.username} AND bip.month <= :monthTo AND bip.month >= :monthFrom GROUP BY bi HAVING count(bip) >0 ORDER BY bi.order ASC") 
    List<BudgetItem> findAllAvailableInPeriod(@Param("monthFrom") LocalDate monthFrom, @Param("monthTo") LocalDate monthTo); 

    @Query("SELECT bi FROM BudgetItem as bi WHERE bi.account.id = :accountId AND bi.order = (SELECT MAX(bi2.order) FROM BudgetItem bi2 WHERE bi2.order < :order AND bi2.account.id = :accountId)") 
    List<BudgetItem> findPreviousOrderBudgetItem(@Param("accountId") Long accountId, @Param("order") Integer order);

    @Query("SELECT bi FROM BudgetItem as bi WHERE bi.account.id = :accountId AND bi.order = (SELECT MIN(bi2.order) FROM BudgetItem bi2 WHERE bi2.order > :order AND bi2.account.id = :accountId)") 
    List<BudgetItem> findNextOrderBudgetItem(@Param("accountId") Long accountId, @Param("order") Integer order);

    @Query("SELECT MAX(bi.order) + 1 FROM BudgetItem as bi WHERE bi.account.id = :accountId") 
    Integer findNewOrder(@Param("accountId") Long accountId);

}
