package org.mgoulene.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ReportDataByDate entity.
 */
public class ReportDataByDateDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalDate month;

    @NotNull
    private Boolean hasOperation;

    private Float operationAmount;

    private Float budgetSmoothedAmount;

    private Float budgetUnsmoothedMarkedAmount;

    private Float budgetUnsmoothedUnmarkedAmount;

    private Long categoryId;

    private Long accountId;

    private String accountLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    public Boolean isHasOperation() {
        return hasOperation;
    }

    public void setHasOperation(Boolean hasOperation) {
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

    public Float getBudgetUnsmoothedMarkedAmount() {
        return budgetUnsmoothedMarkedAmount;
    }

    public void setBudgetUnsmoothedMarkedAmount(Float budgetUnsmoothedMarkedAmount) {
        this.budgetUnsmoothedMarkedAmount = budgetUnsmoothedMarkedAmount;
    }

    public Float getBudgetUnsmoothedUnmarkedAmount() {
        return budgetUnsmoothedUnmarkedAmount;
    }

    public void setBudgetUnsmoothedUnmarkedAmount(Float budgetUnsmoothedUnmarkedAmount) {
        this.budgetUnsmoothedUnmarkedAmount = budgetUnsmoothedUnmarkedAmount;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReportDataByDateDTO reportDataByDateDTO = (ReportDataByDateDTO) o;
        if (reportDataByDateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reportDataByDateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReportDataByDateDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", month='" + getMonth() + "'" +
            ", hasOperation='" + isHasOperation() + "'" +
            ", operationAmount=" + getOperationAmount() +
            ", budgetSmoothedAmount=" + getBudgetSmoothedAmount() +
            ", budgetUnsmoothedMarkedAmount=" + getBudgetUnsmoothedMarkedAmount() +
            ", budgetUnsmoothedUnmarkedAmount=" + getBudgetUnsmoothedUnmarkedAmount() +
            ", category=" + getCategoryId() +
            ", account=" + getAccountId() +
            ", account='" + getAccountLogin() + "'" +
            "}";
    }
}
