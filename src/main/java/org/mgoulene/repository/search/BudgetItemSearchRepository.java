package org.mgoulene.repository.search;

import org.mgoulene.domain.BudgetItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BudgetItem entity.
 */
public interface BudgetItemSearchRepository extends ElasticsearchRepository<BudgetItem, Long> {
}
