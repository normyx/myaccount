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
 * A BudgetItemPeriod.
 */
@Entity
@Table(name = "budget_item_period")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BudgetItemPeriod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @NotNull
    @Column(name = "month", nullable = false)
    private LocalDate month;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Float amount;

    @Column(name = "is_smoothed")
    private Boolean isSmoothed;

    @Column(name = "is_recurrent")
    private Boolean isRecurrent;

    @ManyToOne
    @JsonIgnoreProperties("budgetItemPeriods")
    private BudgetItem budgetItem;

    @OneToOne    @JoinColumn(unique = true)
    private Operation operation;

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

    public BudgetItemPeriod date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getMonth() {
        return month;
    }

    public BudgetItemPeriod month(LocalDate month) {
        this.month = month;
        return this;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    public Float getAmount() {
        return amount;
    }

    public BudgetItemPeriod amount(Float amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Boolean isIsSmoothed() {
        return isSmoothed;
    }

    public BudgetItemPeriod isSmoothed(Boolean isSmoothed) {
        this.isSmoothed = isSmoothed;
        return this;
    }

    public void setIsSmoothed(Boolean isSmoothed) {
        this.isSmoothed = isSmoothed;
    }

    public Boolean isIsRecurrent() {
        return isRecurrent;
    }

    public BudgetItemPeriod isRecurrent(Boolean isRecurrent) {
        this.isRecurrent = isRecurrent;
        return this;
    }

    public void setIsRecurrent(Boolean isRecurrent) {
        this.isRecurrent = isRecurrent;
    }

    public BudgetItem getBudgetItem() {
        return budgetItem;
    }

    public BudgetItemPeriod budgetItem(BudgetItem budgetItem) {
        this.budgetItem = budgetItem;
        return this;
    }

    public void setBudgetItem(BudgetItem budgetItem) {
        this.budgetItem = budgetItem;
    }

    public Operation getOperation() {
        return operation;
    }

    public BudgetItemPeriod operation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
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
        BudgetItemPeriod budgetItemPeriod = (BudgetItemPeriod) o;
        if (budgetItemPeriod.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), budgetItemPeriod.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BudgetItemPeriod{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", month='" + getMonth() + "'" +
            ", amount=" + getAmount() +
            ", isSmoothed='" + isIsSmoothed() + "'" +
            ", isRecurrent='" + isIsRecurrent() + "'" +
            "}";
    }
}
