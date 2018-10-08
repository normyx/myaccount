package org.mgoulene.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.codahale.metrics.annotation.Timed;

import org.mgoulene.domain.User;
import org.mgoulene.service.BudgetItemQueryService;
import org.mgoulene.service.BudgetItemService;
import org.mgoulene.service.UserService;
import org.mgoulene.service.dto.BudgetItemCriteria;
import org.mgoulene.service.dto.BudgetItemDTO;
import org.mgoulene.service.dto.BudgetItemPeriodCriteria;
import org.mgoulene.service.dto.BudgetItemPeriodDTO;
import org.mgoulene.service.mapper.UserMapper;
import org.mgoulene.web.rest.errors.BadRequestAlertException;
import org.mgoulene.web.rest.util.HeaderUtil;
import org.mgoulene.web.rest.util.LocalDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.web.util.ResponseUtil;

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

    private final UserService userService;

    public BudgetItemResource(BudgetItemService budgetItemService, BudgetItemQueryService budgetItemQueryService,
            UserService userService) {
        this.budgetItemService = budgetItemService;
        this.budgetItemQueryService = budgetItemQueryService;
        this.userService = userService;
    }

    /**
     * POST /budget-items : Create a new budgetItem.
     *
     * @param budgetItemDTO the budgetItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new
     *         budgetItemDTO, or with status 400 (Bad Request) if the budgetItem has
     *         already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/budget-items")
    @Timed
    public ResponseEntity<BudgetItemDTO> createBudgetItem(@Valid @RequestBody BudgetItemDTO budgetItemDTO)
            throws URISyntaxException {
        log.debug("REST request to save BudgetItem : {}", budgetItemDTO);
        if (budgetItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new budgetItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BudgetItemDTO result = budgetItemService.save(budgetItemDTO);
        return ResponseEntity.created(new URI("/api/budget-items/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * PUT /budget-items : Updates an existing budgetItem.
     *
     * @param budgetItemDTO the budgetItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         budgetItemDTO, or with status 400 (Bad Request) if the budgetItemDTO
     *         is not valid, or with status 500 (Internal Server Error) if the
     *         budgetItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/budget-items")
    @Timed
    public ResponseEntity<BudgetItemDTO> updateBudgetItem(@Valid @RequestBody BudgetItemDTO budgetItemDTO)
            throws URISyntaxException {
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
     * GET /budget-items : get all the budgetItems.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of budgetItems
     *         in body
     */
    @GetMapping("/budget-items")
    @Timed
    public ResponseEntity<List<BudgetItemDTO>> getAllBudgetItems(BudgetItemCriteria criteria) {
        log.debug("REST request to get BudgetItems by criteria: {}", criteria);
        List<BudgetItemDTO> entityList = budgetItemQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET /budget-items/:id : get the "id" budgetItem.
     *
     * @param id the id of the budgetItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         budgetItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/budget-items/{id}")
    @Timed
    public ResponseEntity<BudgetItemDTO> getBudgetItem(@PathVariable Long id) {
        log.debug("REST request to get BudgetItem : {}", id);
        Optional<BudgetItemDTO> budgetItemDTO = budgetItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(budgetItemDTO);
    }

    /**
     * DELETE /budget-items/:id : delete the "id" budgetItem.
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

    /**
     * GET /budget-items/:id : get the "id" budgetItem.
     * 
     * @param id the id of the budgetItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         budgetItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/budget-eligible-items")
    @Timed
    public List<BudgetItemDTO> getEligibleBudgetItem(BudgetItemPeriodCriteria criteria) {
        log.debug("REST request to get BudgetItem : {}", criteria);
        return budgetItemService.findAllAvailableInPeriod(criteria.getMonth().getGreaterOrEqualThan(),
                criteria.getMonth().getLessOrEqualThan());
    }

    /**
     * POST /budget-items-with-periods : Create a new budgetItem with
     * budgetItemPeriod
     *
     * @param budgetItemDTO the budgetItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new
     *         budgetItemDTO, or with status 400 (Bad Request) if the budgetItem has
     *         already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/budget-items-with-periods/{is-smoothed}/{monthFrom}/{amount}/{day-in-month}")
    @Timed
    public ResponseEntity<BudgetItemDTO> createBudgetItemWithPeriods(@Valid @RequestBody BudgetItemDTO budgetItemDTO,
            @PathVariable(name = "monthFrom") LocalDate monthFrom,
            @PathVariable(name = "day-in-month") Integer dayInMonth,
            @PathVariable(name = "is-smoothed") Boolean isSmoothed, @PathVariable(name = "amount") Float amount)
            throws URISyntaxException {
        log.debug("REST request to save BudgetItem with period : {} {} {} {}", budgetItemDTO, monthFrom, isSmoothed,
                amount);
        if (budgetItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new budgetItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Optional<User> existingUser = userService.getUserWithAuthorities();
        if (existingUser.isPresent()) {
            System.out.println(existingUser.get());
            budgetItemDTO.setAccountId(existingUser.get().getId());
        }

        BudgetItemPeriodDTO budgetItemPeriodDTO = new BudgetItemPeriodDTO();
        budgetItemPeriodDTO.setMonth(monthFrom);
        budgetItemPeriodDTO.setAmount(amount);
        budgetItemPeriodDTO.setIsSmoothed(isSmoothed);
        budgetItemPeriodDTO.setIsRecurrent(true);
        if (dayInMonth != null && !isSmoothed) {
            budgetItemPeriodDTO.setDate(LocalDateUtil.getLocalDate(monthFrom, dayInMonth));
        }
        BudgetItemDTO result = budgetItemService.saveWithBudgetItemPeriod(budgetItemDTO, budgetItemPeriodDTO);
        return ResponseEntity.created(new URI("/api/budget-items/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PostMapping("/extend-budget-item-periods-and-next/{id}")
    @Timed
    public ResponseEntity<Void> extendBudgetItemPeriodAndNext(@PathVariable Long id) {
        log.debug("REST request to extend BudgetItemPeriod with BudgetItem: {}", id);
        Optional<BudgetItemDTO> budgetItemDTO = budgetItemService.findOne(id);
        if (budgetItemDTO.isPresent()) {
            budgetItemService.extendWithNext(budgetItemDTO.get());
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/last-budget-item-period/{id}")
    @Timed
    public ResponseEntity<BudgetItemPeriodDTO> getLastBudgetItemPeriod(@PathVariable Long id) {
        log.debug("REST request to get BudgetItem : {}", id);
        BudgetItemPeriodDTO budgetItemPeriodDTO = budgetItemService.findLastBudgetItemPeriod(id);
        return ResponseEntity.ok().body(budgetItemPeriodDTO);
    }

}
