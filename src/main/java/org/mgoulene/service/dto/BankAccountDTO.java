package org.mgoulene.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BankAccount entity.
 */
public class BankAccountDTO implements Serializable {

    private Long id;

    @NotNull
    private String accountName;

    @NotNull
    private String accountBank;

    @NotNull
    private Float initialAmount;

    private Long accountId;

    private String accountLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public Float getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(Float initialAmount) {
        this.initialAmount = initialAmount;
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

        BankAccountDTO bankAccountDTO = (BankAccountDTO) o;
        if (bankAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bankAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BankAccountDTO{" +
            "id=" + getId() +
            ", accountName='" + getAccountName() + "'" +
            ", accountBank='" + getAccountBank() + "'" +
            ", initialAmount=" + getInitialAmount() +
            ", account=" + getAccountId() +
            ", account='" + getAccountLogin() + "'" +
            "}";
    }
}
