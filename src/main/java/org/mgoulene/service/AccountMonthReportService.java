package org.mgoulene.service;

import org.mgoulene.domain.AccountMonthReport;
import org.mgoulene.repository.AccountMonthReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AccountMonthReportService {

    private final Logger log = LoggerFactory.getLogger(AccountMonthReportService.class);

    private final AccountMonthReportRepository accountMonthReportRepository;

    public AccountMonthReportService(AccountMonthReportRepository accountMonthReportRepository) {
        this.accountMonthReportRepository = accountMonthReportRepository;
    }


    /**
     * TODO
     *
     * @param categoryId the categoryId
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AccountMonthReport> findAllFromCategory(Long categoryId, LocalDate dateFrom, LocalDate dateTo) {
        log.debug("Request to get all AccountMonthReport from category {}", categoryId);
        return accountMonthReportRepository.findAllFromCategory(categoryId, dateFrom, dateTo);

    }
}
