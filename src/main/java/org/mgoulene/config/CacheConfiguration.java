package org.mgoulene.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(org.mgoulene.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(org.mgoulene.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(org.mgoulene.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(org.mgoulene.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(org.mgoulene.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(org.mgoulene.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(org.mgoulene.domain.Category.class.getName() + ".subCategories", jcacheConfiguration);
            cm.createCache(org.mgoulene.domain.SubCategory.class.getName(), jcacheConfiguration);
            cm.createCache(org.mgoulene.domain.BudgetItem.class.getName(), jcacheConfiguration);
            cm.createCache(org.mgoulene.domain.BudgetItem.class.getName() + ".budgetItemPeriods", jcacheConfiguration);
            cm.createCache(org.mgoulene.domain.Operation.class.getName(), jcacheConfiguration);
            cm.createCache(org.mgoulene.domain.BudgetItemPeriod.class.getName(), jcacheConfiguration);
            cm.createCache(org.mgoulene.domain.BankAccount.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
