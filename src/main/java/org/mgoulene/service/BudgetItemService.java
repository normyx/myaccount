package org.mgoulene.service;

import org.mgoulene.domain.BudgetItem;
import org.mgoulene.repository.BudgetItemRepository;
import org.mgoulene.service.dto.BudgetItemDTO;
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

    public BudgetItemService(BudgetItemRepository budgetItemRepository, BudgetItemMapper budgetItemMapper) {
        this.budgetItemRepository = budgetItemRepository;
        this.budgetItemMapper = budgetItemMapper;
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
    }
}
