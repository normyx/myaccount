package org.mgoulene.domain;


import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class ReportData implements Serializable {

    private static final long serialVersionUID = 1L;

    public ReportData(String id, LocalDate date, LocalDate month, Long accountId, Long categoryId, Boolean hasOperation, Float operationAmount, Float budgetSmoothedAmount, Float budgetNotSmoothedAmount) {
        this.id = id;
        this.date = date;
        this.month = month;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.hasOperation = hasOperation;
        this.operationAmount = operationAmount;
        this.budgetSmoothedAmount = budgetSmoothedAmount;
        this.budgetNotSmoothedAmount = budgetNotSmoothedAmount;
    }
    private String id;

    private LocalDate month;

    private LocalDate date;

    private Long accountId;

    private Long categoryId;

    private Boolean hasOperation;

    private Float operationAmount;

    private Float budgetSmoothedAmount;

    private Float budgetNotSmoothedAmount;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public Float getBudgetNotSmoothedAmount() {
        return budgetNotSmoothedAmount;
    }

    public void setBudgetNotSmoothedAmount(Float budgetNotSmoothedAmount) {
        this.budgetNotSmoothedAmount = budgetNotSmoothedAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReportData rd = (ReportData) o;
        if (rd.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rd.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReportData{" +
            "id=" + getId() + "}";
    }
}
