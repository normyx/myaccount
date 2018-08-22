package org.mgoulene.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class ReportDateEvolutionData implements Serializable {

    private static final long serialVersionUID = 1L;


    public ReportDateEvolutionData(Object[] initData) {
        convert(initData);

    }

    private void convert(Object[] initData) {
        this.id = (String)initData[0];
        this.date = initData[1] != null ? ((Date)initData[1]).toLocalDate() : null;
        this.month = initData[2] != null ? ((Date)initData[2]).toLocalDate() : null;
        this.categoryId = initData[4] != null ? ((BigInteger) initData[4]).longValue() : null;
        this.categoryName = (String)initData[5];
        this.hasOperation = initData[6] != null ? ((Integer) initData[6] != 0) : null;
        this.operationAmount = initData[7] != null ? ((Double) initData[7]).floatValue() : null;
        this.budgetSmoothedAmount = initData[8] != null ? ((Double) initData[8]).floatValue() : null;
        this.budgetUnSmoothedUnMarkedAmount = initData[9] != null ? ((Double) initData[9]).floatValue() : null;
        this.budgetUnSmoothedMarkedAmount = initData[10] != null ? ((Double) initData[10]).floatValue() : null;

    }

    private String id;

    private LocalDate month;

    private LocalDate date;

    private Long categoryId;

    private String categoryName;

    private Boolean hasOperation;

    private Float operationAmount;

    private Float budgetSmoothedAmount;

    private Float budgetUnSmoothedUnMarkedAmount;

    private Float budgetUnSmoothedMarkedAmount;

    public String getId() {
        return this.id;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public boolean isHasOperation() {
        return hasOperation;
    }

    public void setHasOperation(boolean hasOperation) {
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

    public Float getbudgetUnSmoothedUnMarkedAmount() {
        return budgetUnSmoothedUnMarkedAmount;
    }

    public void setbudgetUnSmoothedUnMarkedAmount(Float budgetUnSmoothedUnMarkedAmount) {
        this.budgetUnSmoothedUnMarkedAmount = budgetUnSmoothedUnMarkedAmount;
    }

    public Float getbudgetUnSmoothedMarkedAmount() {
        return budgetUnSmoothedMarkedAmount;
    }

    public void setbudgetUnSmoothedMarkedAmount(Float budgetUnSmoothedMarkedAmount) {
        this.budgetUnSmoothedMarkedAmount = budgetUnSmoothedMarkedAmount;
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
