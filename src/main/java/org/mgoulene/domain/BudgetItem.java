package org.mgoulene.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BudgetItem.
 */
@Entity
@Table(name = "budget_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BudgetItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @OneToMany(mappedBy = "budgetItem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BudgetItemPeriod> budgetItemPeriods = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User account;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BudgetItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public BudgetItem order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Set<BudgetItemPeriod> getBudgetItemPeriods() {
        return budgetItemPeriods;
    }

    public BudgetItem budgetItemPeriods(Set<BudgetItemPeriod> budgetItemPeriods) {
        this.budgetItemPeriods = budgetItemPeriods;
        return this;
    }

    public BudgetItem addBudgetItemPeriods(BudgetItemPeriod budgetItemPeriod) {
        this.budgetItemPeriods.add(budgetItemPeriod);
        budgetItemPeriod.setBudgetItem(this);
        return this;
    }

    public BudgetItem removeBudgetItemPeriods(BudgetItemPeriod budgetItemPeriod) {
        this.budgetItemPeriods.remove(budgetItemPeriod);
        budgetItemPeriod.setBudgetItem(null);
        return this;
    }

    public void setBudgetItemPeriods(Set<BudgetItemPeriod> budgetItemPeriods) {
        this.budgetItemPeriods = budgetItemPeriods;
    }

    public Category getCategory() {
        return category;
    }

    public BudgetItem category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getAccount() {
        return account;
    }

    public BudgetItem account(User user) {
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
        BudgetItem budgetItem = (BudgetItem) o;
        if (budgetItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), budgetItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BudgetItem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
