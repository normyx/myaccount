package org.mgoulene.service.mapper;

import org.mgoulene.domain.*;
import org.mgoulene.service.dto.ReportDataByDateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ReportDataByDate and its DTO ReportDataByDateDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UserMapper.class})
public interface ReportDataByDateMapper extends EntityMapper<ReportDataByDateDTO, ReportDataByDate> {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.login", target = "accountLogin")
    ReportDataByDateDTO toDto(ReportDataByDate reportDataByDate);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "accountId", target = "account")
    ReportDataByDate toEntity(ReportDataByDateDTO reportDataByDateDTO);

    default ReportDataByDate fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReportDataByDate reportDataByDate = new ReportDataByDate();
        reportDataByDate.setId(id);
        return reportDataByDate;
    }
}
