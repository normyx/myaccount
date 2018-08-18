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

import org.mgoulene.domain.ReportMonthlyData;
import org.springframework.stereotype.Repository;

@Repository
public class ReportMonthlyRepositoryImpl implements ReportMonthlyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ReportMonthlyData> findAllFromCategory(Long accountId, Long categoryId, LocalDate fromDate, LocalDate toDate) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("get_report_amounts_where_accountId_category_grp_by_month");

        // Set the parameters of the stored procedure.
        storedProcedure.registerStoredProcedureParameter(0, Long.class, ParameterMode.IN);
        storedProcedure.setParameter(0, accountId);
        storedProcedure.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
        storedProcedure.setParameter(1, categoryId);
        storedProcedure.registerStoredProcedureParameter(2, LocalDate.class, ParameterMode.IN);
        storedProcedure.setParameter(2, fromDate);
        storedProcedure.registerStoredProcedureParameter(3, LocalDate.class, ParameterMode.IN);
        storedProcedure.setParameter(3, toDate);

        // Call the stored procedure.
        List<Object[]> storedProcedureResults = storedProcedure.getResultList();
        return convertFromStoredProcedure(storedProcedureResults);

    }

    private List<ReportMonthlyData> convertFromStoredProcedure(List<Object[]> storedProcedureResults) {
        return storedProcedureResults.stream().map(result -> new ReportMonthlyData(
            (String) result[0],
            result[1] != null ? ((BigInteger)result[1]).longValue() : null,
            result[2] != null ? ((Date)result[2]).toLocalDate() : null,
            result[3] != null ? ((BigInteger) result[3]).longValue() : null,
            result[4] != null ? (String) result[4] : null,
            result[5] != null ? ((Double) result[5]).floatValue() : null,
            result[6] != null ? ((Double) result[6]).floatValue() : null,
            result[7] != null ? ((Double) result[7]).floatValue() : null,
            result[8] != null ? ((Double) result[8]).floatValue() : null
        )).collect(Collectors.toList());
    }

}
