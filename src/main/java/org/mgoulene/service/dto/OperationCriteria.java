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
 * Criteria class for the Operation entity. This class is used in OperationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /operations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OperationCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter label;

    private LocalDateFilter date;

    private FloatFilter amount;

    private StringFilter note;

    private StringFilter checkNumber;

    private BooleanFilter isUpToDate;

    private LongFilter subCategoryId;

    private LongFilter accountId;

    private LongFilter budgetItemId;

    private LongFilter bankAccountId;

    public OperationCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLabel() {
        return label;
    }

    public void setLabel(StringFilter label) {
        this.label = label;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public FloatFilter getAmount() {
        return amount;
    }

    public void setAmount(FloatFilter amount) {
        this.amount = amount;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public StringFilter getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(StringFilter checkNumber) {
        this.checkNumber = checkNumber;
    }

    public BooleanFilter getIsUpToDate() {
        return isUpToDate;
    }

    public void setIsUpToDate(BooleanFilter isUpToDate) {
        this.isUpToDate = isUpToDate;
    }

    public LongFilter getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(LongFilter subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public LongFilter getAccountId() {
        return accountId;
    }

    public void setAccountId(LongFilter accountId) {
        this.accountId = accountId;
    }

    public LongFilter getBudgetItemId() {
        return budgetItemId;
    }

    public void setBudgetItemId(LongFilter budgetItemId) {
        this.budgetItemId = budgetItemId;
    }

    public LongFilter getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(LongFilter bankAccountId) {
        this.bankAccountId = bankAccountId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OperationCriteria that = (OperationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(label, that.label) &&
            Objects.equals(date, that.date) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(note, that.note) &&
            Objects.equals(checkNumber, that.checkNumber) &&
            Objects.equals(isUpToDate, that.isUpToDate) &&
            Objects.equals(subCategoryId, that.subCategoryId) &&
            Objects.equals(accountId, that.accountId) &&
            Objects.equals(budgetItemId, that.budgetItemId) &&
            Objects.equals(bankAccountId, that.bankAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        label,
        date,
        amount,
        note,
        checkNumber,
        isUpToDate,
        subCategoryId,
        accountId,
        budgetItemId,
        bankAccountId
        );
    }

    @Override
    public String toString() {
        return "OperationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (label != null ? "label=" + label + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (checkNumber != null ? "checkNumber=" + checkNumber + ", " : "") +
                (isUpToDate != null ? "isUpToDate=" + isUpToDate + ", " : "") +
                (subCategoryId != null ? "subCategoryId=" + subCategoryId + ", " : "") +
                (accountId != null ? "accountId=" + accountId + ", " : "") +
                (budgetItemId != null ? "budgetItemId=" + budgetItemId + ", " : "") +
                (bankAccountId != null ? "bankAccountId=" + bankAccountId + ", " : "") +
            "}";
    }

}
