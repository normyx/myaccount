package org.mgoulene.repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.mgoulene.domain.BudgetItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomizedBudgetItemRepositoryImpl implements CustomizedBudgetItemRepository {
    private static final String QUERY_SELECT = "SELECT bi FROM BudgetItem as bi inner join bi.budgetItemPeriods as bip ";
    private static final String QUERY_WHERE = "WHERE bi.account.id = :accountId AND bip.month <= :monthTo AND bip.month >= :monthFrom ";
    private static final String QUERY_WHERE_NAME_CONTAINS = "AND bi.name LIKE :nameContains ";
    private static final String QUERY_WHERE_CATEGORY = "AND bi.category.id = :categoryId ";
    private static final String QUERY_GROUP_BY = "GROUP BY bi HAVING count(bip) >0 ORDER BY bi.order ASC";
    private final Logger log = LoggerFactory.getLogger(CustomizedBudgetItemRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    public List<BudgetItem> findAllAvailableInPeriod(LocalDate from, LocalDate to, Long accountId, String contains,
            Long categoryId) {
        log.debug("REST request to get BudgetItem : {} {} {} {}", from, to, contains, categoryId);
        String query = QUERY_SELECT + QUERY_WHERE;
        if (contains != null) {
            query += QUERY_WHERE_NAME_CONTAINS;
        }
        if (categoryId != null) {
            query += QUERY_WHERE_CATEGORY;
        }
        query += QUERY_GROUP_BY;
        TypedQuery<BudgetItem> typedQuery = em.createQuery(query, BudgetItem.class).setParameter("monthFrom", from)
                .setParameter("monthTo", to).setParameter("accountId", accountId);

        if (contains != null) {
            typedQuery.setParameter("nameContains", "%" + contains + "%");
        }
        if (categoryId != null) {
            typedQuery.setParameter("categoryId", categoryId);
        }
        return typedQuery.getResultList();
    }
}