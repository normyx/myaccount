package org.mgoulene.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.mgoulene.domain.BudgetItemPeriod;
import org.mgoulene.domain.*; // for static metamodels
import org.mgoulene.repository.BudgetItemPeriodRepository;
import org.mgoulene.service.dto.BudgetItemPeriodCriteria;

import org.mgoulene.service.dto.BudgetItemPeriodDTO;
import org.mgoulene.service.mapper.BudgetItemPeriodMapper;

/**
 * Service for executing complex queries for BudgetItemPeriod entities in the database.
 * The main input is a {@link BudgetItemPeriodCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BudgetItemPeriodDTO} or a {@link Page} of {@link BudgetItemPeriodDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BudgetItemPeriodQueryService extends QueryService<BudgetItemPeriod> {

    private final Logger log = LoggerFactory.getLogger(BudgetItemPeriodQueryService.class);

    private final BudgetItemPeriodRepository budgetItemPeriodRepository;

    private final BudgetItemPeriodMapper budgetItemPeriodMapper;

    public BudgetItemPeriodQueryService(BudgetItemPeriodRepository budgetItemPeriodRepository, BudgetItemPeriodMapper budgetItemPeriodMapper) {
        this.budgetItemPeriodRepository = budgetItemPeriodRepository;
        this.budgetItemPeriodMapper = budgetItemPeriodMapper;
    }

    /**
     * Return a {@link List} of {@link BudgetItemPeriodDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BudgetItemPeriodDTO> findByCriteria(BudgetItemPeriodCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BudgetItemPeriod> specification = createSpecification(criteria);
        return budgetItemPeriodMapper.toDto(budgetItemPeriodRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BudgetItemPeriodDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BudgetItemPeriodDTO> findByCriteria(BudgetItemPeriodCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BudgetItemPeriod> specification = createSpecification(criteria);
        return budgetItemPeriodRepository.findAll(specification, page)
            .map(budgetItemPeriodMapper::toDto);
    }

    /**
     * Function to convert BudgetItemPeriodCriteria to a {@link Specification}
     */
    private Specification<BudgetItemPeriod> createSpecification(BudgetItemPeriodCriteria criteria) {
        Specification<BudgetItemPeriod> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BudgetItemPeriod_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), BudgetItemPeriod_.date));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMonth(), BudgetItemPeriod_.month));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), BudgetItemPeriod_.amount));
            }
            if (criteria.getIsSmoothed() != null) {
                specification = specification.and(buildSpecification(criteria.getIsSmoothed(), BudgetItemPeriod_.isSmoothed));
            }
            if (criteria.getIsRecurrent() != null) {
                specification = specification.and(buildSpecification(criteria.getIsRecurrent(), BudgetItemPeriod_.isRecurrent));
            }
            if (criteria.getBudgetItemId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getBudgetItemId(), BudgetItemPeriod_.budgetItem, BudgetItem_.id));
            }
            if (criteria.getOperationId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOperationId(), BudgetItemPeriod_.operation, Operation_.id));
            }
        }
        return specification;
    }

}
