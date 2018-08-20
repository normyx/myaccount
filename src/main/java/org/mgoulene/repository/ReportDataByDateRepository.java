package org.mgoulene.repository;

import org.mgoulene.domain.ReportDataByDate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ReportDataByDate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportDataByDateRepository extends JpaRepository<ReportDataByDate, Long>, JpaSpecificationExecutor<ReportDataByDate> {

    @Query("select report_data_by_date from ReportDataByDate report_data_by_date where report_data_by_date.account.login = ?#{principal.username}")
    List<ReportDataByDate> findByAccountIsCurrentUser();

}
