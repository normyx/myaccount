package org.mgoulene.service;

import org.mgoulene.domain.BudgetItem;
import org.mgoulene.domain.BudgetItemPeriod;
import org.mgoulene.repository.AvailableDateRepository;
import org.mgoulene.repository.BudgetItemPeriodRepository;
import org.mgoulene.repository.BudgetItemRepository;
import org.mgoulene.service.dto.BudgetItemDTO;
import org.mgoulene.service.dto.BudgetItemPeriodDTO;
import org.mgoulene.service.mapper.BudgetItemMapper;
import org.mgoulene.service.mapper.BudgetItemPeriodMapper;
import org.mgoulene.web.rest.util.LocalDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        budgetItemPeriodService.createNext(result, budgetItemPeriodDTO);
        return result;
    }

  
    public void extend(BudgetItemDTO budgetItemDTO) {
        log.debug("Request to extend BudgetItem : {}", budgetItemDTO);
        budgetItemPeriodService.createNext(budgetItemDTO, findLastBudgetItemPeriod(budgetItemDTO));
    }

    public BudgetItemPeriodDTO findLastBudgetItemPeriod(BudgetItemDTO budgetItemDTO) {
        log.debug("Request to findLastBudgetItemPeriod : {}", budgetItemDTO);        
        return budgetItemPeriodService.findLastBudgetItemPeriod(budgetItemDTO.getId());
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

}
