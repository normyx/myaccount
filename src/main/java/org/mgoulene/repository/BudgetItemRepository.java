package org.mgoulene.repository;

import org.mgoulene.domain.BudgetItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the BudgetItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BudgetItemRepository extends JpaRepository<BudgetItem, Long>, JpaSpecificationExecutor<BudgetItem> {

    @Query("select budget_item from BudgetItem budget_item where budget_item.account.login = ?#{principal.username}")
    List<BudgetItem> findByAccountIsCurrentUser();

}
