package org.mgoulene.service;

import org.mgoulene.domain.BudgetItem;
import org.mgoulene.repository.BudgetItemRepository;
import org.mgoulene.repository.search.BudgetItemSearchRepository;
import org.mgoulene.service.dto.BudgetItemDTO;
import org.mgoulene.service.mapper.BudgetItemMapper;
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

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BudgetItem.
 */
@Service
@Transactional
public class BudgetItemService {

    private final Logger log = LoggerFactory.getLogger(BudgetItemService.class);

    private final BudgetItemRepository budgetItemRepository;

    private final BudgetItemMapper budgetItemMapper;

    private final BudgetItemSearchRepository budgetItemSearchRepository;

    public BudgetItemService(BudgetItemRepository budgetItemRepository, BudgetItemMapper budgetItemMapper, BudgetItemSearchRepository budgetItemSearchRepository) {
        this.budgetItemRepository = budgetItemRepository;
        this.budgetItemMapper = budgetItemMapper;
        this.budgetItemSearchRepository = budgetItemSearchRepository;
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
        BudgetItemDTO result = budgetItemMapper.toDto(budgetItem);
        budgetItemSearchRepository.save(budgetItem);
        return result;
    }

    /**
     * Get all the budgetItems.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BudgetItemDTO> findAll() {
        log.debug("Request to get all BudgetItems");
        return budgetItemRepository.findAll().stream()
            .map(budgetItemMapper::toDto)
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
        return budgetItemRepository.findById(id)
            .map(budgetItemMapper::toDto);
    }

    /**
     * Delete the budgetItem by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BudgetItem : {}", id);
        budgetItemRepository.deleteById(id);
        budgetItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the budgetItem corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BudgetItemDTO> findAllAvailableInPeriod(LocalDate monthFrom, LocalDate monthTo) { 
        log.debug("Request to search BudgetItems for query {}", monthFrom, monthTo); 
        return StreamSupport 
            .stream(budgetItemRepository.findAllAvailableInPeriod(monthFrom, monthTo).spliterator(), false) 
            .map(budgetItemMapper::toDto) 
            .collect(Collectors.toList()); 
    } 
 
 
    /** 
     * Search for the budgetItem corresponding to the query. 
     * 
     * @param query the query of the search 
     * @return the list of entities 
     */ 
    @Transactional(readOnly = true) 
    public List<BudgetItemDTO> search(String query) {
        log.debug("Request to search BudgetItems for query {}", query);
        return StreamSupport
            .stream(budgetItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(budgetItemMapper::toDto)
            .collect(Collectors.toList());
    }
}
