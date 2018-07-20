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

import org.mgoulene.domain.MonthlyReport;
import org.mgoulene.domain.*; // for static metamodels
import org.mgoulene.repository.MonthlyReportRepository;
import org.mgoulene.service.dto.MonthlyReportCriteria;

import org.mgoulene.service.dto.MonthlyReportDTO;
import org.mgoulene.service.mapper.MonthlyReportMapper;

/**
 * Service for executing complex queries for MonthlyReport entities in the database.
 * The main input is a {@link MonthlyReportCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MonthlyReportDTO} or a {@link Page} of {@link MonthlyReportDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MonthlyReportQueryService extends QueryService<MonthlyReport> {

    private final Logger log = LoggerFactory.getLogger(MonthlyReportQueryService.class);

    private final MonthlyReportRepository monthlyReportRepository;

    private final MonthlyReportMapper monthlyReportMapper;

    public MonthlyReportQueryService(MonthlyReportRepository monthlyReportRepository, MonthlyReportMapper monthlyReportMapper) {
        this.monthlyReportRepository = monthlyReportRepository;
        this.monthlyReportMapper = monthlyReportMapper;
    }

    /**
     * Return a {@link List} of {@link MonthlyReportDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MonthlyReportDTO> findByCriteria(MonthlyReportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MonthlyReport> specification = createSpecification(criteria);
        return monthlyReportMapper.toDto(monthlyReportRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MonthlyReportDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MonthlyReportDTO> findByCriteria(MonthlyReportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MonthlyReport> specification = createSpecification(criteria);
        return monthlyReportRepository.findAll(specification, page)
            .map(monthlyReportMapper::toDto);
    }

    /**
     * Function to convert MonthlyReportCriteria to a {@link Specification}
     */
    private Specification<MonthlyReport> createSpecification(MonthlyReportCriteria criteria) {
        Specification<MonthlyReport> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MonthlyReport_.id));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMonth(), MonthlyReport_.month));
            }
            if (criteria.getMonthValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMonthValue(), MonthlyReport_.monthValue));
            }
            if (criteria.getMonthValueAvg3Months() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMonthValueAvg3Months(), MonthlyReport_.monthValueAvg3Months));
            }
            if (criteria.getMonthValueAvg12Months() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMonthValueAvg12Months(), MonthlyReport_.monthValueAvg12Months));
            }
            if (criteria.getAccountId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAccountId(), MonthlyReport_.account, User_.id));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCategoryId(), MonthlyReport_.category, Category_.id));
            }
        }
        return specification;
    }

}
