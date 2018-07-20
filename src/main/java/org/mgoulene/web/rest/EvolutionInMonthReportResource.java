package org.mgoulene.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mgoulene.service.EvolutionInMonthReportService;
import org.mgoulene.web.rest.errors.BadRequestAlertException;
import org.mgoulene.web.rest.util.HeaderUtil;
import org.mgoulene.service.dto.EvolutionInMonthReportDTO;
import org.mgoulene.service.dto.EvolutionInMonthReportCriteria;
import org.mgoulene.service.EvolutionInMonthReportQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EvolutionInMonthReport.
 */
@RestController
@RequestMapping("/api")
public class EvolutionInMonthReportResource {

    private final Logger log = LoggerFactory.getLogger(EvolutionInMonthReportResource.class);

    private static final String ENTITY_NAME = "evolutionInMonthReport";

    private final EvolutionInMonthReportService evolutionInMonthReportService;

    private final EvolutionInMonthReportQueryService evolutionInMonthReportQueryService;

    public EvolutionInMonthReportResource(EvolutionInMonthReportService evolutionInMonthReportService, EvolutionInMonthReportQueryService evolutionInMonthReportQueryService) {
        this.evolutionInMonthReportService = evolutionInMonthReportService;
        this.evolutionInMonthReportQueryService = evolutionInMonthReportQueryService;
    }

    /**
     * POST  /evolution-in-month-reports : Create a new evolutionInMonthReport.
     *
     * @param evolutionInMonthReportDTO the evolutionInMonthReportDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evolutionInMonthReportDTO, or with status 400 (Bad Request) if the evolutionInMonthReport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/evolution-in-month-reports")
    @Timed
    public ResponseEntity<EvolutionInMonthReportDTO> createEvolutionInMonthReport(@Valid @RequestBody EvolutionInMonthReportDTO evolutionInMonthReportDTO) throws URISyntaxException {
        log.debug("REST request to save EvolutionInMonthReport : {}", evolutionInMonthReportDTO);
        if (evolutionInMonthReportDTO.getId() != null) {
            throw new BadRequestAlertException("A new evolutionInMonthReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvolutionInMonthReportDTO result = evolutionInMonthReportService.save(evolutionInMonthReportDTO);
        return ResponseEntity.created(new URI("/api/evolution-in-month-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evolution-in-month-reports : Updates an existing evolutionInMonthReport.
     *
     * @param evolutionInMonthReportDTO the evolutionInMonthReportDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evolutionInMonthReportDTO,
     * or with status 400 (Bad Request) if the evolutionInMonthReportDTO is not valid,
     * or with status 500 (Internal Server Error) if the evolutionInMonthReportDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/evolution-in-month-reports")
    @Timed
    public ResponseEntity<EvolutionInMonthReportDTO> updateEvolutionInMonthReport(@Valid @RequestBody EvolutionInMonthReportDTO evolutionInMonthReportDTO) throws URISyntaxException {
        log.debug("REST request to update EvolutionInMonthReport : {}", evolutionInMonthReportDTO);
        if (evolutionInMonthReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EvolutionInMonthReportDTO result = evolutionInMonthReportService.save(evolutionInMonthReportDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, evolutionInMonthReportDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evolution-in-month-reports : get all the evolutionInMonthReports.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of evolutionInMonthReports in body
     */
    @GetMapping("/evolution-in-month-reports")
    @Timed
    public ResponseEntity<List<EvolutionInMonthReportDTO>> getAllEvolutionInMonthReports(EvolutionInMonthReportCriteria criteria) {
        log.debug("REST request to get EvolutionInMonthReports by criteria: {}", criteria);
        List<EvolutionInMonthReportDTO> entityList = evolutionInMonthReportQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /evolution-in-month-reports/:id : get the "id" evolutionInMonthReport.
     *
     * @param id the id of the evolutionInMonthReportDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evolutionInMonthReportDTO, or with status 404 (Not Found)
     */
    @GetMapping("/evolution-in-month-reports/{id}")
    @Timed
    public ResponseEntity<EvolutionInMonthReportDTO> getEvolutionInMonthReport(@PathVariable Long id) {
        log.debug("REST request to get EvolutionInMonthReport : {}", id);
        Optional<EvolutionInMonthReportDTO> evolutionInMonthReportDTO = evolutionInMonthReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evolutionInMonthReportDTO);
    }

    /**
     * DELETE  /evolution-in-month-reports/:id : delete the "id" evolutionInMonthReport.
     *
     * @param id the id of the evolutionInMonthReportDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/evolution-in-month-reports/{id}")
    @Timed
    public ResponseEntity<Void> deleteEvolutionInMonthReport(@PathVariable Long id) {
        log.debug("REST request to delete EvolutionInMonthReport : {}", id);
        evolutionInMonthReportService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
