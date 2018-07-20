package org.mgoulene.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of SubCategorySearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SubCategorySearchRepositoryMockConfiguration {

    @MockBean
    private SubCategorySearchRepository mockSubCategorySearchRepository;

}
