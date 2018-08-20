package org.mgoulene.service;

import org.mgoulene.domain.ReportDataByDate;
import org.mgoulene.domain.ReportDateEvolutionData;
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

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    public void refreshData(Long accountId, List<Long> categoryIds) {
        log.debug("Request to refresh ReportDataByDate for accountId and categoryIds: {}, {}", accountId);
        reportDataRepository.refreshReportData(accountId, categoryIds);
    }

    public List<ReportDateEvolutionData> findByAccountIsCurrentUserAndMonth(LocalDate month) {
        return convertFromQuery(reportDataByDateRepository.findByAccountIsCurrentUserAndMonth(month));
        
    }

    private List<ReportDateEvolutionData> convertFromQuery(List<Object[]> rawResult) {
        return rawResult.stream().map(result -> new ReportDateEvolutionData(
            (LocalDate)result[0],
            (LocalDate)result[1],
            null,
            null,
            (Boolean)result[2],
            result[3] != null ? ((Double)result[3]).floatValue() : null,
            result[4] != null ? ((Double)result[4]).floatValue() : null,
            result[5] != null ? ((Double)result[5]).floatValue() : null,
            result[6] != null ? ((Double)result[6]).floatValue() : null
        )).collect(Collectors.toList());
    }

}
