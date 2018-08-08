package org.mgoulene.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mgoulene.domain.ReportDateEvolutionData;
import org.mgoulene.domain.ReportMonthlyData;
import org.mgoulene.service.ReportDataService;
import org.mgoulene.web.rest.vm.AccountMonthReportData;
import org.mgoulene.web.rest.vm.ReportDataMonthly;
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
public class ReportDataResource {
    private static final String ENTITY_NAME = "reportData";
    private final Logger log = LoggerFactory.getLogger(ReportDataResource.class);
    private final ReportDataService reportDataService;


    public ReportDataResource(ReportDataService reportDataService) {
        this.reportDataService = reportDataService;
    }


    /**
     * GET /budget-items : get all the budgetItems.
     *
     * @param categoryId the categoryID which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of budgetItems
     * in body
     */
    @GetMapping("/report-data-by-month/{accountId}/{month}")
    @Timed
    public ResponseEntity<ReportDataMonthly> findReportDataByDateWhereAccountIdMonth(@PathVariable(name = "accountId") Long accountId, @PathVariable(name = "month") LocalDate month) {
        log.debug("REST request to get ReportDataResource from accountId: {}", accountId);
        List<ReportDateEvolutionData> data = reportDataService.findReportDataByDateWhereAccountIdMonth(accountId, month);
        ReportDataMonthly reportDataMonthly = new ReportDataMonthly(accountId, month);
        float cumulOperationAmount = 0;
        float cumulBudgetAmount = 0;

        //float cumulPredictiveBudgetAmount = 0;
        for (int i = 0; i < data.size(); i++) {
            ReportDateEvolutionData rd = data.get(i);
            float operationAmount = rd.getOperationAmount() == null ? 0 : rd.getOperationAmount();
            float budgetSAmount = rd.getBudgetSmoothedAmount() == null ? 0 : rd.getBudgetSmoothedAmount();
            float budgetNSAmount = rd.getBudgetNotSmoothedAmount() == null ? 0 : rd.getBudgetNotSmoothedAmount();
            cumulBudgetAmount += budgetSAmount + budgetNSAmount;
            reportDataMonthly.addDate(rd.getDate()).addBudgetAmount(cumulBudgetAmount);
            if (rd.isHasOperation()) {
                cumulOperationAmount += operationAmount;
                reportDataMonthly.addOperationAmounts(cumulOperationAmount).addPredictiveBudgetAmount(null);
            } else {
                cumulOperationAmount += budgetSAmount + budgetNSAmount;
                reportDataMonthly.addOperationAmounts(null).addPredictiveBudgetAmount(cumulOperationAmount);
            }
        }
        return ResponseEntity.ok().body(reportDataMonthly);
    }

    @GetMapping("/report-monthly-data/{accountId}/{categoryId}")
    @Timed
    public ResponseEntity<AccountMonthReportData> findAllFromCategory(@PathVariable(name = "accountId") Long accountId, @PathVariable(name = "categoryId") Long categoryId) {
        log.debug("REST request to get AccountMonthReport from categoryId: {}", categoryId);
        List<ReportMonthlyData> entityList = reportDataService.findAllFromCategory(accountId, categoryId, LocalDate.now().minusYears(1), LocalDate.now());
        AccountMonthReportData data = null;
        if (entityList.size() != 0) {
            ReportMonthlyData first = entityList.get(0);
            data = new AccountMonthReportData(first.getAccountId(), first.getCategoryId(), first.getCategoryName());
            for (ReportMonthlyData report : entityList) {
                data.addMonth(report.getMonth()).addAmount(report.getAmount()).addAmountAvg3(report.getAmountAvg3()).addAmountAvg12(report.getAmountAvg12()).addBudgetAmount(report.getBudgetAmount());
            }
        }
        return ResponseEntity.ok().body(data);
    }
}
