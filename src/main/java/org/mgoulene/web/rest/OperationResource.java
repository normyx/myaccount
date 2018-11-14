package org.mgoulene.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mgoulene.service.OperationService;
import org.mgoulene.web.rest.errors.BadRequestAlertException;
import org.mgoulene.web.rest.util.HeaderUtil;
import org.mgoulene.web.rest.util.PaginationUtil;
import org.mgoulene.service.dto.OperationDTO;
import org.mgoulene.service.dto.BudgetItemDTO;
import org.mgoulene.service.dto.BudgetItemPeriodDTO;
import org.mgoulene.service.dto.OperationCriteria;
import org.mgoulene.service.BudgetItemPeriodService;
import org.mgoulene.service.BudgetItemService;
import org.mgoulene.service.OperationCSVImporterService;
import org.mgoulene.service.OperationQueryService;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Operation.
 */
@RestController
@RequestMapping("/api")
public class OperationResource {

    private final Logger log = LoggerFactory.getLogger(OperationResource.class);

    private static final String ENTITY_NAME = "operation";

    private final OperationService operationService;

    private final OperationQueryService operationQueryService;

    private final BudgetItemPeriodService budgetItemPeriodService;

    private final BudgetItemService budgetItemService;

    private final OperationCSVImporterService operationCSVImporterService;

    public OperationResource(OperationService operationService, OperationQueryService operationQueryService,
            BudgetItemPeriodService budgetItemPeriodService, BudgetItemService budgetItemService,
            OperationCSVImporterService operationCSVImporterService) {
        this.operationService = operationService;
        this.operationQueryService = operationQueryService;
        this.operationCSVImporterService = operationCSVImporterService;
        this.budgetItemService = budgetItemService;
        this.budgetItemPeriodService = budgetItemPeriodService;
    }

    /**
     * POST  /operations : Create a new operation.
     *
     * @param operationDTO the operationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operationDTO, or with status 400 (Bad Request) if the operation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operations")
    @Timed
    public ResponseEntity<OperationDTO> createOperation(@Valid @RequestBody OperationDTO operationDTO) throws URISyntaxException {
        log.debug("REST request to save Operation : {}", operationDTO);
        if (operationDTO.getId() != null) {
            throw new BadRequestAlertException("A new operation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperationDTO result = operationService.save(operationDTO);
        return ResponseEntity.created(new URI("/api/operations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operations : Updates an existing operation.
     *
     * @param operationDTO the operationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operationDTO,
     * or with status 400 (Bad Request) if the operationDTO is not valid,
     * or with status 500 (Internal Server Error) if the operationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operations")
    @Timed
    public ResponseEntity<OperationDTO> updateOperation(@Valid @RequestBody OperationDTO operationDTO) throws URISyntaxException {
        log.debug("REST request to update Operation : {}", operationDTO);
        if (operationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OperationDTO result = operationService.save(operationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operations : get all the operations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of operations in body
     */
    @GetMapping("/operations")
    @Timed
    public ResponseEntity<List<OperationDTO>> getAllOperations(OperationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Operations by criteria: {}", criteria);
        Page<OperationDTO> page = operationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /operations/count : count all the operations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/operations/count")
    @Timed
    public ResponseEntity<Long> countOperations(OperationCriteria criteria) {
        log.debug("REST request to count Operations by criteria: {}", criteria);
        return ResponseEntity.ok().body(operationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /operations/:id : get the "id" operation.
     *
     * @param id the id of the operationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/operations/{id}")
    @Timed
    public ResponseEntity<OperationDTO> getOperation(@PathVariable Long id) {
        log.debug("REST request to get Operation : {}", id);
        Optional<OperationDTO> operationDTO = operationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operationDTO);
    }

    /**
     * DELETE  /operations/:id : delete the "id" operation.
     *
     * @param id the id of the operationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operations/{id}")
    @Timed
    public ResponseEntity<Void> deleteOperation(@PathVariable Long id) {
        log.debug("REST request to delete Operation : {}", id);
        operationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
 


    /**
     * GET /operations-close-to-budget/:budget-item-period-id : get the "id"
     * operation.
     *
     * @param id the id of the budgetItemPeriodId to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         operationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/operations-close-to-budget/{budgetItemPeriodId}")
    @Timed
    public ResponseEntity<List<OperationDTO>> getOperationCloseToBudgetItemPeriod(
            @PathVariable Long budgetItemPeriodId) {
        log.debug("REST request to get Operation clode to budgetPeriodId : {}", budgetItemPeriodId);
        Optional<BudgetItemPeriodDTO> budgetItemPeriodDTOOptional = budgetItemPeriodService.findOne(budgetItemPeriodId);
        if (budgetItemPeriodDTOOptional.isPresent()) {
            BudgetItemPeriodDTO budgetItemPeriodDTO = budgetItemPeriodDTOOptional.get();
            Optional<BudgetItemDTO> budgetItemDTOOptional = budgetItemService
                    .findOne(budgetItemPeriodDTO.getBudgetItemId());
            if (budgetItemDTOOptional.isPresent()) {
                BudgetItemDTO budgetItemDTO = budgetItemDTOOptional.get();

                List<OperationDTO> operations = operationService.findAllCloseToBudgetItemPeriod(
                        budgetItemDTO.getAccountId(), budgetItemDTO.getCategoryId(),
                        budgetItemPeriodDTO.getAmount().floatValue(), budgetItemPeriodDTO.getDate().minusDays(20),
                        budgetItemPeriodDTO.getDate().plusDays(20));
                return new ResponseEntity<>(operations, HttpStatus.OK);
            } else {
                log.error("REST request cannot find BudgetItem : {}", budgetItemPeriodDTO.getBudgetItemId());
            }
        } else {
            log.error("REST request cannot find BudgetItemPeriod : {}", budgetItemPeriodId);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/import-operations-file")
    @Timed
    public void importOperationCSVFileFromSFTP() throws URISyntaxException {
        operationCSVImporterService.importOperationCSVFileFromSFTP();

    }

}
