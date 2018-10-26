package org.mgoulene.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.github.stefanbirkner.fakesftpserver.rule.FakeSftpServerRule;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mgoulene.MyaccountApp;
import org.mgoulene.domain.Category;
import org.mgoulene.domain.Operation;
import org.mgoulene.domain.SubCategory;
import org.mgoulene.domain.User;
import org.mgoulene.domain.enumeration.CategoryType;
import org.mgoulene.repository.CategoryRepository;
import org.mgoulene.repository.OperationRepository;
import org.mgoulene.repository.SubCategoryRepository;
import org.mgoulene.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyaccountApp.class)
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OperationCSVImporterServiceIntTest {

    @Autowired
    private OperationCSVImporterService operationCSVImporterService;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    private Category cat1;

    private SubCategory subCat1;

    private SubCategory subCat2;

    @Rule
    public final FakeSftpServerRule sftpServer = new FakeSftpServerRule().addUser("user", "password").setPort(40015);

    @Before
    public void init() {
        cat1 = new Category();
        cat1.setCategoryName("Cat1");
        cat1.setCategoryType(CategoryType.SPENDING);
        subCat1 = new SubCategory();
        subCat1.setSubCategoryName("sc1");
        subCat2 = new SubCategory();
        subCat2.setSubCategoryName("sc2");
    }

    @Test
    @Transactional
    public void testAssertFirstImportCSVFile() throws IOException {
        // create Category and SubCategories
        cat1 = categoryRepository.saveAndFlush(cat1);
        subCat1.setCategory(cat1);
        subCategoryRepository.saveAndFlush(subCat1);
        subCat2.setCategory(cat1);
        subCategoryRepository.saveAndFlush(subCat2);
        User user = userRepository.findOneByLogin("mgoulene").get();
        // Import One Operation
        InputStream is = new ClassPathResource("./csv/op1.tsv").getInputStream();
        operationCSVImporterService.importOperationCSVFile(user.getId(), is);
        List<Operation> operations = operationRepository.findAll();
        assertTrue(operations.size() == 1);

        Operation operation = operations.get(0);
        Long subCatId = operation.getSubCategory().getId();
        Long opId = operation.getId();
        // Update operation by modifying the subCategory
        is = new ClassPathResource("./csv/op2.tsv").getInputStream();
        operationCSVImporterService.importOperationCSVFile(user.getId(), is);
        operations = operationRepository.findAll();
        assertTrue(operations.size() == 1);
        Operation newOperation = operations.get(0);
        assertTrue(opId == newOperation.getId());
        assertTrue(subCatId != newOperation.getSubCategory().getId());
        // Import with differetn date so delete the already imported & create new one
        is = new ClassPathResource("./csv/op3.tsv").getInputStream();
        operationCSVImporterService.importOperationCSVFile(user.getId(), is);
        operations = operationRepository.findAll();
        assertTrue(operations.size() == 1);
        newOperation = operations.get(0);
        assertTrue(opId != newOperation.getId());
    }

    @Test
    @Transactional
    public void testAssertSFTPServer() {
        try {
            // create Category and SubCategories
            cat1 = categoryRepository.saveAndFlush(cat1);
            subCat1.setCategory(cat1);
            subCategoryRepository.saveAndFlush(subCat1);
            subCat2.setCategory(cat1);
            subCategoryRepository.saveAndFlush(subCat2);
            User user = userRepository.findOneByLogin("mgoulene").get();
            // Import One Operation
            InputStream is = new ClassPathResource("./csv/op1.tsv").getInputStream();

            String operationString = IOUtils.toString(is, StandardCharsets.UTF_16);
            
            sftpServer.putFile("/home/in/mgoulene/operation.tsv", operationString, StandardCharsets.UTF_16);
            operationCSVImporterService.importOperationCSVFileFromSFTP();
            List<Operation> operations = operationRepository.findAll();
            assertTrue(operations.size() == 1);
            

            // Operation operation = operations.get(0);
            // Long subCatId = operation.getSubCategory().getId();
            // Long opId = operation.getId();
        } catch (IOException e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
