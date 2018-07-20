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
 * A EvolutionInMonthReport.
 */
@Entity
@Table(name = "evolution_in_month_report")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EvolutionInMonthReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "month", nullable = false)
    private LocalDate month;

    @NotNull
    @Column(name = "operation", nullable = false)
    private Float operation;

    @NotNull
    @Column(name = "budget", nullable = false)
    private Float budget;

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

    public LocalDate getMonth() {
        return month;
    }

    public EvolutionInMonthReport month(LocalDate month) {
        this.month = month;
        return this;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    public Float getOperation() {
        return operation;
    }

    public EvolutionInMonthReport operation(Float operation) {
        this.operation = operation;
        return this;
    }

    public void setOperation(Float operation) {
        this.operation = operation;
    }

    public Float getBudget() {
        return budget;
    }

    public EvolutionInMonthReport budget(Float budget) {
        this.budget = budget;
        return this;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    public User getAccount() {
        return account;
    }

    public EvolutionInMonthReport account(User user) {
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
        EvolutionInMonthReport evolutionInMonthReport = (EvolutionInMonthReport) o;
        if (evolutionInMonthReport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evolutionInMonthReport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EvolutionInMonthReport{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", operation=" + getOperation() +
            ", budget=" + getBudget() +
            "}";
    }
}
