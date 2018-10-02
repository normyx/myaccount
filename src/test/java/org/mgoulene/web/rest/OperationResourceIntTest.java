package org.mgoulene.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mgoulene.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.persistence.EntityManager;

import com.github.stefanbirkner.fakesftpserver.rule.FakeSftpServerRule;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mgoulene.MyaccountApp;
import org.mgoulene.domain.BudgetItem;
import org.mgoulene.domain.BudgetItemPeriod;
import org.mgoulene.domain.Category;
import org.mgoulene.domain.Operation;
import org.mgoulene.domain.SubCategory;
import org.mgoulene.domain.User;
import org.mgoulene.domain.enumeration.CategoryType;
import org.mgoulene.repository.BudgetItemPeriodRepository;
import org.mgoulene.repository.CategoryRepository;
import org.mgoulene.repository.OperationRepository;
import org.mgoulene.repository.SubCategoryRepository;
import org.mgoulene.repository.UserRepository;
import org.mgoulene.service.BudgetItemPeriodService;
import org.mgoulene.service.BudgetItemQueryService;
import org.mgoulene.service.BudgetItemService;
import org.mgoulene.service.OperationCSVImporterService;
import org.mgoulene.service.OperationQueryService;
import org.mgoulene.service.OperationService;
import org.mgoulene.service.dto.BudgetItemDTO;
import org.mgoulene.service.dto.OperationDTO;
import org.mgoulene.service.mapper.BudgetItemMapper;
import org.mgoulene.service.mapper.OperationMapper;
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

/**
 * Test class for the OperationResource REST controller.
 *
 * @see OperationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyaccountApp.class)
public class OperationResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_CHECK_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_UP_TO_DATE = false;
    private static final Boolean UPDATED_IS_UP_TO_DATE = true;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private BudgetItemPeriodRepository budgetItemPeriodRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OperationMapper operationMapper;

    @Autowired
    private OperationService operationService;

    @Autowired
    private OperationQueryService operationQueryService;

    @Autowired
    private OperationCSVImporterService operationCSVImporterService;

    @Autowired
    private BudgetItemPeriodService budgetItemPeriodService;
    @Autowired
    private BudgetItemService budgetItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;
    @Autowired
    private BudgetItemMapper budgetItemMapper;

    @Autowired
    private BudgetItemQueryService budgetItemQueryService;

    @Rule
    public final FakeSftpServerRule sftpServer = new FakeSftpServerRule().addUser("user", "password").setPort(40015);

    private MockMvc restOperationMockMvc;

    private MockMvc restBudgetItemMockMvc;

    private Operation operation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperationResource operationResource = new OperationResource(operationService, operationQueryService,
                budgetItemPeriodService, budgetItemService, operationCSVImporterService);
        final BudgetItemResource budgetItemResource = new BudgetItemResource(budgetItemService, budgetItemQueryService);

        this.restOperationMockMvc = MockMvcBuilders.standaloneSetup(operationResource)
                .setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
                .setConversionService(createFormattingConversionService()).setMessageConverters(jacksonMessageConverter)
                .build();
        this.restBudgetItemMockMvc = MockMvcBuilders.standaloneSetup(budgetItemResource)
                .setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
                .setConversionService(createFormattingConversionService()).setMessageConverters(jacksonMessageConverter)
                .build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it, if
     * they test an entity which requires the current entity.
     */
    public static Operation createEntity(EntityManager em) {
        Operation operation = new Operation().label(DEFAULT_LABEL).date(DEFAULT_DATE).amount(DEFAULT_AMOUNT)
                .note(DEFAULT_NOTE).checkNumber(DEFAULT_CHECK_NUMBER).isUpToDate(DEFAULT_IS_UP_TO_DATE);
        return operation;
    }

    @Before
    public void initTest() {
        operation = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperation() throws Exception {
        int databaseSizeBeforeCreate = operationRepository.findAll().size();

        // Create the Operation
        OperationDTO operationDTO = operationMapper.toDto(operation);
        restOperationMockMvc.perform(post("/api/operations").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(operationDTO))).andExpect(status().isCreated());

        // Validate the Operation in the database
        List<Operation> operationList = operationRepository.findAll();
        assertThat(operationList).hasSize(databaseSizeBeforeCreate + 1);
        Operation testOperation = operationList.get(operationList.size() - 1);
        assertThat(testOperation.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testOperation.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testOperation.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testOperation.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testOperation.getCheckNumber()).isEqualTo(DEFAULT_CHECK_NUMBER);
        assertThat(testOperation.isIsUpToDate()).isEqualTo(DEFAULT_IS_UP_TO_DATE);
    }

    @Test
    @Transactional
    public void createOperationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operationRepository.findAll().size();

        // Create the Operation with an existing ID
        operation.setId(1L);
        OperationDTO operationDTO = operationMapper.toDto(operation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationMockMvc.perform(post("/api/operations").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(operationDTO))).andExpect(status().isBadRequest());

        // Validate the Operation in the database
        List<Operation> operationList = operationRepository.findAll();
        assertThat(operationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationRepository.findAll().size();
        // set the field null
        operation.setLabel(null);

        // Create the Operation, which fails.
        OperationDTO operationDTO = operationMapper.toDto(operation);

        restOperationMockMvc.perform(post("/api/operations").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(operationDTO))).andExpect(status().isBadRequest());

        List<Operation> operationList = operationRepository.findAll();
        assertThat(operationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationRepository.findAll().size();
        // set the field null
        operation.setDate(null);

        // Create the Operation, which fails.
        OperationDTO operationDTO = operationMapper.toDto(operation);

        restOperationMockMvc.perform(post("/api/operations").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(operationDTO))).andExpect(status().isBadRequest());

        List<Operation> operationList = operationRepository.findAll();
        assertThat(operationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationRepository.findAll().size();
        // set the field null
        operation.setAmount(null);

        // Create the Operation, which fails.
        OperationDTO operationDTO = operationMapper.toDto(operation);

        restOperationMockMvc.perform(post("/api/operations").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(operationDTO))).andExpect(status().isBadRequest());

        List<Operation> operationList = operationRepository.findAll();
        assertThat(operationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsUpToDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationRepository.findAll().size();
        // set the field null
        operation.setIsUpToDate(null);

        // Create the Operation, which fails.
        OperationDTO operationDTO = operationMapper.toDto(operation);

        restOperationMockMvc.perform(post("/api/operations").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(operationDTO))).andExpect(status().isBadRequest());

        List<Operation> operationList = operationRepository.findAll();
        assertThat(operationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOperations() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList
        restOperationMockMvc.perform(get("/api/operations?sort=id,desc")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(operation.getId().intValue())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
                .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
                .andExpect(jsonPath("$.[*].checkNumber").value(hasItem(DEFAULT_CHECK_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].isUpToDate").value(hasItem(DEFAULT_IS_UP_TO_DATE.booleanValue())));
    }

    @Test
    @Transactional
    public void getOperation() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get the operation
        restOperationMockMvc.perform(get("/api/operations/{id}", operation.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(operation.getId().intValue()))
                .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
                .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
                .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
                .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
                .andExpect(jsonPath("$.checkNumber").value(DEFAULT_CHECK_NUMBER.toString()))
                .andExpect(jsonPath("$.isUpToDate").value(DEFAULT_IS_UP_TO_DATE.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllOperationsByLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where label equals to DEFAULT_LABEL
        defaultOperationShouldBeFound("label.equals=" + DEFAULT_LABEL);

        // Get all the operationList where label equals to UPDATED_LABEL
        defaultOperationShouldNotBeFound("label.equals=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void getAllOperationsByLabelIsInShouldWork() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where label in DEFAULT_LABEL or UPDATED_LABEL
        defaultOperationShouldBeFound("label.in=" + DEFAULT_LABEL + "," + UPDATED_LABEL);

        // Get all the operationList where label equals to UPDATED_LABEL
        defaultOperationShouldNotBeFound("label.in=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void getAllOperationsByLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where label is not null
        defaultOperationShouldBeFound("label.specified=true");

        // Get all the operationList where label is null
        defaultOperationShouldNotBeFound("label.specified=false");
    }

    @Test
    @Transactional
    public void getAllOperationsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where date equals to DEFAULT_DATE
        defaultOperationShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the operationList where date equals to UPDATED_DATE
        defaultOperationShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllOperationsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where date in DEFAULT_DATE or UPDATED_DATE
        defaultOperationShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the operationList where date equals to UPDATED_DATE
        defaultOperationShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllOperationsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where date is not null
        defaultOperationShouldBeFound("date.specified=true");

        // Get all the operationList where date is null
        defaultOperationShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllOperationsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where date greater than or equals to DEFAULT_DATE
        defaultOperationShouldBeFound("date.greaterOrEqualThan=" + DEFAULT_DATE);

        // Get all the operationList where date greater than or equals to UPDATED_DATE
        defaultOperationShouldNotBeFound("date.greaterOrEqualThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllOperationsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where date less than or equals to DEFAULT_DATE
        defaultOperationShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the operationList where date less than or equals to UPDATED_DATE
        defaultOperationShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllOperationsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where amount equals to DEFAULT_AMOUNT
        defaultOperationShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the operationList where amount equals to UPDATED_AMOUNT
        defaultOperationShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOperationsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultOperationShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the operationList where amount equals to UPDATED_AMOUNT
        defaultOperationShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOperationsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where amount is not null
        defaultOperationShouldBeFound("amount.specified=true");

        // Get all the operationList where amount is null
        defaultOperationShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllOperationsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where note equals to DEFAULT_NOTE
        defaultOperationShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the operationList where note equals to UPDATED_NOTE
        defaultOperationShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllOperationsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultOperationShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the operationList where note equals to UPDATED_NOTE
        defaultOperationShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllOperationsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where note is not null
        defaultOperationShouldBeFound("note.specified=true");

        // Get all the operationList where note is null
        defaultOperationShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    public void getAllOperationsByCheckNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where checkNumber equals to DEFAULT_CHECK_NUMBER
        defaultOperationShouldBeFound("checkNumber.equals=" + DEFAULT_CHECK_NUMBER);

        // Get all the operationList where checkNumber equals to UPDATED_CHECK_NUMBER
        defaultOperationShouldNotBeFound("checkNumber.equals=" + UPDATED_CHECK_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOperationsByCheckNumberIsInShouldWork() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where checkNumber in DEFAULT_CHECK_NUMBER or
        // UPDATED_CHECK_NUMBER
        defaultOperationShouldBeFound("checkNumber.in=" + DEFAULT_CHECK_NUMBER + "," + UPDATED_CHECK_NUMBER);

        // Get all the operationList where checkNumber equals to UPDATED_CHECK_NUMBER
        defaultOperationShouldNotBeFound("checkNumber.in=" + UPDATED_CHECK_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOperationsByCheckNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where checkNumber is not null
        defaultOperationShouldBeFound("checkNumber.specified=true");

        // Get all the operationList where checkNumber is null
        defaultOperationShouldNotBeFound("checkNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllOperationsByIsUpToDateIsEqualToSomething() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where isUpToDate equals to DEFAULT_IS_UP_TO_DATE
        defaultOperationShouldBeFound("isUpToDate.equals=" + DEFAULT_IS_UP_TO_DATE);

        // Get all the operationList where isUpToDate equals to UPDATED_IS_UP_TO_DATE
        defaultOperationShouldNotBeFound("isUpToDate.equals=" + UPDATED_IS_UP_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllOperationsByIsUpToDateIsInShouldWork() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where isUpToDate in DEFAULT_IS_UP_TO_DATE or
        // UPDATED_IS_UP_TO_DATE
        defaultOperationShouldBeFound("isUpToDate.in=" + DEFAULT_IS_UP_TO_DATE + "," + UPDATED_IS_UP_TO_DATE);

        // Get all the operationList where isUpToDate equals to UPDATED_IS_UP_TO_DATE
        defaultOperationShouldNotBeFound("isUpToDate.in=" + UPDATED_IS_UP_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllOperationsByIsUpToDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        // Get all the operationList where isUpToDate is not null
        defaultOperationShouldBeFound("isUpToDate.specified=true");

        // Get all the operationList where isUpToDate is null
        defaultOperationShouldNotBeFound("isUpToDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOperationsBySubCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        SubCategory subCategory = SubCategoryResourceIntTest.createEntity(em);
        em.persist(subCategory);
        em.flush();
        operation.setSubCategory(subCategory);
        operationRepository.saveAndFlush(operation);
        Long subCategoryId = subCategory.getId();

        // Get all the operationList where subCategory equals to subCategoryId
        defaultOperationShouldBeFound("subCategoryId.equals=" + subCategoryId);

        // Get all the operationList where subCategory equals to subCategoryId + 1
        defaultOperationShouldNotBeFound("subCategoryId.equals=" + (subCategoryId + 1));
    }

    @Test
    @Transactional
    public void getAllOperationsByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        User account = UserResourceIntTest.createEntity(em);
        em.persist(account);
        em.flush();
        operation.setAccount(account);
        operationRepository.saveAndFlush(operation);
        Long accountId = account.getId();

        // Get all the operationList where account equals to accountId
        defaultOperationShouldBeFound("accountId.equals=" + accountId);

        // Get all the operationList where account equals to accountId + 1
        defaultOperationShouldNotBeFound("accountId.equals=" + (accountId + 1));
    }

    @Test
    @Transactional
    public void getAllOperationsByBudgetItemIsEqualToSomething() throws Exception {
        // Initialize the database
        BudgetItemPeriod budgetItem = BudgetItemPeriodResourceIntTest.createEntity(em);
        em.persist(budgetItem);
        em.flush();
        operation.setBudgetItem(budgetItem);
        budgetItem.setOperation(operation);
        operationRepository.saveAndFlush(operation);
        Long budgetItemId = budgetItem.getId();

        // Get all the operationList where budgetItem equals to budgetItemId
        defaultOperationShouldBeFound("budgetItemId.equals=" + budgetItemId);

        // Get all the operationList where budgetItem equals to budgetItemId + 1
        defaultOperationShouldNotBeFound("budgetItemId.equals=" + (budgetItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultOperationShouldBeFound(String filter) throws Exception {
        restOperationMockMvc.perform(get("/api/operations?sort=id,desc&" + filter)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(operation.getId().intValue())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
                .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
                .andExpect(jsonPath("$.[*].checkNumber").value(hasItem(DEFAULT_CHECK_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].isUpToDate").value(hasItem(DEFAULT_IS_UP_TO_DATE.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultOperationShouldNotBeFound(String filter) throws Exception {
        restOperationMockMvc.perform(get("/api/operations?sort=id,desc&" + filter)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingOperation() throws Exception {
        // Get the operation
        restOperationMockMvc.perform(get("/api/operations/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperation() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        int databaseSizeBeforeUpdate = operationRepository.findAll().size();

        // Update the operation
        Operation updatedOperation = operationRepository.findById(operation.getId()).get();
        // Disconnect from session so that the updates on updatedOperation are not
        // directly saved in db
        em.detach(updatedOperation);
        updatedOperation.label(UPDATED_LABEL).date(UPDATED_DATE).amount(UPDATED_AMOUNT).note(UPDATED_NOTE)
                .checkNumber(UPDATED_CHECK_NUMBER).isUpToDate(UPDATED_IS_UP_TO_DATE);
        OperationDTO operationDTO = operationMapper.toDto(updatedOperation);

        restOperationMockMvc.perform(put("/api/operations").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(operationDTO))).andExpect(status().isOk());

        // Validate the Operation in the database
        List<Operation> operationList = operationRepository.findAll();
        assertThat(operationList).hasSize(databaseSizeBeforeUpdate);
        Operation testOperation = operationList.get(operationList.size() - 1);
        assertThat(testOperation.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testOperation.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testOperation.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testOperation.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testOperation.getCheckNumber()).isEqualTo(UPDATED_CHECK_NUMBER);
        assertThat(testOperation.isIsUpToDate()).isEqualTo(UPDATED_IS_UP_TO_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOperation() throws Exception {
        int databaseSizeBeforeUpdate = operationRepository.findAll().size();

        // Create the Operation
        OperationDTO operationDTO = operationMapper.toDto(operation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationMockMvc.perform(put("/api/operations").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(operationDTO))).andExpect(status().isBadRequest());

        // Validate the Operation in the database
        List<Operation> operationList = operationRepository.findAll();
        assertThat(operationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOperation() throws Exception {
        // Initialize the database
        operationRepository.saveAndFlush(operation);

        int databaseSizeBeforeDelete = operationRepository.findAll().size();

        // Get the operation
        restOperationMockMvc
                .perform(delete("/api/operations/{id}", operation.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Operation> operationList = operationRepository.findAll();
        assertThat(operationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operation.class);
        Operation operation1 = new Operation();
        operation1.setId(1L);
        Operation operation2 = new Operation();
        operation2.setId(operation1.getId());
        assertThat(operation1).isEqualTo(operation2);
        operation2.setId(2L);
        assertThat(operation1).isNotEqualTo(operation2);
        operation1.setId(null);
        assertThat(operation1).isNotEqualTo(operation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationDTO.class);
        OperationDTO operationDTO1 = new OperationDTO();
        operationDTO1.setId(1L);
        OperationDTO operationDTO2 = new OperationDTO();
        assertThat(operationDTO1).isNotEqualTo(operationDTO2);
        operationDTO2.setId(operationDTO1.getId());
        assertThat(operationDTO1).isEqualTo(operationDTO2);
        operationDTO2.setId(2L);
        assertThat(operationDTO1).isNotEqualTo(operationDTO2);
        operationDTO1.setId(null);
        assertThat(operationDTO1).isNotEqualTo(operationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(operationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(operationMapper.fromId(null)).isNull();
    }

    @Test
    @Transactional
    public void testAssertSFTPServer() throws Exception {
        try {
            Category cat1;
            SubCategory subCat1;
            cat1 = new Category();
            cat1.setCategoryName("Cat1");
            cat1.setCategoryType(CategoryType.SPENDING);
            subCat1 = new SubCategory();
            subCat1.setSubCategoryName("sc1");

            System.out.println("Server Port : " + sftpServer.getPort());
            // create Category and SubCategories
            cat1 = categoryRepository.saveAndFlush(cat1);
            subCat1.setCategory(cat1);
            subCategoryRepository.saveAndFlush(subCat1);

            User user = userRepository.findOneByLogin("mgoulene").get();
            // Import One Operation
            InputStream is = new ClassPathResource("./csv/op1.csv").getInputStream();

            String operationString = IOUtils.toString(is, StandardCharsets.UTF_16);

            sftpServer.putFile("/home/in/mgoulene/operation.csv", operationString, StandardCharsets.UTF_16);

            restOperationMockMvc.perform(put("/api/import-operations-file")).andExpect(status().isOk());

            BudgetItem budgetItem = new BudgetItem().name("aaaaaaaa").category(cat1).order(1);

            BudgetItemDTO budgetItemDTO = budgetItemMapper.toDto(budgetItem);

            restBudgetItemMockMvc.perform(post("/api/budget-items-with-periods/false/2018-01-01/-10/5")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(budgetItemDTO))).andExpect(status().isCreated());

            List<BudgetItemPeriod> bips = budgetItemPeriodRepository.findAll();

            assertThat(bips).hasSize(12);
            restOperationMockMvc.perform(get("/api/operations-close-to-budget/" + bips.get(0).getId()))
                    .andExpect(status().isOk()).andExpect(jsonPath("$[0].amount").value(-10));

        } catch (IOException e) {

            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
