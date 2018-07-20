package org.mgoulene.service.mapper;

import org.mgoulene.domain.*;
import org.mgoulene.service.dto.BudgetItemPeriodDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BudgetItemPeriod and its DTO BudgetItemPeriodDTO.
 */
@Mapper(componentModel = "spring", uses = {BudgetItemMapper.class, OperationMapper.class})
public interface BudgetItemPeriodMapper extends EntityMapper<BudgetItemPeriodDTO, BudgetItemPeriod> {

    @Mapping(source = "budgetItem.id", target = "budgetItemId")
    @Mapping(source = "operation.id", target = "operationId")
    BudgetItemPeriodDTO toDto(BudgetItemPeriod budgetItemPeriod);

    @Mapping(source = "budgetItemId", target = "budgetItem")
    @Mapping(source = "operationId", target = "operation")
    BudgetItemPeriod toEntity(BudgetItemPeriodDTO budgetItemPeriodDTO);

    default BudgetItemPeriod fromId(Long id) {
        if (id == null) {
            return null;
        }
        BudgetItemPeriod budgetItemPeriod = new BudgetItemPeriod();
        budgetItemPeriod.setId(id);
        return budgetItemPeriod;
    }
}
