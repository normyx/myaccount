package org.mgoulene.repository;

import java.time.LocalDate;
import java.util.List;

import org.mgoulene.domain.ReportDateEvolutionData;

@SuppressWarnings("unused")
public interface ReportDataRepository {

    List<ReportDateEvolutionData> findReportDataByDateWhereAccountIdMonth(Long accountId, LocalDate month);
}
