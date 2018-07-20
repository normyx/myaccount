package org.mgoulene.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.service.filter.StringFilter;
import org.mgoulene.service.OperationService;
import org.mgoulene.service.SubCategoryQueryService;
import org.mgoulene.service.dto.SubCategoryCriteria;
import org.mgoulene.service.dto.SubCategoryDTO;
import org.mgoulene.web.rest.errors.BadRequestAlertException;
import org.mgoulene.web.rest.util.HeaderUtil;
import org.mgoulene.web.rest.util.PaginationUtil;
import org.mgoulene.service.dto.OperationDTO;
import org.mgoulene.service.dto.BudgetItemDTO;
import org.mgoulene.service.dto.BudgetItemPeriodDTO;
import org.mgoulene.service.dto.OperationCriteria;
import org.mgoulene.service.BudgetItemPeriodService;
import org.mgoulene.service.BudgetItemService;
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

import static org.elasticsearch.index.query.QueryBuilders.*;

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

    private final SubCategoryQueryService subCategoryQueryService;

    private final BudgetItemPeriodService budgetItemPeriodService; 
 
    private final BudgetItemService budgetItemService; 

    public OperationResource(OperationService operationService, OperationQueryService operationQueryService, 
            SubCategoryQueryService subCategoryQueryService, BudgetItemPeriodService budgetItemPeriodService, 
            BudgetItemService budgetItemService) { 
        this.operationService = operationService;
        this.operationQueryService = operationQueryService;
        this.subCategoryQueryService = subCategoryQueryService;
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
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
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
     * SEARCH  /_search/operations?query=:query : search for the operation corresponding
     * to the query.
     *
     * @param query the query of the operation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/operations")
    @Timed
    public ResponseEntity<List<OperationDTO>> searchOperations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Operations for query {}", query);
        Page<OperationDTO> page = operationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/operations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * PUT  /import-operations : Updates an existing operation.
     *
     * @param operationDTO the operationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operationDTO,
     * or with status 400 (Bad Request) if the operationDTO is not valid,
     * or with status 500 (Internal Server Error) if the operationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/import-operations")
    @Timed
    public ResponseEntity<OperationDTO> importOperation(@Valid @RequestBody OperationDTO operationDTO) throws URISyntaxException {
        /*log.debug("REST request to update Operation : {}", operationDTO);
        if (operationDTO.getId() == null) {
            return createOperation(operationDTO);
        }
        OperationDTO result = operationService.save(operationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operationDTO.getId().toString()))
            .body(result);*/
        List<OperationDTO> results = operationService.findAllByDateLabelAmountAndAccountAndNotUpToDate(operationDTO.getDate(), operationDTO.getAmount(), operationDTO.getLabel(), operationDTO.getAccountLogin());
        OperationDTO operationToSave;
        if (results.size() > 0) {
            log.debug("Data already exists. Updatating : " + results.get(0) + "to " + operationDTO);
            // Only take the first one
            operationToSave = results.get(0);
            operationToSave.setNote(operationDTO.getNote());
            operationToSave.setCheckNumber(operationDTO.getCheckNumber());

        } else {

            log.debug("Create data :"+operationDTO);
            operationToSave = operationDTO;

        }
        // Get Sub Category by Name
        SubCategoryCriteria subCategoryCriteria = new SubCategoryCriteria();
        StringFilter sf = new StringFilter();
        sf.setEquals(operationDTO.getSubCategorySubCategoryName());
        subCategoryCriteria.setSubCategoryName(sf);
        List<SubCategoryDTO> subCategoryList = subCategoryQueryService.findByCriteria(subCategoryCriteria);
        if (subCategoryList.size() == 1) {
            operationToSave.setSubCategoryId(subCategoryList.get(0).getId());

        }
        operationToSave.setIsUpToDate(true);
        OperationDTO result = operationService.save(operationToSave);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);

    }

    /**
     * GET  /operations/:id : get the "id" operation.
     *
     * @param accountId the login of the User to delete
     * @return the ResponseEntity with status 200 (OK) and with body the operationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/delete-operations-unimported/{accountId}")
    @Timed
    public int deleteIsNotUpToDate(@PathVariable Long accountId) {
        log.debug("REST request to get Operation : {}", accountId);
        return operationService.deleteIsNotUpToDate(accountId);
    }

    /**
     * GET  /operations/:id : get the "id" operation.
     *
     * @param accountId the id of the User to update
     * @return the ResponseEntity with status 200 (OK) and with body the operationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reset-operations-for-import/{accountId}")
    @Timed
    public int updateIsUpToDate(@PathVariable Long accountId) {
        log.debug("REST request to get Operation : {}", accountId);
        return operationService.updateIsUtToDate(accountId);
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
        BudgetItemPeriodDTO budgetItemPeriodDTO = budgetItemPeriodService.findOne(budgetItemPeriodId).get(); 
        BudgetItemDTO budgetItemDTO = budgetItemService.findOne(budgetItemPeriodDTO.getBudgetItemId()).get(); 
 
        List<OperationDTO> operations = operationService.findAllCloseToBudgetItemPeriod( 
                budgetItemDTO.getAccountId(), budgetItemDTO.getCategoryId(), budgetItemPeriodDTO.getAmount().floatValue(), 
                budgetItemPeriodDTO.getDate().minusDays(20), 
                budgetItemPeriodDTO.getDate().plusDays(20)); 
        return new ResponseEntity<>(operations, HttpStatus.OK); 
    } 

}
