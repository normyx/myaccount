package org.mgoulene.repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class AvailableDateRepositoryImpl implements AvailableDateRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<LocalDate> findAllMonthFrom(LocalDate fromMonth) {
        Query querySelect = entityManager.createNativeQuery("SELECT month FROM param_date WHERE month >= :fromMonth",
                LocalDate.class);
        querySelect.setParameter("fromMonth", fromMonth);
        return querySelect.getResultList();
    }
}