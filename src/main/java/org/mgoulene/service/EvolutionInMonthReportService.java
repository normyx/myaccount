package org.mgoulene.service;

import org.mgoulene.domain.EvolutionInMonthReport;
import org.mgoulene.repository.EvolutionInMonthReportRepository;
import org.mgoulene.service.dto.EvolutionInMonthReportDTO;
import org.mgoulene.service.mapper.EvolutionInMonthReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing EvolutionInMonthReport.
 */
@Service
@Transactional
public class EvolutionInMonthReportService {

    private final Logger log = LoggerFactory.getLogger(EvolutionInMonthReportService.class);

    private final EvolutionInMonthReportRepository evolutionInMonthReportRepository;

    private final EvolutionInMonthReportMapper evolutionInMonthReportMapper;

    public EvolutionInMonthReportService(EvolutionInMonthReportRepository evolutionInMonthReportRepository, EvolutionInMonthReportMapper evolutionInMonthReportMapper) {
        this.evolutionInMonthReportRepository = evolutionInMonthReportRepository;
        this.evolutionInMonthReportMapper = evolutionInMonthReportMapper;
    }

    /**
     * Save a evolutionInMonthReport.
     *
     * @param evolutionInMonthReportDTO the entity to save
     * @return the persisted entity
     */
    public EvolutionInMonthReportDTO save(EvolutionInMonthReportDTO evolutionInMonthReportDTO) {
        log.debug("Request to save EvolutionInMonthReport : {}", evolutionInMonthReportDTO);
        EvolutionInMonthReport evolutionInMonthReport = evolutionInMonthReportMapper.toEntity(evolutionInMonthReportDTO);
        evolutionInMonthReport = evolutionInMonthReportRepository.save(evolutionInMonthReport);
        return evolutionInMonthReportMapper.toDto(evolutionInMonthReport);
    }

    /**
     * Get all the evolutionInMonthReports.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EvolutionInMonthReportDTO> findAll() {
        log.debug("Request to get all EvolutionInMonthReports");
        return evolutionInMonthReportRepository.findAll().stream()
            .map(evolutionInMonthReportMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one evolutionInMonthReport by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EvolutionInMonthReportDTO> findOne(Long id) {
        log.debug("Request to get EvolutionInMonthReport : {}", id);
        return evolutionInMonthReportRepository.findById(id)
            .map(evolutionInMonthReportMapper::toDto);
    }

    /**
     * Delete the evolutionInMonthReport by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EvolutionInMonthReport : {}", id);
        evolutionInMonthReportRepository.deleteById(id);
    }
}
