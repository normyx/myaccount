package org.mgoulene.service.mapper;

import org.mgoulene.domain.*;
import org.mgoulene.service.dto.OperationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Operation and its DTO OperationDTO.
 */
@Mapper(componentModel = "spring", uses = {SubCategoryMapper.class, UserMapper.class})
public interface OperationMapper extends EntityMapper<OperationDTO, Operation> {

    @Mapping(source = "subCategory.id", target = "subCategoryId")
    @Mapping(source = "subCategory.subCategoryName", target = "subCategorySubCategoryName")
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.login", target = "accountLogin")
    OperationDTO toDto(Operation operation);

    @Mapping(source = "subCategoryId", target = "subCategory")
    @Mapping(source = "accountId", target = "account")
    @Mapping(target = "budgetItem", ignore = true)
    Operation toEntity(OperationDTO operationDTO);

    default Operation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Operation operation = new Operation();
        operation.setId(id);
        return operation;
    }
}
