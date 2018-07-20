package org.mgoulene.service.mapper;

import org.mgoulene.domain.*;
import org.mgoulene.service.dto.MonthlyReportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MonthlyReport and its DTO MonthlyReportDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CategoryMapper.class})
public interface MonthlyReportMapper extends EntityMapper<MonthlyReportDTO, MonthlyReport> {

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.login", target = "accountLogin")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.categoryName", target = "categoryCategoryName")
    MonthlyReportDTO toDto(MonthlyReport monthlyReport);

    @Mapping(source = "accountId", target = "account")
    @Mapping(source = "categoryId", target = "category")
    MonthlyReport toEntity(MonthlyReportDTO monthlyReportDTO);

    default MonthlyReport fromId(Long id) {
        if (id == null) {
            return null;
        }
        MonthlyReport monthlyReport = new MonthlyReport();
        monthlyReport.setId(id);
        return monthlyReport;
    }
}
