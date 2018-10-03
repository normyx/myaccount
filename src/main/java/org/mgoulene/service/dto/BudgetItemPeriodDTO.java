package org.mgoulene.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;

import org.mgoulene.web.rest.util.LocalDateUtil;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BudgetItemPeriod entity.
 */
public class BudgetItemPeriodDTO implements Serializable {

    private Long id;

    private LocalDate date;

    @NotNull
    private LocalDate month;

    @NotNull
    private Float amount;

    private Boolean isSmoothed;

    private Boolean isRecurrent;

    private Long budgetItemId;

    private Long operationId;

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

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Boolean isIsSmoothed() {
        return isSmoothed;
    }

    public void setIsSmoothed(Boolean isSmoothed) {
        this.isSmoothed = isSmoothed;
    }

    public Boolean isIsRecurrent() {
        return isRecurrent;
    }

    public void setIsRecurrent(Boolean isRecurrent) {
        this.isRecurrent = isRecurrent;
    }

    public Long getBudgetItemId() {
        return budgetItemId;
    }

    public void setBudgetItemId(Long budgetItemId) {
        this.budgetItemId = budgetItemId;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
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

        BudgetItemPeriodDTO budgetItemPeriodDTO = (BudgetItemPeriodDTO) o;
        if (budgetItemPeriodDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), budgetItemPeriodDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BudgetItemPeriodDTO{" + "id=" + getId() + ", date='" + getDate() + "'" + ", month='" + getMonth() + "'"
                + ", amount=" + getAmount() + ", isSmoothed='" + isIsSmoothed() + "'" + ", isRecurrent='"
                + isIsRecurrent() + "'" + ", budgetItem=" + getBudgetItemId() + ", operation=" + getOperationId() + "}";
    }

    public boolean isDateSameMonthThatMonth() {
        if (getDate() == null || getMonth() == null) {
            return false;
        } else {
            return getDate().getYear() == getMonth().getYear() && getDate().getMonthValue() == getMonth().getMonthValue();
        }
    }

    public void setMonthWithDate(LocalDate date) {
        setMonth(LocalDate.of(date.getYear(), date.getMonthValue(),1));
    }

    /**
     * Set the valid date from a given day of month and sync month if diffrerent from date. If dayOfMonth > maxDayInTheMonth, then set to the max days in month
     */
    public void setDateAndSyncMonth(int dayOfMonth) {
        if (getDate().getMonthValue() != getMonth().getMonthValue()) {
            setMonthWithDate(this.date);
        }
        
        
        setDate(LocalDateUtil.getLocalDate(month, dayOfMonth));

    }
}
