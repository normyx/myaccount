package org.mgoulene.service.mapper;

import org.mgoulene.domain.*;
import org.mgoulene.service.dto.EvolutionInMonthReportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EvolutionInMonthReport and its DTO EvolutionInMonthReportDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface EvolutionInMonthReportMapper extends EntityMapper<EvolutionInMonthReportDTO, EvolutionInMonthReport> {

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.login", target = "accountLogin")
    EvolutionInMonthReportDTO toDto(EvolutionInMonthReport evolutionInMonthReport);

    @Mapping(source = "accountId", target = "account")
    EvolutionInMonthReport toEntity(EvolutionInMonthReportDTO evolutionInMonthReportDTO);

    default EvolutionInMonthReport fromId(Long id) {
        if (id == null) {
            return null;
        }
        EvolutionInMonthReport evolutionInMonthReport = new EvolutionInMonthReport();
        evolutionInMonthReport.setId(id);
        return evolutionInMonthReport;
    }
}
