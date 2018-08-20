package org.mgoulene.service;

import org.mgoulene.domain.ReportDataByDate;
import org.mgoulene.repository.ReportDataByDateRepository;
import org.mgoulene.repository.ReportDataRepository;
import org.mgoulene.service.dto.ReportDataByDateDTO;
import org.mgoulene.service.mapper.ReportDataByDateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ReportDataByDate.
 */
@Service
@Transactional
public class ReportDataByDateService {

    private final Logger log = LoggerFactory.getLogger(ReportDataByDateService.class);

    private final ReportDataByDateRepository reportDataByDateRepository;

    private final ReportDataByDateMapper reportDataByDateMapper;

    private final ReportDataRepository reportDataRepository; 

    public ReportDataByDateService(ReportDataByDateRepository reportDataByDateRepository, ReportDataByDateMapper reportDataByDateMapper, ReportDataRepository reportDataRepository) {
        this.reportDataByDateRepository = reportDataByDateRepository;
        this.reportDataByDateMapper = reportDataByDateMapper;
        this.reportDataRepository = reportDataRepository;
    }

    /**
     * Save a reportDataByDate.
     *
     * @param reportDataByDateDTO the entity to save
     * @return the persisted entity
     */
    public ReportDataByDateDTO save(ReportDataByDateDTO reportDataByDateDTO) {
        log.debug("Request to save ReportDataByDate : {}", reportDataByDateDTO);
        ReportDataByDate reportDataByDate = reportDataByDateMapper.toEntity(reportDataByDateDTO);
        reportDataByDate = reportDataByDateRepository.save(reportDataByDate);
        return reportDataByDateMapper.toDto(reportDataByDate);
    }

    /**
     * Get all the reportDataByDates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ReportDataByDateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReportDataByDates");
        return reportDataByDateRepository.findAll(pageable)
            .map(reportDataByDateMapper::toDto);
    }


    /**
     * Get one reportDataByDate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ReportDataByDateDTO> findOne(Long id) {
        log.debug("Request to get ReportDataByDate : {}", id);
        return reportDataByDateRepository.findById(id)
            .map(reportDataByDateMapper::toDto);
    }

    /**
     * Delete the reportDataByDate by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ReportDataByDate : {}", id);
        reportDataByDateRepository.deleteById(id);
    }

    /**
     * Refresh the Report Data from account
     * @param accountId the account id to use
     */
    public void refreshData(Long accountId) {
        log.debug("Request to refresh ReportDataByDate fro accountId : {}", accountId);
        reportDataRepository.refreshReportData(accountId);
    }
}
