package org.mgoulene.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BudgetItem entity.
 */
public class BudgetItemDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    private String name;

    private Integer order;

    private Long categoryId;

    private String categoryCategoryName;

    private Long accountId;

    private String accountLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryCategoryName() {
        return categoryCategoryName;
    }

    public void setCategoryCategoryName(String categoryCategoryName) {
        this.categoryCategoryName = categoryCategoryName;
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

        BudgetItemDTO budgetItemDTO = (BudgetItemDTO) o;
        if (budgetItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), budgetItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BudgetItemDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", order=" + getOrder() +
            ", category=" + getCategoryId() +
            ", category='" + getCategoryCategoryName() + "'" +
            ", account=" + getAccountId() +
            ", account='" + getAccountLogin() + "'" +
            "}";
    }
}
