package org.mgoulene.repository;

import org.mgoulene.domain.MonthlyReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MonthlyReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonthlyReportRepository extends JpaRepository<MonthlyReport, Long>, JpaSpecificationExecutor<MonthlyReport> {

    @Query("select monthly_report from MonthlyReport monthly_report where monthly_report.account.login = ?#{principal.username}")
    List<MonthlyReport> findByAccountIsCurrentUser();

}
