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
 * Criteria class for the EvolutionInMonthReport entity. This class is used in EvolutionInMonthReportResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /evolution-in-month-reports?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EvolutionInMonthReportCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LocalDateFilter month;

    private FloatFilter operation;

    private FloatFilter budget;

    private LongFilter accountId;

    public EvolutionInMonthReportCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getMonth() {
        return month;
    }

    public void setMonth(LocalDateFilter month) {
        this.month = month;
    }

    public FloatFilter getOperation() {
        return operation;
    }

    public void setOperation(FloatFilter operation) {
        this.operation = operation;
    }

    public FloatFilter getBudget() {
        return budget;
    }

    public void setBudget(FloatFilter budget) {
        this.budget = budget;
    }

    public LongFilter getAccountId() {
        return accountId;
    }

    public void setAccountId(LongFilter accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "EvolutionInMonthReportCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (month != null ? "month=" + month + ", " : "") +
                (operation != null ? "operation=" + operation + ", " : "") +
                (budget != null ? "budget=" + budget + ", " : "") +
                (accountId != null ? "accountId=" + accountId + ", " : "") +
            "}";
    }

}
