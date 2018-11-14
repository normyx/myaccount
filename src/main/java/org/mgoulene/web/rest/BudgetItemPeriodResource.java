package org.mgoulene.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mgoulene.service.BudgetItemPeriodService;
import org.mgoulene.web.rest.errors.BadRequestAlertException;
import org.mgoulene.web.rest.util.HeaderUtil;
import org.mgoulene.web.rest.util.PaginationUtil;
import org.mgoulene.service.dto.BudgetItemPeriodDTO;
import org.mgoulene.service.dto.BudgetItemPeriodCriteria;
import org.mgoulene.service.BudgetItemPeriodQueryService;
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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BudgetItemPeriod.
 */
@RestController
@RequestMapping("/api")
public class BudgetItemPeriodResource {

    private final Logger log = LoggerFactory.getLogger(BudgetItemPeriodResource.class);

    private static final String ENTITY_NAME = "budgetItemPeriod";

    private final BudgetItemPeriodService budgetItemPeriodService;

    private final BudgetItemPeriodQueryService budgetItemPeriodQueryService;

    public BudgetItemPeriodResource(BudgetItemPeriodService budgetItemPeriodService, BudgetItemPeriodQueryService budgetItemPeriodQueryService) {
        this.budgetItemPeriodService = budgetItemPeriodService;
        this.budgetItemPeriodQueryService = budgetItemPeriodQueryService;
    }

    /**
     * POST  /budget-item-periods : Create a new budgetItemPeriod.
     *
     * @param budgetItemPeriodDTO the budgetItemPeriodDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new budgetItemPeriodDTO, or with status 400 (Bad Request) if the budgetItemPeriod has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/budget-item-periods")
    @Timed
    public ResponseEntity<BudgetItemPeriodDTO> createBudgetItemPeriod(@Valid @RequestBody BudgetItemPeriodDTO budgetItemPeriodDTO) throws URISyntaxException {
        log.debug("REST request to save BudgetItemPeriod : {}", budgetItemPeriodDTO);
        if (budgetItemPeriodDTO.getId() != null) {
            throw new BadRequestAlertException("A new budgetItemPeriod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BudgetItemPeriodDTO result = budgetItemPeriodService.save(budgetItemPeriodDTO);
        return ResponseEntity.created(new URI("/api/budget-item-periods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /budget-item-periods : Updates an existing budgetItemPeriod.
     *
     * @param budgetItemPeriodDTO the budgetItemPeriodDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated budgetItemPeriodDTO,
     * or with status 400 (Bad Request) if the budgetItemPeriodDTO is not valid,
     * or with status 500 (Internal Server Error) if the budgetItemPeriodDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/budget-item-periods")
    @Timed
    public ResponseEntity<BudgetItemPeriodDTO> updateBudgetItemPeriod(@Valid @RequestBody BudgetItemPeriodDTO budgetItemPeriodDTO) throws URISyntaxException {
        log.debug("REST request to update BudgetItemPeriod : {}", budgetItemPeriodDTO);
        if (budgetItemPeriodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BudgetItemPeriodDTO result = budgetItemPeriodService.save(budgetItemPeriodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, budgetItemPeriodDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /budget-item-periods : get all the budgetItemPeriods.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of budgetItemPeriods in body
     */
    @GetMapping("/budget-item-periods")
    @Timed
    public ResponseEntity<List<BudgetItemPeriodDTO>> getAllBudgetItemPeriods(BudgetItemPeriodCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BudgetItemPeriods by criteria: {}", criteria);
        Page<BudgetItemPeriodDTO> page = budgetItemPeriodQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/budget-item-periods");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /budget-item-periods/count : count all the budgetItemPeriods.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/budget-item-periods/count")
    @Timed
    public ResponseEntity<Long> countBudgetItemPeriods(BudgetItemPeriodCriteria criteria) {
        log.debug("REST request to count BudgetItemPeriods by criteria: {}", criteria);
        return ResponseEntity.ok().body(budgetItemPeriodQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /budget-item-periods/:id : get the "id" budgetItemPeriod.
     *
     * @param id the id of the budgetItemPeriodDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the budgetItemPeriodDTO, or with status 404 (Not Found)
     */
    @GetMapping("/budget-item-periods/{id}")
    @Timed
    public ResponseEntity<BudgetItemPeriodDTO> getBudgetItemPeriod(@PathVariable Long id) {
        log.debug("REST request to get BudgetItemPeriod : {}", id);
        Optional<BudgetItemPeriodDTO> budgetItemPeriodDTO = budgetItemPeriodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(budgetItemPeriodDTO);
    }

    /**
     * DELETE  /budget-item-periods/:id : delete the "id" budgetItemPeriod.
     *
     * @param id the id of the budgetItemPeriodDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/budget-item-periods/{id}")
    @Timed
    public ResponseEntity<Void> deleteBudgetItemPeriod(@PathVariable Long id) {
        log.debug("REST request to delete BudgetItemPeriod : {}", id);
        budgetItemPeriodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
