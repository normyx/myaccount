package org.mgoulene.web.rest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.codahale.metrics.annotation.Timed;

import org.mgoulene.domain.ReportDateEvolutionData;
import org.mgoulene.domain.ReportMonthlyData;
import org.mgoulene.domain.User;
import org.mgoulene.service.ReportDataService;
import org.mgoulene.service.UserService;
import org.mgoulene.web.rest.vm.AccountMonthReportData;
import org.mgoulene.web.rest.vm.ReportDataMonthly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ReportDataResource {
    private final Logger log = LoggerFactory.getLogger(ReportDataResource.class);
    private final ReportDataService reportDataService;
    private final UserService userService;

    public ReportDataResource(ReportDataService reportDataService, UserService userService) {
        this.reportDataService = reportDataService;
        this.userService = userService;
    }

    /**
     * GET /budget-items : get all the budgetItems.
     *
     * @param categoryId the categoryID which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of budgetItems
     *         in body
     */
    @GetMapping("/report-amount-global-per-day-in-month/{month}")
    @Timed
    public ResponseEntity<ReportDataMonthly> findReportDataByDateWhereAccountIdMonth(
            @PathVariable(name = "month") LocalDate month) {
        log.debug("REST request to get ReportDataResource from month: {}", month);
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (userOptional.isPresent()) {
            Long accountId = userOptional.get().getId();
            List<ReportDateEvolutionData> data = reportDataService.findReportDataWhereMonth(accountId, month);
            ReportDataMonthly reportDataMonthly = new ReportDataMonthly(null, month);
            float cumulOperationAmount = 0;
            float cumulBudgetAmount = 0;
            for (int i = 0; i < data.size(); i++) {
                ReportDateEvolutionData rd = data.get(i);
                float operationAmount = rd.getOperationAmount() == null ? 0 : rd.getOperationAmount();
                float budgetSAmount = rd.getBudgetSmoothedAmount() == null ? 0 : rd.getBudgetSmoothedAmount();
                float budgetUSUMAmount = rd.getbudgetUnSmoothedUnMarkedAmount() == null ? 0
                        : rd.getbudgetUnSmoothedUnMarkedAmount();
                float budgetUSMAmount = rd.getbudgetUnSmoothedMarkedAmount() == null ? 0
                        : rd.getbudgetUnSmoothedMarkedAmount();
                cumulBudgetAmount += budgetSAmount + budgetUSUMAmount + budgetUSMAmount;
                reportDataMonthly.addDate(rd.getDate()).addBudgetAmount(cumulBudgetAmount);
                if (rd.isHasOperation()) {
                    cumulOperationAmount += operationAmount;
                    reportDataMonthly.addOperationAmounts(cumulOperationAmount).addPredictiveBudgetAmount(null);
                } else {
                    cumulOperationAmount += budgetSAmount + budgetUSUMAmount + budgetUSMAmount;
                    reportDataMonthly.addOperationAmounts(null).addPredictiveBudgetAmount(cumulOperationAmount);
                }
            }
            return ResponseEntity.ok().body(reportDataMonthly);
        } else {
            log.error("REST request error to get User");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/report-amount-category-per-month/{categoryId}/{monthFrom}/{monthTo}")
    @Timed
    public ResponseEntity<AccountMonthReportData> findAllFromCategory(
            @PathVariable(name = "categoryId") Long categoryId, @PathVariable(name = "monthFrom") LocalDate monthFrom,
            @PathVariable(name = "monthTo") LocalDate monthTo) {
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (userOptional.isPresent()) {
            Long accountId = userOptional.get().getId();
            log.debug("REST request to get AccountMonthReport from categoryId: {}", categoryId);
            List<ReportMonthlyData> entityList = reportDataService.findAllFromCategory(accountId, categoryId, monthFrom,
                    monthTo);
            AccountMonthReportData data = null;
            if (!entityList.isEmpty()) {
                ReportMonthlyData first = entityList.get(0);
                data = new AccountMonthReportData(first.getAccountId(), first.getCategoryId(), first.getCategoryName());
                for (ReportMonthlyData report : entityList) {
                    data.addMonth(report.getMonth()).addAmount(report.getAmount()).addAmountAvg3(report.getAmountAvg3())
                            .addAmountAvg12(report.getAmountAvg12()).addBudgetAmount(report.getBudgetAmount());
                }
            }
            return ResponseEntity.ok().body(data);
        } else {
            log.error("REST request error to get User");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/report-amount-with-marked-category-per-month/{categoryId}/{monthFrom}/{monthTo}")
    @Timed
    public ResponseEntity<ReportDataMonthly> findReportPerMonthWithCategoryWithMarked(
            @PathVariable(name = "categoryId") Long categoryId, @PathVariable(name = "monthFrom") LocalDate monthFrom,
            @PathVariable(name = "monthTo") LocalDate monthTo) {
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (userOptional.isPresent()) {
            Long accountId = userOptional.get().getId();
            log.debug("REST request to get AccountMonthReport from categoryId: {}", categoryId);
            List<ReportDateEvolutionData> entityList = reportDataService
                    .findMonthlyReportDataWhereCategoryBetweenMonthWithUnmarked(accountId, categoryId, monthFrom,
                            monthTo);
            ReportDataMonthly data = null;
            if (!entityList.isEmpty()) {
                ReportDateEvolutionData first = entityList.get(0);
                data = new ReportDataMonthly(first.getCategoryId());
                for (ReportDateEvolutionData report : entityList) {
                    data.addMonth(report.getMonth()).addBudgetSmoothedAmounts(report.getBudgetSmoothedAmount())
                            .addBudgetUnSmoothedMarkedAmounts(report.getbudgetUnSmoothedMarkedAmount())
                            .addBudgetUnSmoothedUnMarkedAmounts(report.getbudgetUnSmoothedUnMarkedAmount());
                }
            }
            return ResponseEntity.ok().body(data);
        } else {
            log.error("REST request error to get User");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    

}
