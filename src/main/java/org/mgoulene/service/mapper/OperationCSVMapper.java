package org.mgoulene.service.mapper;

import org.mgoulene.domain.*;
import org.mgoulene.service.dto.OperationCSVDTO;
import org.mgoulene.service.dto.OperationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Operation and its DTO OperationDTO.
 */
@Mapper(componentModel = "spring")
public interface OperationCSVMapper extends EntityMapper<OperationDTO, OperationCSVDTO> {

    @Mapping(source = "date", target = "date", dateFormat = "dd/MM/yyyy")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "bankAccountId", target = "bankAccountId")
    OperationDTO toDto(OperationCSVDTO operation);

    OperationCSVDTO toEntity(OperationDTO operationDTO);

}
