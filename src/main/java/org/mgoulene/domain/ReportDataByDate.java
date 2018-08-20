package org.mgoulene.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ReportDataByDate.
 */
@Entity
@Table(name = "report_data_by_date")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReportDataByDate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "month", nullable = false)
    private LocalDate month;

    @NotNull
    @Column(name = "has_operation", nullable = false)
    private Boolean hasOperation;

    @Column(name = "operation_amount")
    private Float operationAmount;

    @Column(name = "budget_smoothed_amount")
    private Float budgetSmoothedAmount;

    @Column(name = "budget_unsmoothed_marked_amount")
    private Float budgetUnsmoothedMarkedAmount;

    @Column(name = "budget_unsmoothed_unmarked_amount")
    private Float budgetUnsmoothedUnmarkedAmount;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Category category;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private User account;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReportDataByDate date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getMonth() {
        return month;
    }

    public ReportDataByDate month(LocalDate month) {
        this.month = month;
        return this;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    public Boolean isHasOperation() {
        return hasOperation;
    }

    public ReportDataByDate hasOperation(Boolean hasOperation) {
        this.hasOperation = hasOperation;
        return this;
    }

    public void setHasOperation(Boolean hasOperation) {
        this.hasOperation = hasOperation;
    }

    public Float getOperationAmount() {
        return operationAmount;
    }

    public ReportDataByDate operationAmount(Float operationAmount) {
        this.operationAmount = operationAmount;
        return this;
    }

    public void setOperationAmount(Float operationAmount) {
        this.operationAmount = operationAmount;
    }

    public Float getBudgetSmoothedAmount() {
        return budgetSmoothedAmount;
    }

    public ReportDataByDate budgetSmoothedAmount(Float budgetSmoothedAmount) {
        this.budgetSmoothedAmount = budgetSmoothedAmount;
        return this;
    }

    public void setBudgetSmoothedAmount(Float budgetSmoothedAmount) {
        this.budgetSmoothedAmount = budgetSmoothedAmount;
    }

    public Float getBudgetUnsmoothedMarkedAmount() {
        return budgetUnsmoothedMarkedAmount;
    }

    public ReportDataByDate budgetUnsmoothedMarkedAmount(Float budgetUnsmoothedMarkedAmount) {
        this.budgetUnsmoothedMarkedAmount = budgetUnsmoothedMarkedAmount;
        return this;
    }

    public void setBudgetUnsmoothedMarkedAmount(Float budgetUnsmoothedMarkedAmount) {
        this.budgetUnsmoothedMarkedAmount = budgetUnsmoothedMarkedAmount;
    }

    public Float getBudgetUnsmoothedUnmarkedAmount() {
        return budgetUnsmoothedUnmarkedAmount;
    }

    public ReportDataByDate budgetUnsmoothedUnmarkedAmount(Float budgetUnsmoothedUnmarkedAmount) {
        this.budgetUnsmoothedUnmarkedAmount = budgetUnsmoothedUnmarkedAmount;
        return this;
    }

    public void setBudgetUnsmoothedUnmarkedAmount(Float budgetUnsmoothedUnmarkedAmount) {
        this.budgetUnsmoothedUnmarkedAmount = budgetUnsmoothedUnmarkedAmount;
    }

    public Category getCategory() {
        return category;
    }

    public ReportDataByDate category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getAccount() {
        return account;
    }

    public ReportDataByDate account(User user) {
        this.account = user;
        return this;
    }

    public void setAccount(User user) {
        this.account = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReportDataByDate reportDataByDate = (ReportDataByDate) o;
        if (reportDataByDate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reportDataByDate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReportDataByDate{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", month='" + getMonth() + "'" +
            ", hasOperation='" + isHasOperation() + "'" +
            ", operationAmount=" + getOperationAmount() +
            ", budgetSmoothedAmount=" + getBudgetSmoothedAmount() +
            ", budgetUnsmoothedMarkedAmount=" + getBudgetUnsmoothedMarkedAmount() +
            ", budgetUnsmoothedUnmarkedAmount=" + getBudgetUnsmoothedUnmarkedAmount() +
            "}";
    }
}
