package org.mgoulene.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mgoulene.service.MonthlyReportService;
import org.mgoulene.web.rest.errors.BadRequestAlertException;
import org.mgoulene.web.rest.util.HeaderUtil;
import org.mgoulene.service.dto.MonthlyReportDTO;
import org.mgoulene.service.dto.MonthlyReportCriteria;
import org.mgoulene.service.MonthlyReportQueryService;
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
 * REST controller for managing MonthlyReport.
 */
@RestController
@RequestMapping("/api")
public class MonthlyReportResource {

    private final Logger log = LoggerFactory.getLogger(MonthlyReportResource.class);

    private static final String ENTITY_NAME = "monthlyReport";

    private final MonthlyReportService monthlyReportService;

    private final MonthlyReportQueryService monthlyReportQueryService;

    public MonthlyReportResource(MonthlyReportService monthlyReportService, MonthlyReportQueryService monthlyReportQueryService) {
        this.monthlyReportService = monthlyReportService;
        this.monthlyReportQueryService = monthlyReportQueryService;
    }

    /**
     * POST  /monthly-reports : Create a new monthlyReport.
     *
     * @param monthlyReportDTO the monthlyReportDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new monthlyReportDTO, or with status 400 (Bad Request) if the monthlyReport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/monthly-reports")
    @Timed
    public ResponseEntity<MonthlyReportDTO> createMonthlyReport(@Valid @RequestBody MonthlyReportDTO monthlyReportDTO) throws URISyntaxException {
        log.debug("REST request to save MonthlyReport : {}", monthlyReportDTO);
        if (monthlyReportDTO.getId() != null) {
            throw new BadRequestAlertException("A new monthlyReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MonthlyReportDTO result = monthlyReportService.save(monthlyReportDTO);
        return ResponseEntity.created(new URI("/api/monthly-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /monthly-reports : Updates an existing monthlyReport.
     *
     * @param monthlyReportDTO the monthlyReportDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated monthlyReportDTO,
     * or with status 400 (Bad Request) if the monthlyReportDTO is not valid,
     * or with status 500 (Internal Server Error) if the monthlyReportDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/monthly-reports")
    @Timed
    public ResponseEntity<MonthlyReportDTO> updateMonthlyReport(@Valid @RequestBody MonthlyReportDTO monthlyReportDTO) throws URISyntaxException {
        log.debug("REST request to update MonthlyReport : {}", monthlyReportDTO);
        if (monthlyReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MonthlyReportDTO result = monthlyReportService.save(monthlyReportDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, monthlyReportDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /monthly-reports : get all the monthlyReports.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of monthlyReports in body
     */
    @GetMapping("/monthly-reports")
    @Timed
    public ResponseEntity<List<MonthlyReportDTO>> getAllMonthlyReports(MonthlyReportCriteria criteria) {
        log.debug("REST request to get MonthlyReports by criteria: {}", criteria);
        List<MonthlyReportDTO> entityList = monthlyReportQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /monthly-reports/:id : get the "id" monthlyReport.
     *
     * @param id the id of the monthlyReportDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the monthlyReportDTO, or with status 404 (Not Found)
     */
    @GetMapping("/monthly-reports/{id}")
    @Timed
    public ResponseEntity<MonthlyReportDTO> getMonthlyReport(@PathVariable Long id) {
        log.debug("REST request to get MonthlyReport : {}", id);
        Optional<MonthlyReportDTO> monthlyReportDTO = monthlyReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monthlyReportDTO);
    }

    /**
     * DELETE  /monthly-reports/:id : delete the "id" monthlyReport.
     *
     * @param id the id of the monthlyReportDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/monthly-reports/{id}")
    @Timed
    public ResponseEntity<Void> deleteMonthlyReport(@PathVariable Long id) {
        log.debug("REST request to delete MonthlyReport : {}", id);
        monthlyReportService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
