package org.mgoulene.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.mgoulene.domain.BudgetItem;
import org.mgoulene.domain.*; // for static metamodels
import org.mgoulene.repository.BudgetItemRepository;
import org.mgoulene.service.dto.BudgetItemCriteria;
import org.mgoulene.service.dto.BudgetItemDTO;
import org.mgoulene.service.mapper.BudgetItemMapper;

/**
 * Service for executing complex queries for BudgetItem entities in the database.
 * The main input is a {@link BudgetItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BudgetItemDTO} or a {@link Page} of {@link BudgetItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BudgetItemQueryService extends QueryService<BudgetItem> {

    private final Logger log = LoggerFactory.getLogger(BudgetItemQueryService.class);

    private BudgetItemRepository budgetItemRepository;

    private BudgetItemMapper budgetItemMapper;

    public BudgetItemQueryService(BudgetItemRepository budgetItemRepository, BudgetItemMapper budgetItemMapper) {
        this.budgetItemRepository = budgetItemRepository;
        this.budgetItemMapper = budgetItemMapper;
    }

    /**
     * Return a {@link List} of {@link BudgetItemDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BudgetItemDTO> findByCriteria(BudgetItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BudgetItem> specification = createSpecification(criteria);
        return budgetItemMapper.toDto(budgetItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BudgetItemDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BudgetItemDTO> findByCriteria(BudgetItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BudgetItem> specification = createSpecification(criteria);
        return budgetItemRepository.findAll(specification, page)
            .map(budgetItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BudgetItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BudgetItem> specification = createSpecification(criteria);
        return budgetItemRepository.count(specification);
    }

    /**
     * Function to convert BudgetItemCriteria to a {@link Specification}
     */
    private Specification<BudgetItem> createSpecification(BudgetItemCriteria criteria) {
        Specification<BudgetItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BudgetItem_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BudgetItem_.name));
            }
            if (criteria.getOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrder(), BudgetItem_.order));
            }
            if (criteria.getBudgetItemPeriodsId() != null) {
                specification = specification.and(buildSpecification(criteria.getBudgetItemPeriodsId(),
                    root -> root.join(BudgetItem_.budgetItemPeriods, JoinType.LEFT).get(BudgetItemPeriod_.id)));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoryId(),
                    root -> root.join(BudgetItem_.category, JoinType.LEFT).get(Category_.id)));
            }
            if (criteria.getAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getAccountId(),
                    root -> root.join(BudgetItem_.account, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
