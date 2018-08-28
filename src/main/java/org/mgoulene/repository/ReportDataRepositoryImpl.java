package org.mgoulene.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mgoulene.domain.ReportDateEvolutionData;
import org.mgoulene.domain.ReportMonthlyData;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDataRepositoryImpl implements ReportDataRepository {
    @PersistenceContext
    private EntityManager entityManager;


    private static final String SELECT_REPORT_DATA_WHERE_MONTH_QUERY = "" + "SELECT  "
            + "        CONCAT(rpt_dated_data.jhi_date,'-',rpt_dated_data.month,'-',rpt_dated_data.account_id,'-', rpt_dated_data.category_id) AS id,"
            + "        rpt_dated_data.jhi_date AS date, " 
            + "        rpt_dated_data.month AS month, "
            + "        rpt_dated_data.account_id AS account_id, "
            + "        rpt_dated_data.category_id AS category_id, "
            + "        rpt_dated_data.category_name AS category_name, "
            + "        (rpt_dated_data.jhi_date <= account_max_op_date.max_op_date) AS has_operation, "
            + "        SUM(operation_amount.amount) AS operation_amount, "
            + "        SUM(budget_smoothed.amount) / rpt_dated_data.n_days_in_month AS budget_smoothed_amount, "
            + "        SUM(budget_not_smoothed_marked.amount) AS budget_un_smoothed_marked_amount, "
            + "        SUM(budget_not_smoothed_unmarked.amount) AS budget_un_smoothed_un_marked_amount "
            + "FROM (SELECT  " + "    days.jhi_date AS jhi_date, " + "    days.n_days_in_month AS n_days_in_month, "
            + "    user2.account_id AS account_id, " + "    category.category_type AS category_type, "
            + "    category.category_id AS category_id, " + "    category.category_name AS category_name,  "
            + "    days.month AS month " + "FROM param_days AS days "
            + "JOIN (SELECT jhi_user.id AS account_id FROM jhi_user) AS user2 "
            + "JOIN (SELECT category.id AS category_id,  " + "    category.category_type AS category_type,  "
            + "    category.category_name AS category_name FROM category) AS category) rpt_dated_data "
            + "LEFT JOIN (SELECT op.account_id AS account_id, MAX(op.jhi_date) AS max_op_date FROM operation op GROUP BY op.account_id) account_max_op_date  "
            + "    ON rpt_dated_data.account_id = account_max_op_date.account_id " + "LEFT JOIN ( " + "    SELECT  "
            + "        SUM(bip.amount) AS amount, " + "        bip.month AS month, "
            + "        bi.account_id AS account_id, " + "        bi.category_id AS category_id "
            + "    FROM budget_item_period AS bip " + "    LEFT JOIN budget_item AS bi ON bip.budget_item_id = bi.id "
            + "    WHERE bip.is_smoothed = 1 "
            + "    GROUP BY bip.month, bi.account_id, bi.category_id) AS budget_smoothed  "
            + "ON rpt_dated_data.month = budget_smoothed.month "
            + "    AND rpt_dated_data.account_id = budget_smoothed.account_id  "
            + "    AND rpt_dated_data.category_id = budget_smoothed.category_id " + "LEFT JOIN ( " + "    SELECT  "
            + "        bip.jhi_date AS jhi_date, " + "        SUM(bip.amount) AS amount, "
            + "        bi.account_id AS account_id, " + "        bi.category_id AS category_id "
            + "    FROM budget_item_period AS bip " + "    LEFT JOIN budget_item AS bi ON bip.budget_item_id = bi.id "
            + "    WHERE bip.is_smoothed = 0 AND bip.operation_id IS NOT NULL "
            + "    GROUP BY bip.jhi_date, bi.account_id, bi.category_id) AS budget_not_smoothed_marked  "
            + "ON rpt_dated_data.jhi_date = budget_not_smoothed_marked.jhi_date "
            + "    AND rpt_dated_data.account_id = budget_not_smoothed_marked.account_id  "
            + "    AND rpt_dated_data.category_id = budget_not_smoothed_marked.category_id " + "LEFT JOIN ( "
            + "    SELECT  " + "        bip.jhi_date AS jhi_date, " + "        SUM(bip.amount) AS amount, "
            + "        bip.month AS month, " + "        bi.account_id AS account_id, "
            + "        bi.category_id AS category_id " + "    FROM budget_item_period AS bip "
            + "    LEFT JOIN budget_item AS bi ON bip.budget_item_id = bi.id "
            + "    WHERE bip.is_smoothed = 0 AND bip.operation_id IS NULL "
            + "    GROUP BY bip.jhi_date, bi.account_id, bi.category_id) AS budget_not_smoothed_unmarked  "
            + "ON rpt_dated_data.jhi_date = budget_not_smoothed_unmarked.jhi_date "
            + "    AND rpt_dated_data.account_id = budget_not_smoothed_unmarked.account_id  "
            + "    AND rpt_dated_data.category_id = budget_not_smoothed_unmarked.category_id " + "LEFT JOIN ( "
            + "    SELECT  " + "        op.jhi_date AS jhi_date, " + "        SUM(op.amount) AS amount, "
            + "        op.account_id AS account_id, " + "        sc.category_id AS category_id "
            + "    FROM operation AS op " + "    LEFT JOIN sub_category AS sc ON op.sub_category_id = sc.id "
            + "    GROUP BY op.jhi_date, op.account_id, sc.category_id) AS operation_amount  "
            + "ON rpt_dated_data.jhi_date = operation_amount.jhi_date "
            + "        AND rpt_dated_data.account_id = operation_amount.account_id  "
            + "        AND rpt_dated_data.category_id = operation_amount.category_id "
            + "WHERE rpt_dated_data.category_type <> 'OTHER' AND rpt_dated_data.account_id = :accountId AND rpt_dated_data.month = :month "
            + "GROUP BY rpt_dated_data.jhi_date , rpt_dated_data.account_id " + "ORDER BY rpt_dated_data.jhi_date ";

    private static final String SELECT_MONTHLY_REPORT_DATA_WHERE_CATEGORY_BETWEEN_MONTH_QUERY = "" + "SELECT  "
            + "    CONCAT(br.account_id, " + "            '-', " + "            br.category_id, " + "            '-', "
            + "            br.month) AS id, " 
            + "    br.account_id AS account_id, " 
            + "    br.month AS month, "
            + "    br.category_id AS category_id, " 
            + "    br.category_name AS category_name, "
            + "    br.budget_amount AS budget_amount, " 
            + "    opr.amount AS amount, "
            + "    opr.amount_avg_3 AS amount_avg3, " 
            + "    opr.amount_avg_12 AS amount_avg12 " + "FROM "
            + "    (SELECT  " + "        bi.account_id AS account_id, " + "            bi.category_id AS category_id, "
            + "            cat.category_name AS category_name, " + "            bip.month AS month, "
            + "            SUM(bip.amount) AS budget_amount " + "    FROM " + "        (budget_item bi "
            + "    JOIN budget_item_period bip ON bi.id = bip.budget_item_id "
            + "    JOIN category cat ON cat.id = bi.category_id) "
            + "    GROUP BY bi.account_id , bi.category_id , bip.month) AS br " + "        LEFT JOIN " + "    (SELECT  "
            + "        m1.month AS month, " + "            m1.account_id AS account_id, "
            + "            m1.category_id AS category_id, " + "            m1.amount AS amount, "
            + "            AVG(m2.amount) AS amount_avg_3, " + "            AVG(m3.amount) AS amount_avg_12 "
            + "    FROM " + "        (SELECT  "
            + "        STR_TO_DATE(CONCAT(YEAR(operation.jhi_date), '-', MONTH(operation.jhi_date), '-', '01'), '%Y-%m-%d') AS month, "
            + "            sub_category.category_id AS category_id, "
            + "            operation.account_id AS account_id, " + "            SUM(operation.amount) AS amount "
            + "    FROM operation " + "    JOIN sub_category ON operation.sub_category_id = sub_category.id "
            + "    GROUP BY month , sub_category.category_id , operation.account_id) AS m1 " + "    JOIN (SELECT  "
            + "        STR_TO_DATE(CONCAT(YEAR(operation.jhi_date), '-', MONTH(operation.jhi_date), '-', '01'), '%Y-%m-%d') AS month, "
            + "            sub_category.category_id AS category_id, "
            + "            operation.account_id AS account_id, " + "            SUM(operation.amount) AS amount "
            + "    FROM " + "        (operation "
            + "    JOIN sub_category ON ((operation.sub_category_id = sub_category.id))) "
            + "    GROUP BY month , sub_category.category_id , operation.account_id) AS m2 ON m1.category_id = m2.category_id "
            + "        AND m1.account_id = m2.account_id " + "    JOIN (SELECT  "
            + "        STR_TO_DATE(CONCAT(YEAR(operation.jhi_date), '-', MONTH(operation.jhi_date), '-', '01'), '%Y-%m-%d') AS month, "
            + "            sub_category.category_id AS category_id, "
            + "            operation.account_id AS account_id, " + "            SUM(operation.amount) AS amount "
            + "    FROM " + "        (operation "
            + "    JOIN sub_category ON ((operation.sub_category_id = sub_category.id))) "
            + "    GROUP BY month , sub_category.category_id , operation.account_id) AS m3 ON m1.category_id = m3.category_id "
            + "        AND m1.account_id = m3.account_id " + "    WHERE " + "        m2.month <= m1.month "
            + "            AND m2.month >= (m1.month + INTERVAL -(2) MONTH) " + "            AND m3.month <= m1.month "
            + "            AND m3.month >= (m1.month + INTERVAL -(11) MONTH) "
            + "    GROUP BY m1.month , m1.category_id , m1.account_id "
            + "    ORDER BY m1.category_id , m1.month) AS opr ON br.account_id = opr.account_id "
            + "        AND br.month = opr.month " + "        AND br.category_id = opr.category_id "
            + "WHERE br.account_id = :accountId AND br.category_id = :categoryId AND br.month >= :fromDate AND br.month <= :toDate";

 
    public List<ReportDateEvolutionData> findReportDataWhereMonth(Long accountId, LocalDate month) {
        Query querySelect = entityManager.createNativeQuery(SELECT_REPORT_DATA_WHERE_MONTH_QUERY);
        querySelect.setParameter("accountId", accountId);
        querySelect.setParameter("month", month);
        List<Object[]> results = querySelect.getResultList();
        List<ReportDateEvolutionData> rdedResults = new ArrayList<>();
        for (Object[] res : results) {
            rdedResults.add(new ReportDateEvolutionData(res));
        }
        return rdedResults;
    }

    

    public List<ReportMonthlyData> findMonthlyReportDataWhereCategoryBetweenMonth(Long accountId, Long categoryId, LocalDate fromDate, LocalDate toDate) {
        Query querySelect = entityManager.createNativeQuery(SELECT_MONTHLY_REPORT_DATA_WHERE_CATEGORY_BETWEEN_MONTH_QUERY);
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
