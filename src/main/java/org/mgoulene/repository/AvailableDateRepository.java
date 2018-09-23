package org.mgoulene.repository;

import java.time.LocalDate;
import java.util.List;

public interface AvailableDateRepository {

    public List<LocalDate> findAllMonthFrom(LocalDate fromMonth);
}