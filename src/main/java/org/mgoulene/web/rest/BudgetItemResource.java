package org.mgoulene.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mgoulene.service.BudgetItemService;
import org.mgoulene.web.rest.errors.BadRequestAlertException;
import org.mgoulene.web.rest.util.HeaderUtil;
import org.mgoulene.service.dto.BudgetItemDTO;
import org.mgoulene.service.dto.BudgetItemCriteria;
import org.mgoulene.service.BudgetItemQueryService;
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
 * REST controller for managing BudgetItem.
 */
@RestController
@RequestMapping("/api")
public class BudgetItemResource {

    private final Logger log = LoggerFactory.getLogger(BudgetItemResource.class);

    private static final String ENTITY_NAME = "budgetItem";

    private final BudgetItemService budgetItemService;

    private final BudgetItemQueryService budgetItemQueryService;

    public BudgetItemResource(BudgetItemService budgetItemService, BudgetItemQueryService budgetItemQueryService) {
        this.budgetItemService = budgetItemService;
        this.budgetItemQueryService = budgetItemQueryService;
    }

    /**
     * POST  /budget-items : Create a new budgetItem.
     *
     * @param budgetItemDTO the budgetItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new budgetItemDTO, or with status 400 (Bad Request) if the budgetItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/budget-items")
    @Timed
    public ResponseEntity<BudgetItemDTO> createBudgetItem(@Valid @RequestBody BudgetItemDTO budgetItemDTO) throws URISyntaxException {
        log.debug("REST request to save BudgetItem : {}", budgetItemDTO);
        if (budgetItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new budgetItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BudgetItemDTO result = budgetItemService.save(budgetItemDTO);
        return ResponseEntity.created(new URI("/api/budget-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /budget-items : Updates an existing budgetItem.
     *
     * @param budgetItemDTO the budgetItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated budgetItemDTO,
     * or with status 400 (Bad Request) if the budgetItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the budgetItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/budget-items")
    @Timed
    public ResponseEntity<BudgetItemDTO> updateBudgetItem(@Valid @RequestBody BudgetItemDTO budgetItemDTO) throws URISyntaxException {
        log.debug("REST request to update BudgetItem : {}", budgetItemDTO);
        if (budgetItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BudgetItemDTO result = budgetItemService.save(budgetItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, budgetItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /budget-items : get all the budgetItems.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of budgetItems in body
     */
    @GetMapping("/budget-items")
    @Timed
    public ResponseEntity<List<BudgetItemDTO>> getAllBudgetItems(BudgetItemCriteria criteria) {
        log.debug("REST request to get BudgetItems by criteria: {}", criteria);
        List<BudgetItemDTO> entityList = budgetItemQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /budget-items/count : count all the budgetItems.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/budget-items/count")
    @Timed
    public ResponseEntity<Long> countBudgetItems(BudgetItemCriteria criteria) {
        log.debug("REST request to count BudgetItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(budgetItemQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /budget-items/:id : get the "id" budgetItem.
     *
     * @param id the id of the budgetItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the budgetItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/budget-items/{id}")
    @Timed
    public ResponseEntity<BudgetItemDTO> getBudgetItem(@PathVariable Long id) {
        log.debug("REST request to get BudgetItem : {}", id);
        Optional<BudgetItemDTO> budgetItemDTO = budgetItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(budgetItemDTO);
    }

    /**
     * DELETE  /budget-items/:id : delete the "id" budgetItem.
     *
     * @param id the id of the budgetItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/budget-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteBudgetItem(@PathVariable Long id) {
        log.debug("REST request to delete BudgetItem : {}", id);
        budgetItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
