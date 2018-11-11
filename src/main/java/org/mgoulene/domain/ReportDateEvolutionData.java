package org.mgoulene.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class ReportDateEvolutionData implements Serializable {

    private static final long serialVersionUID = 1L;

    public ReportDateEvolutionData() {
    }

    private String id;

    private LocalDate month;

    private LocalDate date;

    private Long categoryId;

    private String categoryName;

    private Boolean hasOperation;

    private Float operationAmount;

    private Float budgetSmoothedAmount;

    private Float budgetUnSmoothedAtDateAmount;

    private Float budgetUnSmoothedUnMarkedAmount;

    private Float budgetUnSmoothedMarkedAmount;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReportDateEvolutionData id(Object id) {
        setId((String) id);
        return this;
    }

    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    public ReportDateEvolutionData month(Object month) {
        setMonth(month != null ? ((Date) month).toLocalDate() : null);
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ReportDateEvolutionData date(Object date) {
        setDate(date != null ? ((Date) date).toLocalDate() : null);
        return this;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public ReportDateEvolutionData categoryId(Object categoryId) {
        setCategoryId(categoryId != null ? ((BigInteger) categoryId).longValue() : null);
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ReportDateEvolutionData categoryName(Object categoryName) {
        setCategoryName((String) categoryName);
        return this;
    }

    public boolean isHasOperation() {
        return hasOperation == null ? false : hasOperation;
    }

    public void setHasOperation(boolean hasOperation) {
        this.hasOperation = hasOperation;
    }

    public ReportDateEvolutionData hasOperation(Object hasOperation) {
        if (hasOperation instanceof Integer) {
            setHasOperation(hasOperation != null ? ((Integer) hasOperation != 0) : null);
        } else if (hasOperation instanceof Boolean) {
            setHasOperation((Boolean) hasOperation);
        } else {
            throw new RuntimeException("Can not convert " + hasOperation + " to Boolean");
        }
        return this;
    }

    public Float getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(Float operationAmount) {
        this.operationAmount = operationAmount;
    }

    public ReportDateEvolutionData operationAmount(Object amount) {
        setOperationAmount(amount != null ? ((Double) amount).floatValue() : null);
        return this;
    }

    public Float getBudgetSmoothedAmount() {
        return budgetSmoothedAmount;
    }

    public void setBudgetSmoothedAmount(Float budgetSmoothedAmount) {
        this.budgetSmoothedAmount = budgetSmoothedAmount;
    }

    public ReportDateEvolutionData budgetSmoothedAmount(Object amount) {
        setBudgetSmoothedAmount(amount != null ? ((Double) amount).floatValue() : null);
        return this;
    }

    public Float getbudgetUnSmoothedUnMarkedAmount() {
        return budgetUnSmoothedUnMarkedAmount;
    }

    public void setbudgetUnSmoothedUnMarkedAmount(Float budgetUnSmoothedUnMarkedAmount) {
        this.budgetUnSmoothedUnMarkedAmount = budgetUnSmoothedUnMarkedAmount;
    }

    public ReportDateEvolutionData budgetUnSmoothedUnMarkedAmount(Object amount) {
        setbudgetUnSmoothedUnMarkedAmount(amount != null ? ((Double) amount).floatValue() : null);
        return this;
    }

    public Float getbudgetUnSmoothedMarkedAmount() {
        return budgetUnSmoothedMarkedAmount;
    }

    public void setbudgetUnSmoothedMarkedAmount(Float budgetUnSmoothedMarkedAmount) {
        this.budgetUnSmoothedMarkedAmount = budgetUnSmoothedMarkedAmount;
    }

    public ReportDateEvolutionData budgetUnSmoothedMarkedAmount(Object amount) {
        setbudgetUnSmoothedMarkedAmount(amount != null ? ((Double) amount).floatValue() : null);
        return this;
    }

    /**
     * @return the budgetSmoothedAtDateAmount
     */
    public Float getBudgetUnSmoothedAtDateAmount() {
        return budgetUnSmoothedAtDateAmount;
    }

    /**
     * @param budgetSmoothedAtDateAmount the budgetSmoothedAtDateAmount to set
     */
    public void setBudgetUnSmoothedAtDateAmount(Float budgetUnSmoothedAtDateAmount) {
        this.budgetUnSmoothedAtDateAmount = budgetUnSmoothedAtDateAmount;
    }

    public ReportDateEvolutionData budgetUnSmoothedAtDateAmount(Object amount) {
        setBudgetUnSmoothedAtDateAmount(amount != null ? ((Double) amount).floatValue() : null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReportDateEvolutionData rd = (ReportDateEvolutionData) o;
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
        return "ReportDateEvolutionData{" + "id=" + getId() + "}";
    }
}
