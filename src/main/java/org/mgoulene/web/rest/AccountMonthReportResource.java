package org.mgoulene.web.rest;


import com.codahale.metrics.annotation.Timed;
import org.mgoulene.domain.AccountMonthReport;
import org.mgoulene.service.AccountMonthReportService;
import org.mgoulene.web.rest.vm.AccountMonthReportData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountMonthReportResource {

    private static final String ENTITY_NAME = "accountMonthReport";
    private final Logger log = LoggerFactory.getLogger(AccountMonthReportResource.class);
    private final AccountMonthReportService accountMonthReportService;


    public AccountMonthReportResource(AccountMonthReportService accountMonthReportService) {
        this.accountMonthReportService = accountMonthReportService;
    }


    /**
     * GET /budget-items : get all the budgetItems.
     *
     * @param categoryId the categoryID which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of budgetItems
     * in body
     */
    @GetMapping("/account-month-report/{categoryId}")
    @Timed
    public ResponseEntity<AccountMonthReportData> getAllFromCategory(@PathVariable Long categoryId) {
        log.debug("REST request to get AccountMonthReport from categoryId: {}", categoryId);
        List<AccountMonthReport> entityList = accountMonthReportService.findAllFromCategory(categoryId, LocalDate.now().minusYears(1), LocalDate.now());
        AccountMonthReportData data = null;
        if (entityList.size() != 0) {
            AccountMonthReport first = entityList.get(0);
            data = new AccountMonthReportData(first.getAccount().getId(), first.getCategory().getId(), first.getCategory().getCategoryName());
            for (AccountMonthReport report : entityList) {
                data.addMonth(report.getMonth()).addAmount(report.getAmount()).addAmountAvg3(report.getAmountAvg3()).addAmountAvg12(report.getAmountAvg12()).addBudgetAmount(report.getBudgetAmount());
            }
        }
        return ResponseEntity.ok().body(data);
    }

}
