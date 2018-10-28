package org.mgoulene.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import org.apache.commons.vfs2.AllFileSelector;
import org.apache.commons.vfs2.FileExtensionSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.mgoulene.config.ApplicationProperties;
import org.mgoulene.config.ApplicationProperties.ImportOperation;
import org.mgoulene.domain.BankAccount;
import org.mgoulene.domain.User;
import org.mgoulene.service.dto.BankAccountDTO;
import org.mgoulene.service.dto.OperationCSVDTO;
import org.mgoulene.service.dto.OperationDTO;
import org.mgoulene.service.dto.SubCategoryDTO;
import org.mgoulene.service.mapper.OperationCSVMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Importation of the Operations from CSV
 * File.
 */
@Service
@Transactional
public class OperationCSVImporterService {

    private final Logger log = LoggerFactory.getLogger(OperationCSVImporterService.class);

    private final OperationCSVMapper operationCSVMapper;

    private final SubCategoryService subCategoryService;

    private final BankAccountService bankAccountService;

    private final UserService userService;
    private final ImportOperation importOperation;

    // private final SFTP sftpConfig;

    private final OperationService operationService;

    public OperationCSVImporterService(OperationCSVMapper operationCSVMapper, SubCategoryService subCategoryService,
            UserService userService, ApplicationProperties applicationProperties, OperationService operationService, BankAccountService bankAccountService) {
        this.operationCSVMapper = operationCSVMapper;
        this.subCategoryService = subCategoryService;
        this.userService = userService;
        this.operationService = operationService;
        this.bankAccountService = bankAccountService;
        this.importOperation = applicationProperties.getImportOperation();
    }

    @Scheduled(fixedRate = 53600000)
    public void scheduleImportOperationCSVFile() {
        if (importOperation.isScheduleEnabled()) {
            log.debug("Schedule Import Operation CSV File enabled");
            importOperationCSVFileFromSFTP();
        } else {
            log.debug("Schedule Import Operation CSV File disabled");
        }
    }

    public void importOperationCSVFileFromSFTP() {

        String server = importOperation.getSFTP().getServer();
        int port = importOperation.getSFTP().getPort();
        String username = importOperation.getSFTP().getUsername();
        String password = importOperation.getSFTP().getPassword();

        StandardFileSystemManager manager = new StandardFileSystemManager();
        log.debug("Try to import Operation data from CSV File");
        try {
            manager.init();

            // Create Remote Folder
            FileObject remoteFolder = manager.resolveFile(
                    createConnectionString(server, port, username, password, "/home/in"), createDefaultOptions());
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
                        FileObject remoteAccountFolder = manager.resolveFile(
                                createConnectionString(server, port, username, password, "/home/in/" + folderName),
                                createDefaultOptions());
                        FileObject[] csvFiles = remoteAccountFolder.findFiles(new FileExtensionSelector("csv"));
                        for (FileObject csvFile : csvFiles) {
                            log.warn("Read file {}", csvFile.getName());
                            InputStream is = csvFile.getContent().getInputStream();
                            // import the csv file
                            importOperationCSVFile(accountId, is);
                            String date = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
                            FileObject remoteDoneFile = manager
                                    .resolveFile(
                                            createConnectionString(server, port, username, password, "/home/done/"
                                                    + folderName + "/" + date + "-" + csvFile.getName().getBaseName()),
                                            createDefaultOptions());
                            log.debug("Copy file {} to {}", csvFile, remoteDoneFile);
                            remoteDoneFile.copyFrom(csvFile, new AllFileSelector());
                            log.debug("Delete file {}", csvFile);
                            csvFile.delete();
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

        CsvToBean<OperationCSVDTO> csvToBean = new CsvToBeanBuilder<OperationCSVDTO>(
                new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_16))).withType(OperationCSVDTO.class)
                        .withSeparator('\t').withSkipLines(1).withKeepCarriageReturn(true)
                        .withIgnoreLeadingWhiteSpace(true).build();
        List<OperationCSVDTO> csvList = csvToBean.parse();
        // Get the HashMap subCategories
        Map<String, Long> subCategoriesMap = new HashMap<>();
        
        List<SubCategoryDTO> subCategories = subCategoryService.findAll();
        for (SubCategoryDTO subCategoryDTO : subCategories) {
            subCategoriesMap.put(subCategoryDTO.getSubCategoryName(), subCategoryDTO.getId());
        }

        // Get the HashMap of BankAccount

        Map<String, BankAccountDTO> bankAccountNameMap = new HashMap<>();
        List<BankAccountDTO> bankAccountDTOs = bankAccountService.findAllByAccountId(accountId);
        for (BankAccountDTO bankAccountDTO : bankAccountDTOs) {
            bankAccountNameMap.put(bankAccountDTO.getAccountName(), bankAccountDTO);
        }
        
        for (OperationCSVDTO csvDto : csvList) {
            // Get the subcategory id
            if (csvDto.getSubCategoryName() != null) {
                Long subCategoryId = subCategoriesMap.get(csvDto.getSubCategoryName());
                log.debug("Retrieving the subCategory Id {}, for the Name : {}", subCategoryId,
                        "\"" + csvDto.getSubCategoryName() + "\"");
                csvDto.setSubCategoryId(subCategoryId);
            } else {
                log.error("Error retrieving the SubCategory Name : {}", "\"" + csvDto.getSubCategoryName() + "\"");
            }
            String accountName = csvDto.getAccountName();
            BankAccountDTO bankAccountDTO = bankAccountNameMap.get(accountName);
            log.debug("BankAccount for {} is {}", accountName, bankAccountDTO);
            // Need to create a BankAccount if it does not exists
            if (bankAccountDTO == null) {
                bankAccountDTO = new BankAccountDTO();
                bankAccountDTO.setAccountBank(csvDto.getBankName());
                bankAccountDTO.setAccountName(csvDto.getAccountName());
                bankAccountDTO.setAccountId(accountId);
                bankAccountDTO.setInitialAmount(0f);
                bankAccountDTO = bankAccountService.save(bankAccountDTO);
                log.debug("Creating BankAccount : {}", bankAccountDTO);
                bankAccountNameMap.put(bankAccountDTO.getAccountName(), bankAccountDTO);
                
            }
            csvDto.setAccountId(accountId);
            csvDto.setBankAccountId(bankAccountDTO.getId());
            
        }

        

        List<OperationDTO> operationDTOs = operationCSVMapper.toDto(csvList);
        for (OperationDTO operationDTO: operationDTOs) {
            log.debug("OperationDTO : {}", operationDTO);
        }

        operationService.updateIsUpToDate(accountId);

        for (OperationDTO dto : operationDTOs) {
            operationService.importOperation(dto);
            // log.debug("Reading : {}", dto);
        }
        operationService.deleteIsNotUpToDate(accountId);

    }

    /**
     * Generates SFTP URL connection String
     *
     * @param hostName       HostName of the server
     * @param username       UserName to login
     * @param password       Password to login
     * @param remoteFilePath remoteFilePath. Should contain the entire remote file
     *                       path - Directory and Filename with / as separator
     * @return concatenated SFTP URL string
     */
    private String createConnectionString(String hostName, int port, String username, String password, String remoteFilePath) {
        return "sftp://" + username + ":" + password + "@" + hostName +":"+port +"/" + remoteFilePath;
    }

    /**
     * Method to setup default SFTP config
     *
     * @return the FileSystemOptions object containing the specified configuration
     *         options
     * @throws FileSystemException
     */
    private FileSystemOptions createDefaultOptions() throws FileSystemException {
        // Create SFTP options
        FileSystemOptions opts = new FileSystemOptions();

        // SSH Key checking
        SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");

        /*
         * Using the following line will cause VFS to choose File System's Root as VFS's
         * root. If I wanted to use User's home as VFS's root then set 2nd method
         * parameter to "true"
         */
        // Root directory set to user home
        SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, false);

        // Timeout is count by Milliseconds
        SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);

        return opts;
    }
}