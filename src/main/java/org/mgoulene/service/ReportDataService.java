package org.mgoulene.service;

import org.mgoulene.domain.ReportData;
import org.mgoulene.repository.ReportDataRepository;
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

    public ReportDataService(ReportDataRepository reportDataRepository) {
        this.reportDataRepository = reportDataRepository;
    }


    /**
     * get all the Report Data of a month for a given accountId.
     *
     * @return the list of entities
     */
    public List<ReportData> findReportDataByDateWhereAccountIdMonth(Long accountId, LocalDate month) {
        log.debug("Request to get all ReportData accountId {}, month: {}", accountId, month);
        return reportDataRepository.findReportDataByDateWhereAccountIdMonth(accountId, month);
    }
}
