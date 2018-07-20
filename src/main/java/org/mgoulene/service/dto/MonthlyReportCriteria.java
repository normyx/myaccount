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
 * Criteria class for the MonthlyReport entity. This class is used in MonthlyReportResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /monthly-reports?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MonthlyReportCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LocalDateFilter month;

    private FloatFilter monthValue;

    private FloatFilter monthValueAvg3Months;

    private FloatFilter monthValueAvg12Months;

    private LongFilter accountId;

    private LongFilter categoryId;

    public MonthlyReportCriteria() {
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

    public FloatFilter getMonthValue() {
        return monthValue;
    }

    public void setMonthValue(FloatFilter monthValue) {
        this.monthValue = monthValue;
    }

    public FloatFilter getMonthValueAvg3Months() {
        return monthValueAvg3Months;
    }

    public void setMonthValueAvg3Months(FloatFilter monthValueAvg3Months) {
        this.monthValueAvg3Months = monthValueAvg3Months;
    }

    public FloatFilter getMonthValueAvg12Months() {
        return monthValueAvg12Months;
    }

    public void setMonthValueAvg12Months(FloatFilter monthValueAvg12Months) {
        this.monthValueAvg12Months = monthValueAvg12Months;
    }

    public LongFilter getAccountId() {
        return accountId;
    }

    public void setAccountId(LongFilter accountId) {
        this.accountId = accountId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "MonthlyReportCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (month != null ? "month=" + month + ", " : "") +
                (monthValue != null ? "monthValue=" + monthValue + ", " : "") +
                (monthValueAvg3Months != null ? "monthValueAvg3Months=" + monthValueAvg3Months + ", " : "") +
                (monthValueAvg12Months != null ? "monthValueAvg12Months=" + monthValueAvg12Months + ", " : "") +
                (accountId != null ? "accountId=" + accountId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            "}";
    }

}
