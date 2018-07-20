package org.mgoulene.repository;

import java.time.LocalDate;

import org.mgoulene.domain.BudgetItemPeriod;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BudgetItemPeriod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BudgetItemPeriodRepository extends JpaRepository<BudgetItemPeriod, Long>, JpaSpecificationExecutor<BudgetItemPeriod> {

    @Modifying(clearAutomatically = true) 
    @Query("UPDATE BudgetItemPeriod bip SET bip.isSmoothed =:isSmoothed, bip.amount =:amount WHERE bip.budgetItem.id =:budgetItemId AND bip.month >=:month") 
    void updateWithNext(@Param("isSmoothed") boolean isSmoothed, @Param("amount") Float amount, 
            @Param("budgetItemId") Long budgetItemId, @Param("month") LocalDate month); 
}
