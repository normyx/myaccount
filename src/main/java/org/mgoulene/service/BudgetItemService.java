package org.mgoulene.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.mgoulene.domain.BudgetItem;
import org.mgoulene.repository.BudgetItemRepository;
import org.mgoulene.service.dto.BudgetItemDTO;
import org.mgoulene.service.dto.BudgetItemPeriodDTO;
import org.mgoulene.service.mapper.BudgetItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing BudgetItem.
 */
@Service
@Transactional
public class BudgetItemService {

    private final Logger log = LoggerFactory.getLogger(BudgetItemService.class);

    private final BudgetItemRepository budgetItemRepository;

    private final BudgetItemMapper budgetItemMapper;


    private final BudgetItemPeriodService budgetItemPeriodService;

    

    public BudgetItemService(BudgetItemRepository budgetItemRepository, BudgetItemMapper budgetItemMapper, BudgetItemPeriodService budgetItemPeriodService) {
        this.budgetItemRepository = budgetItemRepository;
        this.budgetItemMapper = budgetItemMapper;
        this.budgetItemPeriodService = budgetItemPeriodService;
    }

    /**
     * Save a budgetItem.
     *
     * @param budgetItemDTO the entity to save
     * @return the persisted entity
     */
    public BudgetItemDTO save(BudgetItemDTO budgetItemDTO) {
        log.debug("Request to save BudgetItem : {}", budgetItemDTO);

        BudgetItem budgetItem = budgetItemMapper.toEntity(budgetItemDTO);
        budgetItem = budgetItemRepository.save(budgetItem);
        return budgetItemMapper.toDto(budgetItem);
    }


    

    /**
     * Get all the budgetItems.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BudgetItemDTO> findAll() {
        log.debug("Request to get all BudgetItems");
        return budgetItemRepository.findAll().stream().map(budgetItemMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one budgetItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BudgetItemDTO> findOne(Long id) {
        log.debug("Request to get BudgetItem : {}", id);
        return budgetItemRepository.findById(id).map(budgetItemMapper::toDto);
    }

    /**
     * Delete the budgetItem by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BudgetItem : {}", id);
        budgetItemRepository.deleteById(id);
    }

    /**
     * Search for the budgetItem corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BudgetItemDTO> findAllAvailableInPeriod(LocalDate monthFrom, LocalDate monthTo) {
        log.debug("Request to search BudgetItems from {} to {}", monthFrom, monthTo);
        return StreamSupport
                .stream(budgetItemRepository.findAllAvailableInPeriod(monthFrom, monthTo).spliterator(), false)
                .map(budgetItemMapper::toDto).collect(Collectors.toList());
    }

       /**
     * Search for the budgetItem corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BudgetItemDTO> findAllAvailableInPeriod(LocalDate from, LocalDate to, Long accountId, String contains, Long categoryId) {
        log.debug("Request to search BudgetItems from {} to {} with {} {}", from, to, contains, categoryId);
        return StreamSupport
                .stream(budgetItemRepository.findAllAvailableInPeriod(from, to, accountId, contains, categoryId).spliterator(), false)
                .map(budgetItemMapper::toDto).collect(Collectors.toList());
    }

        /**
     * Save a budgetItem with BudgetItemPeriods from the date to all available
     *
     * @param budgetItemDTO       the entity to save
     * @param budgetItemPeriodDTO the start budgetItemPeriod that will be used to
     *                            create all the date
     * @return the persisted entity
     */
    public BudgetItemDTO saveWithBudgetItemPeriod(BudgetItemDTO budgetItemDTO, BudgetItemPeriodDTO budgetItemPeriodDTO) {
        log.debug("Request to save BudgetItem : {}", budgetItemDTO);
        BudgetItem budgetItem = budgetItemMapper.toEntity(budgetItemDTO);
        budgetItem = budgetItemRepository.save(budgetItem);
        BudgetItemDTO result = budgetItemMapper.toDto(budgetItem);
        budgetItemPeriodService.createWithNext(result, budgetItemPeriodDTO);
        return result;
    }

    public void extendWithNext(BudgetItemDTO budgetItemDTO) {
        log.debug("Request to extend BudgetItem : {}", budgetItemDTO);
        budgetItemPeriodService.extendWithNext(budgetItemDTO);
    }

    public BudgetItemPeriodDTO findLastBudgetItemPeriod(Long id) {
        log.debug("Request to find last BudgetItemPeriod of : {}", id);
        return budgetItemPeriodService.findLastBudgetItemPeriod(id);
    }

    public List<BudgetItemDTO> findPreviousOrderBudgetItem(BudgetItemDTO budgetItemDTO) {
        log.debug("Request to findPreviousOrderBudgetItem of : {}", budgetItemDTO);
        return budgetItemMapper.toDto(budgetItemRepository.findPreviousOrderBudgetItem(budgetItemDTO.getAccountId(), budgetItemDTO.getOrder()));
    }

    public List<BudgetItemDTO> findNextOrderBudgetItem(BudgetItemDTO budgetItemDTO) {
        log.debug("Request to findNextOrderBudgetItem of : {}", budgetItemDTO);
        return budgetItemMapper.toDto(budgetItemRepository.findNextOrderBudgetItem(budgetItemDTO.getAccountId(), budgetItemDTO.getOrder()));
    }

    public Integer findNewOrder(Long accountId) {
        log.debug("Request to findNextOrderBudgetItem of : {}", accountId);
        Integer order = budgetItemRepository.findNewOrder(accountId);
        return order == null ? 1 : order;
    }


}
