package org.mgoulene.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mgoulene.MyaccountApp;
import org.mgoulene.domain.Operation;
import org.mgoulene.domain.User;
import org.mgoulene.repository.OperationRepository;
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
    private UserRepository userRepository;

    @Before
    public void init() {

    }

    @Test
    @Transactional
    public void testAssertFirstImportCSVFile() throws IOException {
        User user = userRepository.findOneByLogin("mgoulene").get();
        // Import One Operation
        InputStream is = new ClassPathResource("./csv/op1.csv").getInputStream();
        operationCSVImporterService.importOperationCSVFile(user.getId(), is);
        List<Operation> operations = operationRepository.findAll();
        assertTrue(operations.size() == 1);
        
        Operation operation = operations.get(0);
        Long subCatId = operation.getSubCategory().getId();
        Long opId = operation.getId();
        // Update operation by modifying the subCategory
        is = new ClassPathResource("./csv/op2.csv").getInputStream();
        operationCSVImporterService.importOperationCSVFile(user.getId(), is);
        operations = operationRepository.findAll();
        assertTrue(operations.size() == 1);
        Operation newOperation = operations.get(0);
        assertTrue(opId == newOperation.getId());
        assertTrue(subCatId != newOperation.getSubCategory().getId());
        // Import with differetn date so delete the already imported & create new one
        is = new ClassPathResource("./csv/op3.csv").getInputStream();
        operationCSVImporterService.importOperationCSVFile(user.getId(), is);
        operations = operationRepository.findAll();
        assertTrue(operations.size() == 1);
        newOperation = operations.get(0);
        assertTrue(opId != newOperation.getId());
    }

}
