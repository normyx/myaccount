package org.mgoulene.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Operation.
 */
@Entity
@Table(name = "operation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 400)
    @Column(name = "jhi_label", length = 400, nullable = false)
    private String label;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Float amount;

    @Size(max = 400)
    @Column(name = "note", length = 400)
    private String note;

    @Size(max = 20)
    @Column(name = "check_number", length = 20)
    private String checkNumber;

    @NotNull
    @Column(name = "is_up_to_date", nullable = false)
    private Boolean isUpToDate;

    @ManyToOne
    @JsonIgnoreProperties("")
    private SubCategory subCategory;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User account;

    @OneToOne(mappedBy = "operation")
    @JsonIgnore
    private BudgetItemPeriod budgetItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public Operation label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalDate getDate() {
        return date;
    }

    public Operation date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getAmount() {
        return amount;
    }

    public Operation amount(Float amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public Operation note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public Operation checkNumber(String checkNumber) {
        this.checkNumber = checkNumber;
        return this;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Boolean isIsUpToDate() {
        return isUpToDate;
    }

    public Operation isUpToDate(Boolean isUpToDate) {
        this.isUpToDate = isUpToDate;
        return this;
    }

    public void setIsUpToDate(Boolean isUpToDate) {
        this.isUpToDate = isUpToDate;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public Operation subCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
        return this;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public User getAccount() {
        return account;
    }

    public Operation account(User user) {
        this.account = user;
        return this;
    }

    public void setAccount(User user) {
        this.account = user;
    }

    public BudgetItemPeriod getBudgetItem() {
        return budgetItem;
    }

    public Operation budgetItem(BudgetItemPeriod budgetItemPeriod) {
        this.budgetItem = budgetItemPeriod;
        return this;
    }

    public void setBudgetItem(BudgetItemPeriod budgetItemPeriod) {
        this.budgetItem = budgetItemPeriod;
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
        Operation operation = (Operation) o;
        if (operation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operation{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", date='" + getDate() + "'" +
            ", amount=" + getAmount() +
            ", note='" + getNote() + "'" +
            ", checkNumber='" + getCheckNumber() + "'" +
            ", isUpToDate='" + isIsUpToDate() + "'" +
            "}";
    }
}
