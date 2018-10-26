SELECT  
    CONCAT(br.account_id, 
            '-', 
            br.category_id, 
            '-', 
            br.month) AS id, 
    br.account_id AS account_id, 
    br.month AS month, 
    br.category_id AS category_id, 
    br.category_name AS category_name, 
    br.budget_amount AS budget_amount, 
    opr.amount AS amount, 
    opr.amount_avg_3 AS amount_avg3, 
    opr.amount_avg_12 AS amount_avg12 
FROM 
    (SELECT  
        bi.account_id AS account_id, 
            bi.category_id AS category_id, 
            cat.category_name AS category_name, 
            bip.month AS month, 
            SUM(bip.amount) AS budget_amount 
    FROM 
        (budget_item bi 
    JOIN budget_item_period bip ON bi.id = bip.budget_item_id 
    JOIN category cat ON cat.id = bi.category_id) 
    GROUP BY bi.account_id , bi.category_id , bip.month) AS br 
        LEFT JOIN 
    (SELECT  
        m1.month AS month, 
            m1.account_id AS account_id, 
            m1.category_id AS category_id, 
            m1.amount AS amount, 
            AVG(m2.amount) AS amount_avg_3, 
            AVG(m3.amount) AS amount_avg_12 
    FROM 
        (SELECT  
        PARSEDATETIME(CONCAT(YEAR(op1.jhi_date), '-', MONTH(op1.jhi_date), '-', '01'), 'yyyy-M-dd') AS month, 
            sc1.category_id AS category_id, 
            op1.account_id AS account_id, 
            SUM(op1.amount) AS amount 
    FROM operation op1
    JOIN sub_category sc1 ON op1.sub_category_id = sc1.id 
    GROUP BY month , sc1.category_id , op1.account_id) AS m1 
    JOIN (SELECT  
        PARSEDATETIME(CONCAT(YEAR(op2.jhi_date), '-', MONTH(op2.jhi_date), '-', '01'), 'yyyy-M-dd') AS month, 
            sc2.category_id AS category_id, 
            op2.account_id AS account_id, 
            SUM(op2.amount) AS amount 
    FROM 
        (operation op2
    JOIN sub_category sc2 ON op2.sub_category_id = sc2.id) 
    GROUP BY month , sc2.category_id , op2.account_id) AS m2 ON m1.category_id = m2.category_id 
        AND m1.account_id = m2.account_id 
    JOIN (SELECT  
        PARSEDATETIME(CONCAT(YEAR(op3.jhi_date), '-', MONTH(op3.jhi_date), '-', '01'), 'yyyy-M-dd') AS month, 
            sc3.category_id AS category_id, 
            op3.account_id AS account_id, 
            SUM(op3.amount) AS amount 
    FROM 
        (operation op3
    JOIN sub_category sc3 ON op3.sub_category_id = sc3.id) 
    GROUP BY month , sc3.category_id , op3.account_id) AS m3 ON m1.category_id = m3.category_id 
        AND m1.account_id = m3.account_id 
    WHERE 
        m2.month <= m1.month 
            AND m2.month >= DATEADD('MONTH',-2, m1.month) 
            AND m3.month <= m1.month 
            AND m3.month >= DATEADD('MONTH',-11, m1.month)  
    GROUP BY m1.month , m1.category_id , m1.account_id 
    ORDER BY m1.category_id , m1.month) AS opr ON br.account_id = opr.account_id 
        AND br.month = opr.month 
        AND br.category_id = opr.category_id 
WHERE br.account_id = :accountId AND br.category_id = :categoryId AND br.month >= :fromDate AND br.month <= :toDate
ORDER BY month ASC