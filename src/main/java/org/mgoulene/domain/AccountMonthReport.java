package org.mgoulene.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "account_report")
public class AccountMonthReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @NotNull
    @Column(name = "month", nullable = false)
    private LocalDate month;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Float amount;

    @NotNull
    @Column(name = "amount_avg_3")
    private Float amountAvg3;

    @NotNull
    @Column(name = "amount_avg_12")
    private Float amountAvg12;

    @NotNull
    @Column(name = "budget_amount")
    private Float budgetAmount;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User account;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Category category;

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

    public User getAccount() {
        return account;
    }

    public void setAccount(User account) {
        this.account = account;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountMonthReport amr = (AccountMonthReport) o;
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
        return "BudgetItem{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", amount=" + getAmount() +
            ", amountAvg3=" + getAmountAvg3() +
            ", amountAvg12=" + getAmountAvg12() +
            ", budgetAmount=" + getBudgetAmount() +
            "}";
    }
}
