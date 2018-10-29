package org.mgoulene.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the BudgetItemPeriod entity. This class is used in BudgetItemPeriodResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /budget-item-periods?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BudgetItemPeriodCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter date;

    private LocalDateFilter month;

    private FloatFilter amount;

    private BooleanFilter isSmoothed;

    private BooleanFilter isRecurrent;

    private LongFilter budgetItemId;

    private LongFilter operationId;

    public BudgetItemPeriodCriteria() {
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

    public FloatFilter getAmount() {
        return amount;
    }

    public void setAmount(FloatFilter amount) {
        this.amount = amount;
    }

    public BooleanFilter getIsSmoothed() {
        return isSmoothed;
    }

    public void setIsSmoothed(BooleanFilter isSmoothed) {
        this.isSmoothed = isSmoothed;
    }

    public BooleanFilter getIsRecurrent() {
        return isRecurrent;
    }

    public void setIsRecurrent(BooleanFilter isRecurrent) {
        this.isRecurrent = isRecurrent;
    }

    public LongFilter getBudgetItemId() {
        return budgetItemId;
    }

    public void setBudgetItemId(LongFilter budgetItemId) {
        this.budgetItemId = budgetItemId;
    }

    public LongFilter getOperationId() {
        return operationId;
    }

    public void setOperationId(LongFilter operationId) {
        this.operationId = operationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BudgetItemPeriodCriteria that = (BudgetItemPeriodCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(month, that.month) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(isSmoothed, that.isSmoothed) &&
            Objects.equals(isRecurrent, that.isRecurrent) &&
            Objects.equals(budgetItemId, that.budgetItemId) &&
            Objects.equals(operationId, that.operationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        date,
        month,
        amount,
        isSmoothed,
        isRecurrent,
        budgetItemId,
        operationId
        );
    }

    @Override
    public String toString() {
        return "BudgetItemPeriodCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (month != null ? "month=" + month + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (isSmoothed != null ? "isSmoothed=" + isSmoothed + ", " : "") +
                (isRecurrent != null ? "isRecurrent=" + isRecurrent + ", " : "") +
                (budgetItemId != null ? "budgetItemId=" + budgetItemId + ", " : "") +
                (operationId != null ? "operationId=" + operationId + ", " : "") +
            "}";
    }

}
