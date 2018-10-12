package org.mgoulene.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.mgoulene.domain.BudgetItem;
import org.mgoulene.domain.BudgetItemPeriod;
import org.mgoulene.repository.AvailableDateRepository;
import org.mgoulene.repository.BudgetItemPeriodRepository;
import org.mgoulene.service.dto.BudgetItemDTO;
import org.mgoulene.service.dto.BudgetItemPeriodDTO;
import org.mgoulene.service.mapper.BudgetItemMapper;
import org.mgoulene.service.mapper.BudgetItemPeriodMapper;
import org.mgoulene.web.rest.util.LocalDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing BudgetItemPeriod.
 */
@Service
@Transactional
public class BudgetItemPeriodService {

    private final Logger log = LoggerFactory.getLogger(BudgetItemPeriodService.class);

    private final BudgetItemPeriodRepository budgetItemPeriodRepository;

    private final BudgetItemPeriodMapper budgetItemPeriodMapper;

    private final BudgetItemMapper budgetItemMapper;

    private final AvailableDateRepository availableDateRepository;

    public BudgetItemPeriodService(BudgetItemPeriodRepository budgetItemPeriodRepository,
            BudgetItemPeriodMapper budgetItemPeriodMapper, BudgetItemMapper budgetItemMapper,
            AvailableDateRepository availableDateRepository) {
        this.budgetItemPeriodRepository = budgetItemPeriodRepository;
        this.budgetItemPeriodMapper = budgetItemPeriodMapper;
        this.budgetItemMapper = budgetItemMapper;
        this.availableDateRepository = availableDateRepository;

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
        return budgetItemPeriodRepository.findAll(pageable).map(budgetItemPeriodMapper::toDto);
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
        return budgetItemPeriodRepository.findById(id).map(budgetItemPeriodMapper::toDto);
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

    public void createWithNext(BudgetItemDTO budgetItemDTO, BudgetItemPeriodDTO budgetItemPeriodDTO) {
        budgetItemPeriodDTO.setId(0L);
        BudgetItem budgetItem = budgetItemMapper.toEntity(budgetItemDTO);
        List<LocalDate> months = availableDateRepository.findAllMonthFrom(budgetItemPeriodDTO.getMonth());
        // Create all the BudgetItemPeriod from the parameter start month
        for (LocalDate month : months) {
            BudgetItemPeriod bip = budgetItemPeriodMapper.toEntity(budgetItemPeriodDTO);
            bip.setMonth(month);
            bip.setBudgetItem(budgetItem);
            if (!budgetItemPeriodDTO.isIsSmoothed()) {
                bip.setDate(LocalDateUtil.getLocalDate(month, budgetItemPeriodDTO.getDate().getDayOfMonth()));
            }
            budgetItemPeriodRepository.save(bip);
        }

    }

    public BudgetItemPeriodDTO findLastBudgetItemPeriod(Long budgetItemId) {
        log.debug("Request to findLastBudgetItemPeriod BudgetItemId : {}", budgetItemId);
        BudgetItemPeriod bip = budgetItemPeriodRepository.findLastBudgetItemPeriod(budgetItemId);

        return budgetItemPeriodMapper.toDto(bip);
    }

    public void extendWithNext(BudgetItemDTO budgetItemDTO) {
        log.debug("Request to extend BudgetItem : {}", budgetItemDTO);
        BudgetItemPeriodDTO budgetItemPeriodDTO = findLastBudgetItemPeriod(budgetItemDTO.getId());
        budgetItemPeriodDTO.setMonth(budgetItemPeriodDTO.getMonth().plusMonths(1));
        if (!budgetItemPeriodDTO.isIsSmoothed()) {
            budgetItemPeriodDTO.setDate(LocalDateUtil.getLocalDate(budgetItemPeriodDTO.getMonth(),
                budgetItemPeriodDTO.getDate().getDayOfMonth()));
        }
        createWithNext(budgetItemDTO, budgetItemPeriodDTO);
    }

    /**
     * Delete BudgetItemPeriod with the next.
     *
     * @param budgetItemPeriodDTO the entity to save
     * @return the persisted entity
     */
    public void deleteWithNext(BudgetItemPeriodDTO budgetItemPeriodDTO) {
        log.debug("Request to deleteWithNext BudgetItemPeriod : {}", budgetItemPeriodDTO);
        budgetItemPeriodRepository.deleteWithNext(budgetItemPeriodDTO.getBudgetItemId(),
                budgetItemPeriodDTO.getMonth());
    }

    

}
