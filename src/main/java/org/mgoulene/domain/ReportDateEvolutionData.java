package org.mgoulene.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class ReportDateEvolutionData implements Serializable {

    private static final long serialVersionUID = 1L;

    public ReportDateEvolutionData(LocalDate date, LocalDate month, Long categoryId,
            String categoryName, Boolean hasOperation, Float operationAmount, Float budgetSmoothedAmount,
            Float budgetUnSmoothedMarkedAmount, Float budgetUnSmoothedUnMarkedAmount) {
        this.date = date;
        this.month = month;
        this.categoryId = categoryId;
        this.hasOperation = hasOperation;
        this.operationAmount = operationAmount;
        this.budgetSmoothedAmount = budgetSmoothedAmount;
        this.budgetUnSmoothedUnMarkedAmount = budgetUnSmoothedUnMarkedAmount;
        this.budgetUnSmoothedMarkedAmount = budgetUnSmoothedMarkedAmount;
        this.categoryName = categoryName;
    }


    private LocalDate month;

    private LocalDate date;

    private Long categoryId;

    private String categoryName;

    private Boolean hasOperation;

    private Float operationAmount;

    private Float budgetSmoothedAmount;

    private Float budgetUnSmoothedUnMarkedAmount;

    private Float budgetUnSmoothedMarkedAmount;


    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isHasOperation() {
        return hasOperation;
    }

    public void setHasOperation(boolean hasOperation) {
        this.hasOperation = hasOperation;
    }

    public Float getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(Float operationAmount) {
        this.operationAmount = operationAmount;
    }

    public Float getBudgetSmoothedAmount() {
        return budgetSmoothedAmount;
    }

    public void setBudgetSmoothedAmount(Float budgetSmoothedAmount) {
        this.budgetSmoothedAmount = budgetSmoothedAmount;
    }

    public Float getbudgetUnSmoothedUnMarkedAmount() {
        return budgetUnSmoothedUnMarkedAmount;
    }

    public void setbudgetUnSmoothedUnMarkedAmount(Float budgetUnSmoothedUnMarkedAmount) {
        this.budgetUnSmoothedUnMarkedAmount = budgetUnSmoothedUnMarkedAmount;
    }

    public Float getbudgetUnSmoothedMarkedAmount() {
        return budgetUnSmoothedMarkedAmount;
    }

    public void setbudgetUnSmoothedMarkedAmount(Float budgetUnSmoothedMarkedAmount) {
        this.budgetUnSmoothedMarkedAmount = budgetUnSmoothedMarkedAmount;
    }


    @Override
    public String toString() {
        return "ReportDateEvolutionData{"  + "}";
    }
}
