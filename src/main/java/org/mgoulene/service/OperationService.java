package org.mgoulene.service;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.mgoulene.config.ApplicationProperties;
import org.mgoulene.config.ApplicationProperties.SFTP;
import org.mgoulene.domain.Operation;
import org.mgoulene.domain.User;
import org.mgoulene.repository.OperationRepository;
import org.mgoulene.service.dto.OperationCSVDTO;
import org.mgoulene.service.dto.OperationDTO;
import org.mgoulene.service.dto.SubCategoryCriteria;
import org.mgoulene.service.dto.SubCategoryDTO;
import org.mgoulene.service.mapper.OperationCSVMapper;
import org.mgoulene.service.mapper.OperationMapper;
import org.mgoulene.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import io.github.jhipster.service.filter.StringFilter;

/**
 * Service Implementation for managing Operation.
 */
@Service
@Transactional
public class OperationService {

    private final Logger log = LoggerFactory.getLogger(OperationService.class);

    private final OperationRepository operationRepository;

    private final OperationMapper operationMapper;

    private final OperationCSVMapper operationCSVMapper;

    private final SubCategoryService subCategoryService;

    private final UserService userService;

    private final SFTP sftpConfig;

    public OperationService(OperationRepository operationRepository, OperationMapper operationMapper,
            OperationCSVMapper operationCSVMapper, SubCategoryService subCategoryService, UserService userService, ApplicationProperties applicationProperties) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
        this.operationCSVMapper = operationCSVMapper;
        this.subCategoryService = subCategoryService;
        this.userService = userService;
        this.sftpConfig = applicationProperties.getSFTP();
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
            String label, String login) {
        log.debug("Request to get all Operations by date, label and amount");
        return StreamSupport
                .stream(operationRepository.findAllByDateAmountLabelAccountAndNotUpToDate(date, amount, label, login)
                        .spliterator(), false)
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
    public List<OperationDTO> findAllCloseToBudgetItemPeriod(Long accountId, Long categoryId, float value,
            LocalDate dateFrom, LocalDate dateTo) {
        log.debug("Request to get all operations close to a budgetItemPeriod");
        return StreamSupport.stream(operationRepository
                .findAllCloseToBudgetItemPeriod(accountId, categoryId, value, dateFrom, dateTo).spliterator(), false)
                .map(operationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Scheduled(fixedRate = 53600000)
    public void importOperationCSVFile() {
        String server = sftpConfig.getServer();
        String username = sftpConfig.getUsername();
        String password = sftpConfig.getPassword();

        StandardFileSystemManager manager = new StandardFileSystemManager();
        log.debug("Try to import Operation data from CSV File");
        try {
            manager.init();

            // Create Remote Folder
            FileObject remoteFolder = manager.resolveFile(createConnectionString(server, username, password, "/home/in"), createDefaultOptions());
            FileObject[] folders = remoteFolder.findFiles(new AllFileSelector());
            for (FileObject folder : folders) {
                if (folder.isFolder()) {
                    String folderName = folder.getName().getBaseName();
                    // Find user if exists
                    log.debug("Find user by login : {}", folderName);
                    Optional<User> userOptional = userService.getUserWithAuthoritiesByLogin(folderName);
                    if (userOptional.isPresent()) {
                        Long accountId = userOptional.get().getId();
                        // Get all files
                        FileObject remoteAccountFolder = manager.resolveFile(createConnectionString(server, username, password, "/home/in/"+folderName), createDefaultOptions());
                        FileObject[] csvFiles = remoteAccountFolder.findFiles(new FileExtensionSelector("csv"));
                        for (FileObject csvFile : csvFiles) {
                            log.warn("Read file {}", csvFile.getName());
                            InputStream is = csvFile.getContent().getInputStream();
                            // import the csv file
                            importOperationCSVFile(accountId, is);
                            String date = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
                            FileObject remoteDoneFile = manager.resolveFile(createConnectionString(server, username, password, "/home/done/"+folderName+"/"+date+"-"+csvFile.getName().getBaseName()), createDefaultOptions());
                            log.debug("Copy file {} to {}", csvFile, remoteDoneFile);
                            remoteDoneFile.copyFrom(csvFile, new AllFileSelector());
                            log.debug("Delete file {}", csvFile);
                            csvFile.delete();
                            //csvFile.moveTo(remoteDoneFile);
                        }

                    } else {
                        log.warn("User with login {} not found", folderName);
                    }

                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            manager.close();
        }
    }


    public void importOperationCSVFile(Long accountId, InputStream is) {

            CsvToBean<OperationCSVDTO> csvToBean = new CsvToBeanBuilder(
                    new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_16)))
                            .withType(OperationCSVDTO.class).withSeparator('\t').withSkipLines(1)
                            .withKeepCarriageReturn(true).withIgnoreLeadingWhiteSpace(true).build();
            List<OperationCSVDTO> csvList = csvToBean.parse();
            // Get the HashMap subCategories
            Map<String, Long> subCategoriesMap = new HashMap<String, Long>();
            List<SubCategoryDTO> subCategories =  subCategoryService.findAll();
            for (SubCategoryDTO subCategoryDTO : subCategories) {
                subCategoriesMap.put(subCategoryDTO.getSubCategoryName(), subCategoryDTO.getId());
            }
            // Get the subcategory id
            for (OperationCSVDTO csvDto : csvList) {
                /*SubCategoryCriteria crit = new SubCategoryCriteria();
                StringFilter subCatNameFilter = new StringFilter();
                subCatNameFilter.setEquals(csvDto.getSubCategoryName());
                crit.setSubCategoryName(subCatNameFilter);
                List<SubCategoryDTO> subCategories = subCategoryQueryService.findByCriteria(crit);*/
                if (csvDto.getSubCategoryName() != null) {
                    Long subCategoryId = subCategoriesMap.get(csvDto.getSubCategoryName());
                    log.debug("Retrieving the subCategory Id {}, for the Name : {}", subCategoryId,
                            "\"" + csvDto.getSubCategoryName() + "\"");
                    csvDto.setSubCategoryId(subCategoryId);
                } else {
                    log.error("Error retrieving the SubCategory Name : {}", "\"" + csvDto.getSubCategoryName() + "\"");
                }
                csvDto.setAccountId(accountId);
            }

            List<OperationDTO> operationDTOs = operationCSVMapper.toDto(csvList);

            updateIsUpToDate(accountId);

            for (OperationDTO dto : operationDTOs) {
                importOperation(dto);
                //log.debug("Reading : {}", dto);
            }
            deleteIsNotUpToDate(accountId);

    }

    public OperationDTO importOperation(OperationDTO operationDTO)  {
        List<OperationDTO> results = findAllByDateLabelAmountAndAccountAndNotUpToDate(
                operationDTO.getDate(), operationDTO.getAmount(), operationDTO.getLabel(),
                operationDTO.getAccountLogin());
        OperationDTO operationToSave;
        if (!results.isEmpty()) {
            log.debug("Data already exists. Updatating : {} to {} ", results.get(0), operationDTO);
            // Only take the first one
            operationToSave = results.get(0);
            //if (operationDTO.isIdentical(operationToSave))
            operationToSave.setNote(operationDTO.getNote());
            operationToSave.setCheckNumber(operationDTO.getCheckNumber());
            operationToSave.setSubCategoryId(operationDTO.getSubCategoryId());

        } else {

            log.debug("Create data : {}", operationDTO);
            operationToSave = operationDTO;

        }

        operationToSave.setIsUpToDate(true);
        OperationDTO result = save(operationToSave);
        return result;
        

    }

    /**
     * Generates SFTP URL connection String
     *
     * @param hostName
     *            HostName of the server
     * @param username
     *            UserName to login
     * @param password
     *            Password to login
     * @param remoteFilePath
     *            remoteFilePath. Should contain the entire remote file path -
     *            Directory and Filename with / as separator
     * @return concatenated SFTP URL string
     */
    private String createConnectionString(String hostName, String username, String password, String remoteFilePath) {
        return "sftp://" + username + ":" + password + "@" + hostName + "/" + remoteFilePath;
    }

    /**
     * Method to setup default SFTP config
     *
     * @return the FileSystemOptions object containing the specified
     *         configuration options
     * @throws FileSystemException
     */
    private FileSystemOptions createDefaultOptions() throws FileSystemException {
        // Create SFTP options
        FileSystemOptions opts = new FileSystemOptions();

        // SSH Key checking
        SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");

        /*
         * Using the following line will cause VFS to choose File System's Root
         * as VFS's root. If I wanted to use User's home as VFS's root then set
         * 2nd method parameter to "true"
         */
        // Root directory set to user home
        SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, false);

        // Timeout is count by Milliseconds
        SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);

        return opts;
    }
 

}
