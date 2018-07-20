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

import org.mgoulene.domain.EvolutionInMonthReport;
import org.mgoulene.domain.*; // for static metamodels
import org.mgoulene.repository.EvolutionInMonthReportRepository;
import org.mgoulene.service.dto.EvolutionInMonthReportCriteria;

import org.mgoulene.service.dto.EvolutionInMonthReportDTO;
import org.mgoulene.service.mapper.EvolutionInMonthReportMapper;

/**
 * Service for executing complex queries for EvolutionInMonthReport entities in the database.
 * The main input is a {@link EvolutionInMonthReportCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EvolutionInMonthReportDTO} or a {@link Page} of {@link EvolutionInMonthReportDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EvolutionInMonthReportQueryService extends QueryService<EvolutionInMonthReport> {

    private final Logger log = LoggerFactory.getLogger(EvolutionInMonthReportQueryService.class);

    private final EvolutionInMonthReportRepository evolutionInMonthReportRepository;

    private final EvolutionInMonthReportMapper evolutionInMonthReportMapper;

    public EvolutionInMonthReportQueryService(EvolutionInMonthReportRepository evolutionInMonthReportRepository, EvolutionInMonthReportMapper evolutionInMonthReportMapper) {
        this.evolutionInMonthReportRepository = evolutionInMonthReportRepository;
        this.evolutionInMonthReportMapper = evolutionInMonthReportMapper;
    }

    /**
     * Return a {@link List} of {@link EvolutionInMonthReportDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EvolutionInMonthReportDTO> findByCriteria(EvolutionInMonthReportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EvolutionInMonthReport> specification = createSpecification(criteria);
        return evolutionInMonthReportMapper.toDto(evolutionInMonthReportRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EvolutionInMonthReportDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EvolutionInMonthReportDTO> findByCriteria(EvolutionInMonthReportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EvolutionInMonthReport> specification = createSpecification(criteria);
        return evolutionInMonthReportRepository.findAll(specification, page)
            .map(evolutionInMonthReportMapper::toDto);
    }

    /**
     * Function to convert EvolutionInMonthReportCriteria to a {@link Specification}
     */
    private Specification<EvolutionInMonthReport> createSpecification(EvolutionInMonthReportCriteria criteria) {
        Specification<EvolutionInMonthReport> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EvolutionInMonthReport_.id));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMonth(), EvolutionInMonthReport_.month));
            }
            if (criteria.getOperation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOperation(), EvolutionInMonthReport_.operation));
            }
            if (criteria.getBudget() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBudget(), EvolutionInMonthReport_.budget));
            }
            if (criteria.getAccountId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAccountId(), EvolutionInMonthReport_.account, User_.id));
            }
        }
        return specification;
    }

}
