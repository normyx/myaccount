package org.mgoulene.repository;

import org.mgoulene.domain.Operation;
import org.mgoulene.domain.ReportData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("unused")
public interface ReportDataRepository {

    List<ReportData> findReportDataByDateWhereAccountIdMonth(Long accountId, LocalDate month);
}
