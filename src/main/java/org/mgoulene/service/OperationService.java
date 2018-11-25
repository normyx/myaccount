package org.mgoulene.service;

import org.mgoulene.domain.Operation;
import org.mgoulene.repository.OperationRepository;
import org.mgoulene.service.dto.OperationDTO;
import org.mgoulene.service.mapper.OperationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Operation.
 */
@Service
@Transactional
public class OperationService {

    private final Logger log = LoggerFactory.getLogger(OperationService.class);

    private final OperationRepository operationRepository;

    private final OperationMapper operationMapper;

    public OperationService(OperationRepository operationRepository, OperationMapper operationMapper) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
    }

    /**
     * Save a operation.
     *
     * @param operationDTO the entity to save
     * @return the persisted entity
     */
    public OperationDTO save(OperationDTO operationDTO) {
        log.debug("Request to save Operation : {}", operationDTO);

        Operation operation = operationMapper.toEntity(operationDTO);
        operation = operationRepository.save(operation);
        return operationMapper.toDto(operation);
    }

    /**
     * Get all the operations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OperationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Operations");
        return operationRepository.findAll(pageable).map(operationMapper::toDto);
    }

    /**
     * get all the operations where BudgetItem is null.
     * 
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OperationDTO> findAllWhereBudgetItemIsNull() {
        log.debug("Request to get all operations where BudgetItem is null");
        return StreamSupport.stream(operationRepository.findAll().spliterator(), false)
                .filter(operation -> operation.getBudgetItem() == null).map(operationMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one operation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<OperationDTO> findOne(Long id) {
        log.debug("Request to get Operation : {}", id);
        return operationRepository.findById(id).map(operationMapper::toDto);
    }

    /**
     * Delete the operation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Operation : {}", id);
        operationRepository.deleteById(id);
    }

    /**
     * Get all the operations that fits with the key date, amount and label and
     * accountId that is not uptodate
     *
     * @param date      the date
     * @param amount    the amount
     * @param label     the label
     * @param accountID the account id
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OperationDTO> findAllByDateLabelAmountAndAccountAndNotUpToDate(LocalDate date, float amount,
            String label, Long accountId) {
        log.debug("Request to get all Operations by date, label and amount");
        return StreamSupport.stream(operationRepository
                .findAllByDateAmountLabelAccountAndNotUpToDate(date, amount, label, accountId).spliterator(), false)
                .map(operationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Update all the operation from an accountId to isUpToDate to false.
     *
     * @param accountId the id of the userAccount
     */
    public int updateIsUpToDate(Long accountId) {
        log.debug("Request to update isUpToDate for Operation to false : {}", accountId);
        return operationRepository.updateIsUpToDate(accountId);
    }

    /**
     * Delete the operation from an account id where isUpToDate is false.
     *
     * @param accountId the id of the userAccount
     */
    public int deleteIsNotUpToDate(Long accountId) {
        log.debug("Request to update isUpToDate for Operation to false : {}", accountId);
        return operationRepository.deleteIsNotUpToDate(accountId);
    }

    /**
     * get all the operations where BudgetItem is null.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public int findCountAllCloseToBudgetItemPeriod(Long accountId, Long categoryId, float value,
            LocalDate dateFrom, LocalDate dateTo) {
        log.debug("Request to get all operations close to a findCountAllCloseToBudgetItemPeriod");
        return operationRepository
                .findAllCloseToBudgetItemPeriod(accountId, categoryId, value, dateFrom, dateTo).size();
    }
    /**
     * get all the operations where BudgetItem is null.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OperationDTO> findAllCloseToBudgetItemPeriod(Long accountId, Long categoryId, float value,
            LocalDate dateFrom, LocalDate dateTo) {
        log.debug("Request to get all operations close to a budgetItemPeriod");
        return StreamSupport.stream(operationRepository
                .findAllCloseToBudgetItemPeriod(accountId, categoryId, value, dateFrom, dateTo).spliterator(), false)
                .map(operationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public OperationDTO importOperation(OperationDTO operationDTO) {
        List<OperationDTO> results = findAllByDateLabelAmountAndAccountAndNotUpToDate(operationDTO.getDate(),
                operationDTO.getAmount(), operationDTO.getLabel(), operationDTO.getAccountId());
        OperationDTO operationToSave;
        if (!results.isEmpty()) {
            log.debug("Data already exists. Updatating : {} to {} ", results.get(0), operationDTO);
            // Only take the first one
            operationToSave = results.get(0);
            // if (operationDTO.isIdentical(operationToSave))
            operationToSave.setNote(operationDTO.getNote());
            operationToSave.setCheckNumber(operationDTO.getCheckNumber());
            operationToSave.setSubCategoryId(operationDTO.getSubCategoryId());

        } else {

            log.debug("Create data : {}", operationDTO);
            operationToSave = operationDTO;

        }

        operationToSave.setIsUpToDate(true);
        return save(operationToSave);

    }

    @Transactional(readOnly = true)
    public LocalDate findLastOperationDate(Long accountId) {
        log.debug("Request to findLastOperationDate");
        return operationRepository.findLastOperationDate(accountId);
    }

}
