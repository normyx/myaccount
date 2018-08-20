package org.mgoulene.repository;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.mgoulene.domain.ReportDateEvolutionData;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDataRepositoryImpl implements ReportDataRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private static final String DELETE_REPORT_DATA_QUERY = "DELETE FROM report_data_by_date WHERE account_id = :accountId";
    private static final String UPDATE_REPORT_DATA_QUERY = ""+
    "INSERT INTO report_data_by_date(jhi_date, month, account_id, category_id, has_operation, operation_amount, budget_smoothed_amount, budget_unsmoothed_marked_amount, budget_unsmoothed_unmarked_amount) " +
    "SELECT  " +
    "        rpt_dated_data.jhi_date AS jhi_date, " +
    "        rpt_dated_data.month AS month, " +
    "        rpt_dated_data.account_id AS account_id, " +
    "        rpt_dated_data.category_id AS category_id, " +
    "        (rpt_dated_data.jhi_date <= account_max_op_date.max_op_date) AS has_operation, " +
    "        SUM(operation_amount.amount) AS operation_amount, " +
    "        SUM(budget_smoothed.amount) / rpt_dated_data.n_days_in_month AS budget_smoothed_amount, " +
    "        SUM(budget_not_smoothed_marked.amount) AS budget_not_smoothed_marked_amount, " +
    "        SUM(budget_not_smoothed_unmarked.amount) AS budget_not_smoothed_unmarked_amount " +
    "FROM (SELECT  " +
    "    days.jhi_date AS jhi_date, " +
    "    days.n_days_in_month AS n_days_in_month, " +
    "    user2.account_id AS account_id, " +
    "    category.category_type AS category_type, " +
    "    category.category_id AS category_id, " +
    "    category.category_name AS category_name,  " +
    "    days.month AS month " +
    "FROM param_days AS days " +
    "JOIN (SELECT jhi_user.id AS account_id FROM jhi_user) AS user2 " +
    "JOIN (SELECT category.id AS category_id,  " +
    "    category.category_type AS category_type,  " +
    "    category.category_name AS category_name FROM category) AS category) rpt_dated_data " +
    "LEFT JOIN (SELECT op.account_id AS account_id, MAX(op.jhi_date) AS max_op_date FROM operation op GROUP BY op.account_id) account_max_op_date  " +
    "    ON rpt_dated_data.account_id = account_max_op_date.account_id " +
    "LEFT JOIN ( " +
    "    SELECT  " +
    "        SUM(bip.amount) AS amount, " +
    "        bip.month AS month, " +
    "        bi.account_id AS account_id, " +
    "        bi.category_id AS category_id " +
    "    FROM budget_item_period AS bip " +
    "    LEFT JOIN budget_item AS bi ON bip.budget_item_id = bi.id " +
    "    WHERE bip.is_smoothed = 1 " +
    "    GROUP BY bip.month, bi.account_id, bi.category_id) AS budget_smoothed  " +
    "ON rpt_dated_data.month = budget_smoothed.month " +
    "    AND rpt_dated_data.account_id = budget_smoothed.account_id  " +
    "    AND rpt_dated_data.category_id = budget_smoothed.category_id " +
    "LEFT JOIN ( " +
    "    SELECT  " +
    "        bip.jhi_date AS jhi_date, " +
    "        SUM(bip.amount) AS amount, " +
    "        bi.account_id AS account_id, " +
    "        bi.category_id AS category_id " +
    "    FROM budget_item_period AS bip " +
    "    LEFT JOIN budget_item AS bi ON bip.budget_item_id = bi.id " +
    "    WHERE bip.is_smoothed = 0 AND bip.operation_id IS NOT NULL " +
    "    GROUP BY bip.jhi_date, bi.account_id, bi.category_id) AS budget_not_smoothed_marked  " +
    "ON rpt_dated_data.jhi_date = budget_not_smoothed_marked.jhi_date " +
    "    AND rpt_dated_data.account_id = budget_not_smoothed_marked.account_id  " +
    "    AND rpt_dated_data.category_id = budget_not_smoothed_marked.category_id " +
    "LEFT JOIN ( " +
    "    SELECT  " +
    "        bip.jhi_date AS jhi_date, " +
    "        SUM(bip.amount) AS amount, " +
    "        bip.month AS month, " +
    "        bi.account_id AS account_id, " +
    "        bi.category_id AS category_id " +
    "    FROM budget_item_period AS bip " +
    "    LEFT JOIN budget_item AS bi ON bip.budget_item_id = bi.id " +
    "    WHERE bip.is_smoothed = 0 AND bip.operation_id IS NULL " +
    "    GROUP BY bip.jhi_date, bi.account_id, bi.category_id) AS budget_not_smoothed_unmarked  " +
    "ON rpt_dated_data.jhi_date = budget_not_smoothed_unmarked.jhi_date " +
    "    AND rpt_dated_data.account_id = budget_not_smoothed_unmarked.account_id  " +
    "    AND rpt_dated_data.category_id = budget_not_smoothed_unmarked.category_id " +
    "LEFT JOIN ( " +
    "    SELECT  " +
    "        op.jhi_date AS jhi_date, " +
    "        SUM(op.amount) AS amount, " +
    "        op.account_id AS account_id, " +
    "        sc.category_id AS category_id " +
    "    FROM operation AS op " +
    "    LEFT JOIN sub_category AS sc ON op.sub_category_id = sc.id " +
    "    GROUP BY op.jhi_date, op.account_id, sc.category_id) AS operation_amount  " +
    "ON rpt_dated_data.jhi_date = operation_amount.jhi_date " +
    "        AND rpt_dated_data.account_id = operation_amount.account_id  " +
    "        AND rpt_dated_data.category_id = operation_amount.category_id " +
    "WHERE rpt_dated_data.account_id = :accountId  " +
    "GROUP BY rpt_dated_data.jhi_date , rpt_dated_data.account_id, rpt_dated_data.category_id";

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

    /**
     * REfresh the ReportData from an accountId. 
     * First delete all the data of the accountId
     * Second insert all the data for the accountId
     * @param accountId the accountId to user
     */
    public void refreshReportData(Long accountId) {
        Query queryDelete = entityManager.createNativeQuery(DELETE_REPORT_DATA_QUERY);
        queryDelete.setParameter("accountId", accountId);
        queryDelete.executeUpdate();
        Query queryInsert = entityManager.createNativeQuery(UPDATE_REPORT_DATA_QUERY);
        queryInsert.setParameter("accountId", accountId);
        queryInsert.executeUpdate();
    }
}
