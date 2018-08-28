package org.mgoulene.service;

import org.mgoulene.domain.BudgetItemPeriod;
import org.mgoulene.repository.BudgetItemPeriodRepository;
import org.mgoulene.service.dto.BudgetItemPeriodDTO;
import org.mgoulene.service.mapper.BudgetItemPeriodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing BudgetItemPeriod.
 */
@Service
@Transactional
public class BudgetItemPeriodService {

    private final Logger log = LoggerFactory.getLogger(BudgetItemPeriodService.class);

    private final BudgetItemPeriodRepository budgetItemPeriodRepository;

    private final BudgetItemPeriodMapper budgetItemPeriodMapper;

    

    public BudgetItemPeriodService(BudgetItemPeriodRepository budgetItemPeriodRepository, BudgetItemPeriodMapper budgetItemPeriodMapper) {
        this.budgetItemPeriodRepository = budgetItemPeriodRepository;
        this.budgetItemPeriodMapper = budgetItemPeriodMapper;
    }

    /**
     * Save a budgetItemPeriod.
     *
     * @param budgetItemPeriodDTO the entity to save
     * @return the persisted entity
     */
    public void updateWithNext(BudgetItemPeriodDTO budgetItemPeriodDTO) { 
        log.debug("Request to updateWithNext BudgetItemPeriod : {}", budgetItemPeriodDTO); 
        budgetItemPeriodRepository.updateWithNext(budgetItemPeriodDTO.isIsSmoothed(), budgetItemPeriodDTO.getAmount(), 
                budgetItemPeriodDTO.getBudgetItemId(), budgetItemPeriodDTO.getMonth()); 
    } 
 
    /** 
     * Save a budgetItemPeriod. 
     * 
     * @param budgetItemPeriodDTO the entity to save 
     * @return the persisted entity 
     */ 
    public BudgetItemPeriodDTO save(BudgetItemPeriodDTO budgetItemPeriodDTO) {
        log.debug("Request to save BudgetItemPeriod : {}", budgetItemPeriodDTO);
        BudgetItemPeriod budgetItemPeriod = budgetItemPeriodMapper.toEntity(budgetItemPeriodDTO);
        budgetItemPeriod = budgetItemPeriodRepository.save(budgetItemPeriod);
        return budgetItemPeriodMapper.toDto(budgetItemPeriod);
    }

    /**
     * Get all the budgetItemPeriods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BudgetItemPeriodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BudgetItemPeriods");
        return budgetItemPeriodRepository.findAll(pageable)
            .map(budgetItemPeriodMapper::toDto);
    }


    /**
     * Get one budgetItemPeriod by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BudgetItemPeriodDTO> findOne(Long id) {
        log.debug("Request to get BudgetItemPeriod : {}", id);
        return budgetItemPeriodRepository.findById(id)
            .map(budgetItemPeriodMapper::toDto);
    }

    /**
     * Delete the budgetItemPeriod by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BudgetItemPeriod : {}", id);
        budgetItemPeriodRepository.deleteById(id);
    }

    /** 
     * Save a budgetItemPeriod. 
     * 
     * @param budgetItemPeriodDTOs the entities to save 
     * @return the persisted entity 
     */ 
    public List<BudgetItemPeriodDTO> save(List<BudgetItemPeriodDTO> budgetItemPeriodDTOs) { 
        log.debug("Request to save BudgetItemPeriods : {}", budgetItemPeriodDTOs); 
        List<BudgetItemPeriod> budgetItemPeriods = budgetItemPeriodMapper.toEntity(budgetItemPeriodDTOs); 
        budgetItemPeriods = budgetItemPeriodRepository.saveAll(budgetItemPeriods); 
        return budgetItemPeriodMapper.toDto(budgetItemPeriods); 
    } 

}
