package org.mgoulene.repository;

import java.time.LocalDate;
import java.util.List;

import org.mgoulene.domain.BudgetItem;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface CustomizedBudgetItemRepository {
    List<BudgetItem> findAllAvailableInPeriod(LocalDate monthFrom, LocalDate monthTo, Long accountId, String contains, Long categoryId); 
}