package org.mgoulene.web.rest.vm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportDataMonthly {
    private Long accountId;
    private LocalDate month;
    private List<LocalDate> dates;
    private List<Float> operationAmounts;
    private List<Float> budgetAmounts;
    private List<Float> predictiveBudgetAmounts;

    public ReportDataMonthly(Long accountId, LocalDate month) {
        this.setAccountId(accountId);
        this.month = month;

        setDates(new ArrayList<LocalDate>());
        setOperationAmounts(new ArrayList<Float>());
        setBudgetAmounts(new ArrayList<Float>());
        setPredictiveBudgetAmounts(new ArrayList<Float>());
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    public List<LocalDate> getDates() {
        return dates;
    }

    public void setDates(List<LocalDate> dates) {
        this.dates = dates;
    }

    public ReportDataMonthly addDate(LocalDate date) {
        this.getDates().add(date);
        return this;
    }

    public List<Float> getOperationAmounts() {
        return operationAmounts;
    }

    public void setOperationAmounts(List<Float> operationAmounts) {
        this.operationAmounts = operationAmounts;
    }

    public ReportDataMonthly addOperationAmounts(Float operationAmount) {
        this.getOperationAmounts().add(operationAmount);
        return this;
    }

    public List<Float> getBudgetAmounts() {
        return budgetAmounts;
    }

    public void setBudgetAmounts(List<Float> budgetAmounts) {
        this.budgetAmounts = budgetAmounts;
    }

    public ReportDataMonthly addBudgetAmount(Float amount) {
        this.getBudgetAmounts().add(amount);
        return this;
    }

    public List<Float> getPredictiveBudgetAmounts() {
        return predictiveBudgetAmounts;
    }

    public void setPredictiveBudgetAmounts(List<Float> predictiveBudgetAmounts) {
        this.predictiveBudgetAmounts = predictiveBudgetAmounts;
    }

    public ReportDataMonthly addPredictiveBudgetAmount(Float amount) {
        this.getPredictiveBudgetAmounts().add(amount);
        return this;
    }
}
