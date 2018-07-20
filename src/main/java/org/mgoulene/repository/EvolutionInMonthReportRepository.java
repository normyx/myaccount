package org.mgoulene.repository;

import org.mgoulene.domain.EvolutionInMonthReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the EvolutionInMonthReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvolutionInMonthReportRepository extends JpaRepository<EvolutionInMonthReport, Long>, JpaSpecificationExecutor<EvolutionInMonthReport> {

    @Query("select evolution_in_month_report from EvolutionInMonthReport evolution_in_month_report where evolution_in_month_report.account.login = ?#{principal.username}")
    List<EvolutionInMonthReport> findByAccountIsCurrentUser();

}
