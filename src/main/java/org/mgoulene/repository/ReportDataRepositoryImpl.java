package org.mgoulene.repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.io.IOUtils;
import org.mgoulene.domain.ReportDateEvolutionData;
import org.mgoulene.domain.ReportMonthlyData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDataRepositoryImpl implements ReportDataRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Value("${spring.jpa.database}")
    private String databaseDialect;

    private String selectReportDateWhereMonthQuery;

    private String selectMonthlyReportDataWhereCategoryBetweenMonthQuery;

    private String selectMonthlyReportDataWhereCategoryBetweenMonthWithUnmarkedQuery;

    private String loadQuery(String queryName) {
        try {
            InputStream is;
            is = new ClassPathResource(getQueryFileName(queryName)).getInputStream();
            return IOUtils.toString(is, StandardCharsets.US_ASCII);
        } catch (IOException e) {

            throw new RuntimeException(e);
        }

    }

    private String getQueryFileName(String queryName) {

        return "./sql/" + queryName + "-" + databaseDialect + ".sql";

    }

    private synchronized String getSelectReportDataWhereMonth() {

        if (selectReportDateWhereMonthQuery == null) {
            selectReportDateWhereMonthQuery = loadQuery("select_report_data_where_month");
        }
        return selectReportDateWhereMonthQuery;

    }

    private synchronized String getSelectMonthlyReportDataWhereCategoryBetweenMonth() {
        if (selectMonthlyReportDataWhereCategoryBetweenMonthQuery == null) {
            selectMonthlyReportDataWhereCategoryBetweenMonthQuery = loadQuery(
                    "select_monthly_report_data_where_category_between_month");
        }
        return selectMonthlyReportDataWhereCategoryBetweenMonthQuery;

    }

    private synchronized String getSelectMonthlyReportDataWhereCategoryBetweenMonthWithUnmarked() {
        if (selectMonthlyReportDataWhereCategoryBetweenMonthWithUnmarkedQuery == null) {
            selectMonthlyReportDataWhereCategoryBetweenMonthWithUnmarkedQuery = loadQuery(
                    "select_monthly_report_data_with_unmarked");
        }
        return selectMonthlyReportDataWhereCategoryBetweenMonthWithUnmarkedQuery;

    }

    public List<ReportDateEvolutionData> findReportDataWhereMonth(Long accountId, LocalDate month) {

        Query querySelect = entityManager.createNativeQuery(getSelectReportDataWhereMonth());
        querySelect.setParameter("accountId", accountId);
        querySelect.setParameter("month", month);
        List<Object[]> results = querySelect.getResultList();
        List<ReportDateEvolutionData> rdedResults = new ArrayList<>();
        for (Object[] res : results) {
            rdedResults.add(new ReportDateEvolutionData().id(res[0]).date(res[1]).month(res[2]).categoryId(res[4])
                    .categoryName(res[5]).hasOperation(res[6]).operationAmount(res[7]).budgetSmoothedAmount(res[8])
                    .budgetUnSmoothedUnMarkedAmount(res[9]).budgetUnSmoothedMarkedAmount(res[10]));
        }
        return rdedResults;
    }

    public List<ReportMonthlyData> findMonthlyReportDataWhereCategoryBetweenMonth(Long accountId, Long categoryId,
            LocalDate fromDate, LocalDate toDate) {

        Query querySelect = entityManager.createNativeQuery(getSelectMonthlyReportDataWhereCategoryBetweenMonth());
        querySelect.setParameter("accountId", accountId);
        querySelect.setParameter("categoryId", categoryId);
        querySelect.setParameter("fromDate", fromDate);
        querySelect.setParameter("toDate", toDate);
        List<Object[]> results = querySelect.getResultList();
        List<ReportMonthlyData> rdedResults = new ArrayList<>();
        for (Object[] res : results) {
            rdedResults.add(new ReportMonthlyData(res));
        }
        return rdedResults;
    }

    public List<ReportDateEvolutionData> findMonthlyReportDataWhereCategoryBetweenMonthWithUnmarked(Long accountId,
            Long categoryId, LocalDate fromDate, LocalDate toDate) {

        Query querySelect = entityManager
                .createNativeQuery(getSelectMonthlyReportDataWhereCategoryBetweenMonthWithUnmarked());
        querySelect.setParameter("accountId", accountId);
        querySelect.setParameter("categoryId", categoryId);
        querySelect.setParameter("fromDate", fromDate);
        querySelect.setParameter("toDate", toDate);
        List<Object[]> results = querySelect.getResultList();
        List<ReportDateEvolutionData> rdedResults = new ArrayList<>();
        for (Object[] res : results) {
            rdedResults.add(new ReportDateEvolutionData().month(res[0]).categoryId(res[2]).operationAmount(res[3])
                    .budgetUnSmoothedAtDateAmount(res[4]).budgetSmoothedAmount(res[5])
                    .budgetUnSmoothedMarkedAmount(res[6]).budgetUnSmoothedUnMarkedAmount(res[7]));
        }
        return rdedResults;
    }
}
