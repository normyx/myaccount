package org.mgoulene.repository;

import java.time.LocalDate;
import java.util.List;

import org.mgoulene.domain.ReportDateEvolutionData;
import org.mgoulene.domain.ReportMonthlyData;

public interface ReportDataRepository {

    public List<ReportDateEvolutionData> findReportDataWhereMonth(Long accountId, LocalDate month);
    public List<ReportMonthlyData> findMonthlyReportDataWhereCategoryBetweenMonth(Long accountId, Long categoryId, LocalDate fromDate, LocalDate toDate);
}
