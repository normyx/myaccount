package org.mgoulene.service;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mgoulene.MyaccountApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyaccountApp.class)
@Transactional
public class OperationCSVImporterServiceIntTest {

    @Autowired
    private OperationCSVImporterService operationCSVImporterService;

    @Before
    public void init() {
    }

    @Test
    @Transactional
    public void testImport() throws IOException {
        InputStream is = new ClassPathResource("./csv/opérations.csv").getInputStream();
        
        operationCSVImporterService.importOperationCSVFile(new Long(5), is);
        System.out.println("foo");
        assertTrue(true);
    }
}

