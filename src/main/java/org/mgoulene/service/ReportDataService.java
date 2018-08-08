package org.mgoulene.service;

import org.mgoulene.domain.ReportDateEvolutionData;
import org.mgoulene.domain.ReportMonthlyData;
import org.mgoulene.repository.ReportDataRepository;
import org.mgoulene.repository.ReportMonthlyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ReportDataService {

    private final Logger log = LoggerFactory.getLogger(ReportDataService.class);

    private final ReportDataRepository reportDataRepository;

    private final ReportMonthlyRepository reportMonthlyRepository;

    public ReportDataService(ReportDataRepository reportDataRepository, ReportMonthlyRepository reportMonthlyRepository) {
        this.reportDataRepository = reportDataRepository;
        this.reportMonthlyRepository = reportMonthlyRepository;
    }


    /**
     * get all the Report Data of a month for a given accountId.
     *
     * @return the list of entities
     */
    public List<ReportDateEvolutionData> findReportDataByDateWhereAccountIdMonth(Long accountId, LocalDate month) {
        log.debug("Request to get all ReportData accountId {}, month: {}", accountId, month);
        return reportDataRepository.findReportDataByDateWhereAccountIdMonth(accountId, month);
    }

    public List<ReportMonthlyData> findAllFromCategory(Long accountId, Long categoryId, LocalDate fromDate, LocalDate toDate) {
        log.debug("Request to get all ReportMonthlyData accountId {}, categoryId {}, fromDate: {}, toDate: {}", accountId, categoryId, fromDate, toDate);
        return reportMonthlyRepository.findAllFromCategory(accountId, categoryId, fromDate, toDate);
    }
}
