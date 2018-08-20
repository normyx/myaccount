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

import org.mgoulene.domain.ReportDataByDate;
import org.mgoulene.domain.*; // for static metamodels
import org.mgoulene.repository.ReportDataByDateRepository;
import org.mgoulene.service.dto.ReportDataByDateCriteria;

import org.mgoulene.service.dto.ReportDataByDateDTO;
import org.mgoulene.service.mapper.ReportDataByDateMapper;

/**
 * Service for executing complex queries for ReportDataByDate entities in the database.
 * The main input is a {@link ReportDataByDateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReportDataByDateDTO} or a {@link Page} of {@link ReportDataByDateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReportDataByDateQueryService extends QueryService<ReportDataByDate> {

    private final Logger log = LoggerFactory.getLogger(ReportDataByDateQueryService.class);

    private final ReportDataByDateRepository reportDataByDateRepository;

    private final ReportDataByDateMapper reportDataByDateMapper;

    public ReportDataByDateQueryService(ReportDataByDateRepository reportDataByDateRepository, ReportDataByDateMapper reportDataByDateMapper) {
        this.reportDataByDateRepository = reportDataByDateRepository;
        this.reportDataByDateMapper = reportDataByDateMapper;
    }

    /**
     * Return a {@link List} of {@link ReportDataByDateDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReportDataByDateDTO> findByCriteria(ReportDataByDateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReportDataByDate> specification = createSpecification(criteria);
        return reportDataByDateMapper.toDto(reportDataByDateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReportDataByDateDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportDataByDateDTO> findByCriteria(ReportDataByDateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReportDataByDate> specification = createSpecification(criteria);
        return reportDataByDateRepository.findAll(specification, page)
            .map(reportDataByDateMapper::toDto);
    }

    /**
     * Function to convert ReportDataByDateCriteria to a {@link Specification}
     */
    private Specification<ReportDataByDate> createSpecification(ReportDataByDateCriteria criteria) {
        Specification<ReportDataByDate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ReportDataByDate_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), ReportDataByDate_.date));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMonth(), ReportDataByDate_.month));
            }
            if (criteria.getHasOperation() != null) {
                specification = specification.and(buildSpecification(criteria.getHasOperation(), ReportDataByDate_.hasOperation));
            }
            if (criteria.getOperationAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOperationAmount(), ReportDataByDate_.operationAmount));
            }
            if (criteria.getBudgetSmoothedAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBudgetSmoothedAmount(), ReportDataByDate_.budgetSmoothedAmount));
            }
            if (criteria.getBudgetUnsmoothedMarkedAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBudgetUnsmoothedMarkedAmount(), ReportDataByDate_.budgetUnsmoothedMarkedAmount));
            }
            if (criteria.getBudgetUnsmoothedUnmarkedAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBudgetUnsmoothedUnmarkedAmount(), ReportDataByDate_.budgetUnsmoothedUnmarkedAmount));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCategoryId(), ReportDataByDate_.category, Category_.id));
            }
            if (criteria.getAccountId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAccountId(), ReportDataByDate_.account, User_.id));
            }
        }
        return specification;
    }

}
