package org.mgoulene.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MonthlyReport entity.
 */
public class MonthlyReportDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate month;

    @NotNull
    private Float monthValue;

    private Float monthValueAvg3Months;

    private Float monthValueAvg12Months;

    private Long accountId;

    private String accountLogin;

    private Long categoryId;

    private String categoryCategoryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    public Float getMonthValue() {
        return monthValue;
    }

    public void setMonthValue(Float monthValue) {
        this.monthValue = monthValue;
    }

    public Float getMonthValueAvg3Months() {
        return monthValueAvg3Months;
    }

    public void setMonthValueAvg3Months(Float monthValueAvg3Months) {
        this.monthValueAvg3Months = monthValueAvg3Months;
    }

    public Float getMonthValueAvg12Months() {
        return monthValueAvg12Months;
    }

    public void setMonthValueAvg12Months(Float monthValueAvg12Months) {
        this.monthValueAvg12Months = monthValueAvg12Months;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long userId) {
        this.accountId = userId;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String userLogin) {
        this.accountLogin = userLogin;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryCategoryName() {
        return categoryCategoryName;
    }

    public void setCategoryCategoryName(String categoryCategoryName) {
        this.categoryCategoryName = categoryCategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MonthlyReportDTO monthlyReportDTO = (MonthlyReportDTO) o;
        if (monthlyReportDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monthlyReportDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MonthlyReportDTO{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", monthValue=" + getMonthValue() +
            ", monthValueAvg3Months=" + getMonthValueAvg3Months() +
            ", monthValueAvg12Months=" + getMonthValueAvg12Months() +
            ", account=" + getAccountId() +
            ", account='" + getAccountLogin() + "'" +
            ", category=" + getCategoryId() +
            ", category='" + getCategoryCategoryName() + "'" +
            "}";
    }
}
