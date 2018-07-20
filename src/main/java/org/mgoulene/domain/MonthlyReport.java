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
 * A MonthlyReport.
 */
@Entity
@Table(name = "monthly_report")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MonthlyReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "month", nullable = false)
    private LocalDate month;

    @NotNull
    @Column(name = "month_value", nullable = false)
    private Float monthValue;

    @Column(name = "month_value_avg_3_months")
    private Float monthValueAvg3Months;

    @Column(name = "month_value_avg_12_months")
    private Float monthValueAvg12Months;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User account;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Category category;

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

    public MonthlyReport month(LocalDate month) {
        this.month = month;
        return this;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    public Float getMonthValue() {
        return monthValue;
    }

    public MonthlyReport monthValue(Float monthValue) {
        this.monthValue = monthValue;
        return this;
    }

    public void setMonthValue(Float monthValue) {
        this.monthValue = monthValue;
    }

    public Float getMonthValueAvg3Months() {
        return monthValueAvg3Months;
    }

    public MonthlyReport monthValueAvg3Months(Float monthValueAvg3Months) {
        this.monthValueAvg3Months = monthValueAvg3Months;
        return this;
    }

    public void setMonthValueAvg3Months(Float monthValueAvg3Months) {
        this.monthValueAvg3Months = monthValueAvg3Months;
    }

    public Float getMonthValueAvg12Months() {
        return monthValueAvg12Months;
    }

    public MonthlyReport monthValueAvg12Months(Float monthValueAvg12Months) {
        this.monthValueAvg12Months = monthValueAvg12Months;
        return this;
    }

    public void setMonthValueAvg12Months(Float monthValueAvg12Months) {
        this.monthValueAvg12Months = monthValueAvg12Months;
    }

    public User getAccount() {
        return account;
    }

    public MonthlyReport account(User user) {
        this.account = user;
        return this;
    }

    public void setAccount(User user) {
        this.account = user;
    }

    public Category getCategory() {
        return category;
    }

    public MonthlyReport category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        MonthlyReport monthlyReport = (MonthlyReport) o;
        if (monthlyReport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monthlyReport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MonthlyReport{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", monthValue=" + getMonthValue() +
            ", monthValueAvg3Months=" + getMonthValueAvg3Months() +
            ", monthValueAvg12Months=" + getMonthValueAvg12Months() +
            "}";
    }
}
