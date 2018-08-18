package org.mgoulene.repository;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.mgoulene.domain.ReportDateEvolutionData;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDataRepositoryImpl implements ReportDataRepository {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Find the Report data according to accountId and month
     * @param accountId The User accountId to search
     * @param month the month to search
     * @return the list of ReportDateEvolutionData
     */
    public List<ReportDateEvolutionData> findReportDataByDateWhereAccountIdMonth(Long accountId, LocalDate month) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("get_report_by_date_where_accountId_month");

        // Set the parameters of the stored procedure.
        String firstParam = "accountId";
        storedProcedure.registerStoredProcedureParameter(firstParam, Long.class, ParameterMode.IN);
        storedProcedure.setParameter(firstParam, accountId);
        String secondParam = "month";
        storedProcedure.registerStoredProcedureParameter(secondParam, LocalDate.class, ParameterMode.IN);
        storedProcedure.setParameter(secondParam, month);

        // Call the stored procedure.
        List<Object[]> storedProcedureResults = storedProcedure.getResultList();
        return convertFromStoredProcedure(storedProcedureResults);
    }
    /**
     * Converts the result of the store procedure data to a list of ReportDateEvolutionData
     * @param storedProcedureResults the stored procedure to convert
     * @return a list of ReportDateEvolutionData converted
     */
    private List<ReportDateEvolutionData> convertFromStoredProcedure(List<Object[]> storedProcedureResults) {
        return storedProcedureResults.stream().map(result -> new ReportDateEvolutionData(
            (String) result[0],
            result[1] != null ? ((Date)result[1]).toLocalDate() : null,
            result[2] != null ? ((Date)result[2]).toLocalDate() : null,
            result[3] != null ? ((BigInteger) result[3]).longValue() : null,
            result[4] != null ? ((BigInteger) result[4]).longValue() : null,
            result[5] != null ? (String) result[5] : null,
            result[6] != null ? ((Integer) result[6] != 0) : null,
            result[7] != null ? ((Double) result[7]).floatValue() : null,
            result[8] != null ? ((Double) result[8]).floatValue() : null,
            result[9] != null ? ((Double) result[9]).floatValue() : null,
            result[10] != null ? ((Double) result[10]).floatValue() : null
        )).collect(Collectors.toList());
    }
}
