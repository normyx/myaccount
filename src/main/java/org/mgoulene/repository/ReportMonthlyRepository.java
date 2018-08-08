package org.mgoulene.repository;

import org.mgoulene.domain.ReportMonthlyData;

import java.time.LocalDate;
import java.util.List;

public interface ReportMonthlyRepository {

    List<ReportMonthlyData> findAllFromCategory(Long accountId, Long categoryId, LocalDate fromDate, LocalDate toDate);
}
