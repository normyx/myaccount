package org.mgoulene.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of BudgetItemSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class BudgetItemSearchRepositoryMockConfiguration {

    @MockBean
    private BudgetItemSearchRepository mockBudgetItemSearchRepository;

}
