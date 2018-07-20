package org.mgoulene.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Operation entity.
 */
public class OperationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 400)
    private String label;

    @NotNull
    private LocalDate date;

    @NotNull
    private Float amount;

    @Size(max = 400)
    private String note;

    @Size(max = 20)
    private String checkNumber;

    @NotNull
    private Boolean isUpToDate;

    private Long subCategoryId;

    private String subCategorySubCategoryName;

    private Long accountId;

    private String accountLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Boolean isIsUpToDate() {
        return isUpToDate;
    }

    public void setIsUpToDate(Boolean isUpToDate) {
        this.isUpToDate = isUpToDate;
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategorySubCategoryName() {
        return subCategorySubCategoryName;
    }

    public void setSubCategorySubCategoryName(String subCategorySubCategoryName) {
        this.subCategorySubCategoryName = subCategorySubCategoryName;
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

        OperationDTO operationDTO = (OperationDTO) o;
        if (operationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OperationDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", date='" + getDate() + "'" +
            ", amount=" + getAmount() +
            ", note='" + getNote() + "'" +
            ", checkNumber='" + getCheckNumber() + "'" +
            ", isUpToDate='" + isIsUpToDate() + "'" +
            ", subCategory=" + getSubCategoryId() +
            ", subCategory='" + getSubCategorySubCategoryName() + "'" +
            ", account=" + getAccountId() +
            ", account='" + getAccountLogin() + "'" +
            "}";
    }
}
