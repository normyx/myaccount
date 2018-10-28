package org.mgoulene.service.dto;

import java.io.Serializable;

import com.opencsv.bean.CsvBindByPosition;

/**
 * A DTO for the Operation entity.
 */
public class OperationCSVDTO implements Serializable {


    private static final long serialVersionUID = 1L;

    @CsvBindByPosition(position = 1)
    private String label;

    @CsvBindByPosition(position=0)
    private String date;

    @CsvBindByPosition(position=3)
    private String amount;

    @CsvBindByPosition(position=4)
    private String note;

    @CsvBindByPosition(position=5)
    private String checkNumber;

    @CsvBindByPosition(position=2)
    private String subCategoryName;

    @CsvBindByPosition(position=7)
    private String accountName;

    @CsvBindByPosition(position=8)
    private String bankName;

    private Long subCategoryId;

    private Long accountId;

    private Long bankAccountId;


    public String getLabel() {
        return label;
    }

    /**
     * @return the bankAccountId
     */
    public Long getBankAccountId() {
        return bankAccountId;
    }

    /**
     * @param bankAccountId the bankAccountId to set
     */
    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    /**
     * @return the accountId
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * @return the subCateboryId
     */
    public Long getSubCategoryId() {
        return subCategoryId;
    }

    /**
     * @param subCateboryId the subCateboryId to set
     */
    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        // workaround for , décimal delimiter versus .
        this.amount = amount.replace(',', '.');
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

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }





    @Override
    public String toString() {
        return "OperationDTO{" +
            ", label='" + getLabel() + "'" +
            ", date='" + getDate() + "'" +
            ", amount=" + getAmount() +
            ", note='" + getNote() + "'" +
            ", checkNumber='" + getCheckNumber() + "'" +
            ", subCategory=" + getSubCategoryName() + "'" +
            "}";
    }
}
