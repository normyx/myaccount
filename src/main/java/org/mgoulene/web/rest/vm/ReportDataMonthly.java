package org.mgoulene.web.rest.vm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportDataMonthly {
    private Long accountId;
    private LocalDate month;
    private Long categoryId;
    private List<LocalDate> dates;
    private List<LocalDate> months;
    private List<Float> operationAmounts;
    private List<Float> budgetAmounts;
    private List<Float> predictiveBudgetAmounts;
    private List<Float> budgetSmoothedAmounts;
    private List<Float> budgetUnSmoothedUnMarkedAmounts;
    private List<Float> budgetUnSmoothedMarkedAmounts;
    private List<Float> budgetAtDateAmounts;

    public ReportDataMonthly(Long accountId, LocalDate month) {
        this.setAccountId(accountId);
        this.month = month;
        this.init();

    }


    public ReportDataMonthly(Long categoryId) {
        this.setCategoryId(categoryId);
        this.init();

    }


    private void init() {
        setDates(new ArrayList<LocalDate>());
        setOperationAmounts(new ArrayList<Float>());
        setBudgetAmounts(new ArrayList<Float>());
        setPredictiveBudgetAmounts(new ArrayList<Float>());
        setBudgetSmoothedAmounts(new ArrayList<Float>());
        setBudgetUnSmoothedMarkedAmounts(new ArrayList<Float>());
        setBudgetUnSmoothedUnMarkedAmounts(new ArrayList<Float>());
        setBudgetAtDateAmounts(new ArrayList<Float>());
        setMonths(new ArrayList<LocalDate>());
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

    /**
     * @return the budgetSmoothedAmounts
     */
    public List<Float> getBudgetSmoothedAmounts() {
        return budgetSmoothedAmounts;
    }

    /**
     * @param budgetSmoothedAmounts the budgetSmoothedAmounts to set
     */
    public void setBudgetSmoothedAmounts(List<Float> budgetSmoothedAmounts) {
        this.budgetSmoothedAmounts = budgetSmoothedAmounts;
    }

    public ReportDataMonthly addBudgetSmoothedAmounts(Float amount) {
        getBudgetSmoothedAmounts().add(amount == null ? 0 : amount);
        return this;
    }

    /**
     * @return the budgetUnSmoothedUnMarkedAmounts
     */
    public List<Float> getBudgetUnSmoothedUnMarkedAmounts() {
        return budgetUnSmoothedUnMarkedAmounts;
    }

    /**
     * @param budgetUnSmoothedUnMarkedAmounts the budgetUnSmoothedUnMarkedAmounts to
     *                                        set
     */
    public void setBudgetUnSmoothedUnMarkedAmounts(List<Float> budgetUnSmoothedUnMarkedAmounts) {
        this.budgetUnSmoothedUnMarkedAmounts = budgetUnSmoothedUnMarkedAmounts;
    }

    public ReportDataMonthly addBudgetUnSmoothedUnMarkedAmounts(Float amount) {
        getBudgetUnSmoothedUnMarkedAmounts().add(amount == null ? 0 : amount);
        return this;
    }

    /**
     * @return the budgetUnSmoothedMarkedAmounts
     */
    public List<Float> getBudgetUnSmoothedMarkedAmounts() {
        return budgetUnSmoothedMarkedAmounts;
    }

    /**
     * @param budgetUnSmoothedMarkedAmounts the budgetUnSmoothedMarkedAmounts to set
     */
    public void setBudgetUnSmoothedMarkedAmounts(List<Float> budgetUnSmoothedMarkedAmounts) {
        this.budgetUnSmoothedMarkedAmounts = budgetUnSmoothedMarkedAmounts;
    }

    public ReportDataMonthly addBudgetUnSmoothedMarkedAmounts(Float amount) {
        getBudgetUnSmoothedMarkedAmounts().add(amount == null ? 0 : amount);
        return this;
    }

    /**
     * @return the budgetAtDateAmounts
     */
    public List<Float> getBudgetAtDateAmounts() {
        return budgetAtDateAmounts;
    }

    /**
     * @param budgetAtDateAmounts the budgetAtDateAmounts to set
     */
    public void setBudgetAtDateAmounts(List<Float> budgetAtDateAmounts) {
        this.budgetAtDateAmounts = budgetAtDateAmounts;
    }

    public ReportDataMonthly addBudgetAtDateAmounts(Float amount) {
        getBudgetAtDateAmounts().add(amount == null ? 0 : amount);
        return this;
    }

        /**
     * @return the categoryId
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

        /**
     * @return the months
     */
    public List<LocalDate> getMonths() {
        return months;
    }

    /**
     * @param months the months to set
     */
    public void setMonths(List<LocalDate> months) {
        this.months = months;
    }

    public ReportDataMonthly addMonth(LocalDate month) {
        getMonths().add(month);
        return this;
    }
}
