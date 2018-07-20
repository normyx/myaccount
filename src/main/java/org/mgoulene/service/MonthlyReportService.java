package org.mgoulene.service;

import org.mgoulene.domain.MonthlyReport;
import org.mgoulene.repository.MonthlyReportRepository;
import org.mgoulene.service.dto.MonthlyReportDTO;
import org.mgoulene.service.mapper.MonthlyReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing MonthlyReport.
 */
@Service
@Transactional
public class MonthlyReportService {

    private final Logger log = LoggerFactory.getLogger(MonthlyReportService.class);

    private final MonthlyReportRepository monthlyReportRepository;

    private final MonthlyReportMapper monthlyReportMapper;

    public MonthlyReportService(MonthlyReportRepository monthlyReportRepository, MonthlyReportMapper monthlyReportMapper) {
        this.monthlyReportRepository = monthlyReportRepository;
        this.monthlyReportMapper = monthlyReportMapper;
    }

    /**
     * Save a monthlyReport.
     *
     * @param monthlyReportDTO the entity to save
     * @return the persisted entity
     */
    public MonthlyReportDTO save(MonthlyReportDTO monthlyReportDTO) {
        log.debug("Request to save MonthlyReport : {}", monthlyReportDTO);
        MonthlyReport monthlyReport = monthlyReportMapper.toEntity(monthlyReportDTO);
        monthlyReport = monthlyReportRepository.save(monthlyReport);
        return monthlyReportMapper.toDto(monthlyReport);
    }

    /**
     * Get all the monthlyReports.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MonthlyReportDTO> findAll() {
        log.debug("Request to get all MonthlyReports");
        return monthlyReportRepository.findAll().stream()
            .map(monthlyReportMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one monthlyReport by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MonthlyReportDTO> findOne(Long id) {
        log.debug("Request to get MonthlyReport : {}", id);
        return monthlyReportRepository.findById(id)
            .map(monthlyReportMapper::toDto);
    }

    /**
     * Delete the monthlyReport by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MonthlyReport : {}", id);
        monthlyReportRepository.deleteById(id);
    }
}
