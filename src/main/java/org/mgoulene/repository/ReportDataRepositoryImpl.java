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
       
        return "./sql/"+queryName+"-"+databaseDialect+".sql";
        
    }

    private synchronized String getSelectReportDataWhereMonth() {
        
        if (selectReportDateWhereMonthQuery == null) {
            selectReportDateWhereMonthQuery = loadQuery("select_report_data_where_month");
        }
        return selectReportDateWhereMonthQuery;

    }

    private synchronized String getSelectMonthlyReportDataWhereCategoryBetweenMonth() {
        if (selectMonthlyReportDataWhereCategoryBetweenMonthQuery == null) {
            selectMonthlyReportDataWhereCategoryBetweenMonthQuery = loadQuery("select_monthly_report_data_where_category_between_month");
        }
        return selectMonthlyReportDataWhereCategoryBetweenMonthQuery;

    }

    public List<ReportDateEvolutionData> findReportDataWhereMonth(Long accountId, LocalDate month) {
       
        Query querySelect = entityManager.createNativeQuery(getSelectReportDataWhereMonth());
        querySelect.setParameter("accountId", accountId);
        querySelect.setParameter("month", month);
        List<Object[]> results = querySelect.getResultList();
        List<ReportDateEvolutionData> rdedResults = new ArrayList<>();
        for (Object[] res : results) {
            rdedResults.add(new ReportDateEvolutionData(res));
        }
        return rdedResults;
    }

    public List<ReportMonthlyData> findMonthlyReportDataWhereCategoryBetweenMonth(Long accountId, Long categoryId,
            LocalDate fromDate, LocalDate toDate) {
               
        Query querySelect = entityManager
                .createNativeQuery(getSelectMonthlyReportDataWhereCategoryBetweenMonth());
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
}
