package org.mgoulene.service.mapper;

import org.mgoulene.domain.*;
import org.mgoulene.service.dto.BudgetItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BudgetItem and its DTO BudgetItemDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UserMapper.class})
public interface BudgetItemMapper extends EntityMapper<BudgetItemDTO, BudgetItem> {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.categoryName", target = "categoryCategoryName")
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.login", target = "accountLogin")
    BudgetItemDTO toDto(BudgetItem budgetItem);

    @Mapping(target = "budgetItemPeriods", ignore = true)
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "accountId", target = "account")
    BudgetItem toEntity(BudgetItemDTO budgetItemDTO);

    default BudgetItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setId(id);
        return budgetItem;
    }
}
