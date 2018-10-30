package org.mgoulene.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mgoulene.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mgoulene.MyaccountApp;
import org.mgoulene.domain.BudgetItem;
import org.mgoulene.domain.Category;
import org.mgoulene.domain.Operation;
import org.mgoulene.domain.SubCategory;
import org.mgoulene.domain.User;
import org.mgoulene.domain.enumeration.CategoryType;
import org.mgoulene.repository.CategoryRepository;
import org.mgoulene.repository.OperationRepository;
import org.mgoulene.repository.SubCategoryRepository;
import org.mgoulene.repository.UserRepository;
import org.mgoulene.service.BudgetItemPeriodService;
import org.mgoulene.service.BudgetItemQueryService;
import org.mgoulene.service.BudgetItemService;
import org.mgoulene.service.OperationCSVImporterService;
import org.mgoulene.service.ReportDataService;
import org.mgoulene.service.UserService;
import org.mgoulene.service.dto.BudgetItemDTO;
import org.mgoulene.service.mapper.BudgetItemMapper;
import org.mgoulene.web.rest.errors.ExceptionTranslator;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * Test class for the OperationResource REST controller.
 *
 * @see OperationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyaccountApp.class)
public class ReportDataResourceIntTest {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private OperationCSVImporterService operationCSVImporterService;

    @Autowired
    private BudgetItemService budgetItemService;

    @Autowired
    private BudgetItemQueryService budgetItemQueryService;

    @Autowired
    private ReportDataService reportDataService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private BudgetItemMapper budgetItemMapper;

    @Autowired
    private BudgetItemPeriodService budgetItemPeriodService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private WebApplicationContext context;

    private MockMvc restReportDataMockMvc;
    private MockMvc restBudgetItemMockMvc;

    private Category cat1;

    private SubCategory subCat1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReportDataResource reportDataResource = new ReportDataResource(reportDataService, userService);
        final BudgetItemResource budgetItemResource = new BudgetItemResource(budgetItemService, budgetItemQueryService, userService, budgetItemPeriodService);

        /*
         * this.restReportDataMockMvc =
         * MockMvcBuilders.standaloneSetup(reportDataResource)
         * .setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(
         * exceptionTranslator)
         * .setConversionService(createFormattingConversionService()).
         * setMessageConverters(jacksonMessageConverter).apply(springSecurity()).
         * webAppContextSetup(context) .build();
         */
        this.restReportDataMockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        this.restBudgetItemMockMvc = MockMvcBuilders.standaloneSetup(budgetItemResource)
                .setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
                .setConversionService(createFormattingConversionService()).setMessageConverters(jacksonMessageConverter)
                .build();
    }

    @Before
    public void init() {
        cat1 = new Category();
        cat1.setCategoryName("Cat1");
        cat1.setCategoryType(CategoryType.SPENDING);
        subCat1 = new SubCategory();
        subCat1.setSubCategoryName("sc1");

    }

    @Test
    @Transactional
    public void testfindAllFromCategory() throws Exception {
        // create Category and SubCategories
        cat1 = categoryRepository.saveAndFlush(cat1);
        subCat1.setCategory(cat1);
        subCategoryRepository.saveAndFlush(subCat1);

        User user = userRepository.findOneByLogin("mgoulene").get();
        // Import One Operation
        InputStream is = new ClassPathResource("./csv/opFromReportData.tsv").getInputStream();
        operationCSVImporterService.importOperationCSVFile(user.getId(), is);
        List<Operation> operations = operationRepository.findAll();
        assertThat(operations).hasSize(12);

        // Create the BudgetItem
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.account(user).category(cat1).name("budgetitem").order(1);

        BudgetItemDTO budgetItemDTO = budgetItemMapper.toDto(budgetItem);

        restBudgetItemMockMvc.perform(post("/api/budget-items-with-periods/false/2018-01-01/-10/5")
                .contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(budgetItemDTO)))
                .andExpect(status().isCreated());

        restReportDataMockMvc
                .perform(get("/api/report-amount-global-per-day-in-month/2018-01-01").with(user("mgoulene")))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.dates", hasSize(31))).andExpect(jsonPath("$.operationAmounts", hasSize(31)))
                .andExpect(jsonPath("$.budgetAmounts", hasSize(31)))
                .andExpect(jsonPath("$.operationAmounts[3]").value(0))
                .andExpect(jsonPath("$.operationAmounts[10]").value(-10))
                .andExpect(jsonPath("$.budgetAmounts[3]").value(0))
                .andExpect(jsonPath("$.budgetAmounts[10]").value(-10));
        restReportDataMockMvc
                .perform(get("/api/report-amount-category-per-month/" + cat1.getId() + "/2018-01-01/2018-12-01")
                        .with(user("mgoulene")))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.months", hasSize(12))).andExpect(jsonPath("$.amounts[6]").value(-10))
                .andExpect(jsonPath("$.amountsAvg3[6]").value(-10)).andExpect(jsonPath("$.amountsAvg12[6]").value(-10))
                .andExpect(jsonPath("$.budgetAmounts[6]").value(-10));
    }

    
}