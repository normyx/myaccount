package org.mgoulene.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the ReportDataByDate entity. This class is used in ReportDataByDateResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /report-data-by-dates?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReportDataByDateCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LocalDateFilter date;

    private LocalDateFilter month;

    private BooleanFilter hasOperation;

    private FloatFilter operationAmount;

    private FloatFilter budgetSmoothedAmount;

    private FloatFilter budgetUnsmoothedMarkedAmount;

    private FloatFilter budgetUnsmoothedUnmarkedAmount;

    private LongFilter categoryId;

    private LongFilter accountId;

    public ReportDataByDateCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public LocalDateFilter getMonth() {
        return month;
    }

    public void setMonth(LocalDateFilter month) {
        this.month = month;
    }

    public BooleanFilter getHasOperation() {
        return hasOperation;
    }

    public void setHasOperation(BooleanFilter hasOperation) {
        this.hasOperation = hasOperation;
    }

    public FloatFilter getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(FloatFilter operationAmount) {
        this.operationAmount = operationAmount;
    }

    public FloatFilter getBudgetSmoothedAmount() {
        return budgetSmoothedAmount;
    }

    public void setBudgetSmoothedAmount(FloatFilter budgetSmoothedAmount) {
        this.budgetSmoothedAmount = budgetSmoothedAmount;
    }

    public FloatFilter getBudgetUnsmoothedMarkedAmount() {
        return budgetUnsmoothedMarkedAmount;
    }

    public void setBudgetUnsmoothedMarkedAmount(FloatFilter budgetUnsmoothedMarkedAmount) {
        this.budgetUnsmoothedMarkedAmount = budgetUnsmoothedMarkedAmount;
    }

    public FloatFilter getBudgetUnsmoothedUnmarkedAmount() {
        return budgetUnsmoothedUnmarkedAmount;
    }

    public void setBudgetUnsmoothedUnmarkedAmount(FloatFilter budgetUnsmoothedUnmarkedAmount) {
        this.budgetUnsmoothedUnmarkedAmount = budgetUnsmoothedUnmarkedAmount;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getAccountId() {
        return accountId;
    }

    public void setAccountId(LongFilter accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "ReportDataByDateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (month != null ? "month=" + month + ", " : "") +
                (hasOperation != null ? "hasOperation=" + hasOperation + ", " : "") +
                (operationAmount != null ? "operationAmount=" + operationAmount + ", " : "") +
                (budgetSmoothedAmount != null ? "budgetSmoothedAmount=" + budgetSmoothedAmount + ", " : "") +
                (budgetUnsmoothedMarkedAmount != null ? "budgetUnsmoothedMarkedAmount=" + budgetUnsmoothedMarkedAmount + ", " : "") +
                (budgetUnsmoothedUnmarkedAmount != null ? "budgetUnsmoothedUnmarkedAmount=" + budgetUnsmoothedUnmarkedAmount + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (accountId != null ? "accountId=" + accountId + ", " : "") +
            "}";
    }

}
