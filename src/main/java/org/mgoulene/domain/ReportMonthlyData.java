package org.mgoulene.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


public class ReportMonthlyData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private Long accountId;

    private LocalDate month;

    private Long categoryId;

    private String categoryName;

    private Float budgetAmount;

    private Float amount;

    private Float amountAvg3;

    private Float amountAvg12;

    public ReportMonthlyData(String id, Long accountId, LocalDate month, Long categoryId, String categoryName, Float budgetAmount, Float amount, Float amountAvg3, Float amountAvg12) {
        this.id = id;
        this.accountId = accountId;
        this.month = month;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.budgetAmount = budgetAmount;
        this.amount = amount;
        this.amountAvg3 = amountAvg3;
        this.amountAvg12 = amountAvg12;
    }

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

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getAmountAvg3() {
        return amountAvg3;
    }

    public void setAmountAvg3(Float amountAvg3) {
        this.amountAvg3 = amountAvg3;
    }

    public Float getAmountAvg12() {
        return amountAvg12;
    }

    public void setAmountAvg12(Float amountAvg12) {
        this.amountAvg12 = amountAvg12;
    }

    public Float getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(Float budgetAmount) {
        this.budgetAmount = budgetAmount;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReportMonthlyData amr = (ReportMonthlyData) o;
        if (amr.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amr.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReportMonthlyData{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", amount=" + getAmount() +
            ", amountAvg3=" + getAmountAvg3() +
            ", amountAvg12=" + getAmountAvg12() +
            ", budgetAmount=" + getBudgetAmount() +
            "}";
    }
}
