package org.mgoulene.service;

import java.time.LocalDate;
import java.util.List;

import org.mgoulene.domain.ReportDateEvolutionData;
import org.mgoulene.domain.ReportMonthlyData;
import org.mgoulene.repository.ReportDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReportDataService {

    private final Logger log = LoggerFactory.getLogger(ReportDataService.class);


    private final ReportDataRepository reportDataRepository; 


    public ReportDataService(ReportDataRepository reportDataRepository) {
        this.reportDataRepository = reportDataRepository;
    }



    public List<ReportMonthlyData> findAllFromCategory(Long accountId, Long categoryId, LocalDate fromDate, LocalDate toDate) {
        log.debug("Request to get all ReportMonthlyData accountId {}, categoryId {}, fromDate: {}, toDate: {}", accountId, categoryId, fromDate, toDate);
        return reportDataRepository.findMonthlyReportDataWhereCategoryBetweenMonth(accountId, categoryId, fromDate, toDate);
    }

    public List<ReportDateEvolutionData> findReportDataWhereMonth(Long accountId, LocalDate month) {
        return reportDataRepository.findReportDataWhereMonth(accountId, month);
        
    }

}
