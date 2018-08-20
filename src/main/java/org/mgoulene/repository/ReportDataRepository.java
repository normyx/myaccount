package org.mgoulene.repository;

import java.time.LocalDate;
import java.util.List;

import org.mgoulene.domain.ReportDateEvolutionData;

@SuppressWarnings("unused")
public interface ReportDataRepository {

    public void refreshReportData(Long accountId, List<Long> categoryIds);
}
