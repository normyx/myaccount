package org.mgoulene.repository;

import org.mgoulene.domain.ReportDataByDate;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data  repository for the ReportDataByDate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportDataByDateRepository extends JpaRepository<ReportDataByDate, Long>, JpaSpecificationExecutor<ReportDataByDate> {

    @Query("select report_data_by_date from ReportDataByDate report_data_by_date where report_data_by_date.account.login = ?#{principal.username}")
    List<ReportDataByDate> findByAccountIsCurrentUser();

    @Query("select rd.date, rd.month, rd.hasOperation, SUM(rd.operationAmount), SUM(rd.budgetSmoothedAmount), SUM(rd.budgetUnsmoothedMarkedAmount), SUM(rd.budgetUnsmoothedUnmarkedAmount) "+
    "from ReportDataByDate rd where rd.account.login = ?#{principal.username} AND rd.month = :month "+
    "group by rd.date, rd.month, rd.hasOperation")
    List<Object[]> findByAccountIsCurrentUserAndMonth(@Param("month") LocalDate month);

}
