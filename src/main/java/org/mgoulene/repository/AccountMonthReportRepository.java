package org.mgoulene.repository;

import org.mgoulene.domain.AccountMonthReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AccountMonthReportRepository extends JpaRepository<AccountMonthReport, Long>, JpaSpecificationExecutor<AccountMonthReport> {

    @Query("SELECT amr FROM AccountMonthReport as amr WHERE amr.category.id = :categoryId AND amr.month >= :fromDate AND amr.month <= :toDate ORDER BY amr.month ASC")
    List<AccountMonthReport> findAllFromCategory(@Param("categoryId") Long categoryId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
}
