package org.mgoulene.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mgoulene.service.ReportDataByDateService;
import org.mgoulene.web.rest.errors.BadRequestAlertException;
import org.mgoulene.web.rest.util.HeaderUtil;
import org.mgoulene.web.rest.util.PaginationUtil;
import org.mgoulene.service.dto.ReportDataByDateDTO;
import org.mgoulene.service.dto.CategoryDTO;
import org.mgoulene.service.dto.ReportDataByDateCriteria;
import org.mgoulene.domain.ReportDateEvolutionData;
import org.mgoulene.service.CategoryService;
import org.mgoulene.service.ReportDataByDateQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing ReportDataByDate.
 */
@RestController
@RequestMapping("/api")
public class ReportDataByDateResource {

    private final Logger log = LoggerFactory.getLogger(ReportDataByDateResource.class);

    private static final String ENTITY_NAME = "reportDataByDate";

    private final ReportDataByDateService reportDataByDateService;

    private final ReportDataByDateQueryService reportDataByDateQueryService;

    private final CategoryService categoryService;

    public ReportDataByDateResource(ReportDataByDateService reportDataByDateService, ReportDataByDateQueryService reportDataByDateQueryService, CategoryService categoryService) {
        this.reportDataByDateService = reportDataByDateService;
        this.reportDataByDateQueryService = reportDataByDateQueryService;
        this.categoryService = categoryService;
    }

    /**
     * POST  /report-data-by-dates : Create a new reportDataByDate.
     *
     * @param reportDataByDateDTO the reportDataByDateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reportDataByDateDTO, or with status 400 (Bad Request) if the reportDataByDate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/report-data-by-dates")
    @Timed
    public ResponseEntity<ReportDataByDateDTO> createReportDataByDate(@Valid @RequestBody ReportDataByDateDTO reportDataByDateDTO) throws URISyntaxException {
        log.debug("REST request to save ReportDataByDate : {}", reportDataByDateDTO);
        if (reportDataByDateDTO.getId() != null) {
            throw new BadRequestAlertException("A new reportDataByDate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportDataByDateDTO result = reportDataByDateService.save(reportDataByDateDTO);
        return ResponseEntity.created(new URI("/api/report-data-by-dates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /report-data-by-dates : Updates an existing reportDataByDate.
     *
     * @param reportDataByDateDTO the reportDataByDateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reportDataByDateDTO,
     * or with status 400 (Bad Request) if the reportDataByDateDTO is not valid,
     * or with status 500 (Internal Server Error) if the reportDataByDateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/report-data-by-dates")
    @Timed
    public ResponseEntity<ReportDataByDateDTO> updateReportDataByDate(@Valid @RequestBody ReportDataByDateDTO reportDataByDateDTO) throws URISyntaxException {
        log.debug("REST request to update ReportDataByDate : {}", reportDataByDateDTO);
        if (reportDataByDateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportDataByDateDTO result = reportDataByDateService.save(reportDataByDateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reportDataByDateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /report-data-by-dates : get all the reportDataByDates.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of reportDataByDates in body
     */
    @GetMapping("/report-data-by-dates")
    @Timed
    public ResponseEntity<List<ReportDataByDateDTO>> getAllReportDataByDates(ReportDataByDateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ReportDataByDates by criteria: {}", criteria);
        Page<ReportDataByDateDTO> page = reportDataByDateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/report-data-by-dates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /report-data-by-dates/:id : get the "id" reportDataByDate.
     *
     * @param id the id of the reportDataByDateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reportDataByDateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/report-data-by-dates/{id}")
    @Timed
    public ResponseEntity<ReportDataByDateDTO> getReportDataByDate(@PathVariable Long id) {
        log.debug("REST request to get ReportDataByDate : {}", id);
        Optional<ReportDataByDateDTO> reportDataByDateDTO = reportDataByDateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportDataByDateDTO);
    }

    /**
     * DELETE  /report-data-by-dates/:id : delete the "id" reportDataByDate.
     *
     * @param id the id of the reportDataByDateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/report-data-by-dates/{id}")
    @Timed
    public ResponseEntity<Void> deleteReportDataByDate(@PathVariable Long id) {
        log.debug("REST request to delete ReportDataByDate : {}", id);
        reportDataByDateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /refresh-report-data/:accountId : refresh reportDataByDate of account accountId.
     *
     * @param accountId the id of USer to refresh
     */
    @GetMapping("/refresh-report-data/{accountId}")
    @Timed
    public void refreshData(@PathVariable Long accountId) {
        log.debug("REST request to update ReportDataByDate from  : {}", accountId);
        log.debug("Getting all categories");
        List<CategoryDTO> categories = categoryService.findAll();
        log.debug("Getting all categories : {}", categories);
        List<Long> categoryIds = categories.stream().map(CategoryDTO::getId).collect(Collectors.toList());
        log.debug("Getting all categories converted to List<Long>: {}", categoryIds);
        reportDataByDateService.refreshData(accountId, categoryIds);
        
    }

        /**
     * GET  /refresh-report-data/:accountId : refresh reportDataByDate of account accountId.
     *
     * @param accountId the id of USer to refresh
     */
    @GetMapping("/get-report-data-in-a-month/{month}")
    @Timed
    public ResponseEntity<List<ReportDateEvolutionData>> getReportDataByDatesInMonth(@PathVariable LocalDate month) {
        List<ReportDateEvolutionData> results = reportDataByDateService.findByAccountIsCurrentUserAndMonth(month);
        return new ResponseEntity<>(results, HttpStatus.OK);
        
    }

}
