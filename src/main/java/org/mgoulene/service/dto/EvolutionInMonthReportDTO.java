package org.mgoulene.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EvolutionInMonthReport entity.
 */
public class EvolutionInMonthReportDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate month;

    @NotNull
    private Float operation;

    @NotNull
    private Float budget;

    private Long accountId;

    private String accountLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    public Float getOperation() {
        return operation;
    }

    public void setOperation(Float operation) {
        this.operation = operation;
    }

    public Float getBudget() {
        return budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long userId) {
        this.accountId = userId;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String userLogin) {
        this.accountLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EvolutionInMonthReportDTO evolutionInMonthReportDTO = (EvolutionInMonthReportDTO) o;
        if (evolutionInMonthReportDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evolutionInMonthReportDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EvolutionInMonthReportDTO{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", operation=" + getOperation() +
            ", budget=" + getBudget() +
            ", account=" + getAccountId() +
            ", account='" + getAccountLogin() + "'" +
            "}";
    }
}
