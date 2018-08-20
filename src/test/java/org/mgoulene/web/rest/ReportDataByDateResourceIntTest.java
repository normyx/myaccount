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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mgoulene.MyaccountApp;
import org.mgoulene.domain.Category;
import org.mgoulene.domain.ReportDataByDate;
import org.mgoulene.domain.User;
import org.mgoulene.repository.ReportDataByDateRepository;
import org.mgoulene.service.ReportDataByDateQueryService;
import org.mgoulene.service.ReportDataByDateService;
import org.mgoulene.service.dto.ReportDataByDateDTO;
import org.mgoulene.service.mapper.ReportDataByDateMapper;
import org.mgoulene.web.rest.errors.ExceptionTranslator;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class for the ReportDataByDateResource REST controller.
 *
 * @see ReportDataByDateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyaccountApp.class)
public class ReportDataByDateResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MONTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MONTH = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_HAS_OPERATION = false;
    private static final Boolean UPDATED_HAS_OPERATION = true;

    private static final Float DEFAULT_OPERATION_AMOUNT = 1F;
    private static final Float UPDATED_OPERATION_AMOUNT = 2F;

    private static final Float DEFAULT_BUDGET_SMOOTHED_AMOUNT = 1F;
    private static final Float UPDATED_BUDGET_SMOOTHED_AMOUNT = 2F;

    private static final Float DEFAULT_BUDGET_UNSMOOTHED_MARKED_AMOUNT = 1F;
    private static final Float UPDATED_BUDGET_UNSMOOTHED_MARKED_AMOUNT = 2F;

    private static final Float DEFAULT_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT = 1F;
    private static final Float UPDATED_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT = 2F;

    @Autowired
    private ReportDataByDateRepository reportDataByDateRepository;


    @Autowired
    private ReportDataByDateMapper reportDataByDateMapper;
    

    @Autowired
    private ReportDataByDateService reportDataByDateService;

    @Autowired
    private ReportDataByDateQueryService reportDataByDateQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReportDataByDateMockMvc;

    private ReportDataByDate reportDataByDate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReportDataByDateResource reportDataByDateResource = new ReportDataByDateResource(reportDataByDateService, reportDataByDateQueryService);
        this.restReportDataByDateMockMvc = MockMvcBuilders.standaloneSetup(reportDataByDateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportDataByDate createEntity(EntityManager em) {
        ReportDataByDate reportDataByDate = new ReportDataByDate()
            .date(DEFAULT_DATE)
            .month(DEFAULT_MONTH)
            .hasOperation(DEFAULT_HAS_OPERATION)
            .operationAmount(DEFAULT_OPERATION_AMOUNT)
            .budgetSmoothedAmount(DEFAULT_BUDGET_SMOOTHED_AMOUNT)
            .budgetUnsmoothedMarkedAmount(DEFAULT_BUDGET_UNSMOOTHED_MARKED_AMOUNT)
            .budgetUnsmoothedUnmarkedAmount(DEFAULT_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT);
        // Add required entity
        Category category = CategoryResourceIntTest.createEntity(em);
        em.persist(category);
        em.flush();
        reportDataByDate.setCategory(category);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        reportDataByDate.setAccount(user);
        return reportDataByDate;
    }

    @Before
    public void initTest() {
        reportDataByDate = createEntity(em);
    }

    @Test
    @Transactional
    public void createReportDataByDate() throws Exception {
        int databaseSizeBeforeCreate = reportDataByDateRepository.findAll().size();

        // Create the ReportDataByDate
        ReportDataByDateDTO reportDataByDateDTO = reportDataByDateMapper.toDto(reportDataByDate);
        restReportDataByDateMockMvc.perform(post("/api/report-data-by-dates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDataByDateDTO)))
            .andExpect(status().isCreated());

        // Validate the ReportDataByDate in the database
        List<ReportDataByDate> reportDataByDateList = reportDataByDateRepository.findAll();
        assertThat(reportDataByDateList).hasSize(databaseSizeBeforeCreate + 1);
        ReportDataByDate testReportDataByDate = reportDataByDateList.get(reportDataByDateList.size() - 1);
        assertThat(testReportDataByDate.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testReportDataByDate.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testReportDataByDate.isHasOperation()).isEqualTo(DEFAULT_HAS_OPERATION);
        assertThat(testReportDataByDate.getOperationAmount()).isEqualTo(DEFAULT_OPERATION_AMOUNT);
        assertThat(testReportDataByDate.getBudgetSmoothedAmount()).isEqualTo(DEFAULT_BUDGET_SMOOTHED_AMOUNT);
        assertThat(testReportDataByDate.getBudgetUnsmoothedMarkedAmount()).isEqualTo(DEFAULT_BUDGET_UNSMOOTHED_MARKED_AMOUNT);
        assertThat(testReportDataByDate.getBudgetUnsmoothedUnmarkedAmount()).isEqualTo(DEFAULT_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT);
    }

    @Test
    @Transactional
    public void createReportDataByDateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportDataByDateRepository.findAll().size();

        // Create the ReportDataByDate with an existing ID
        reportDataByDate.setId(1L);
        ReportDataByDateDTO reportDataByDateDTO = reportDataByDateMapper.toDto(reportDataByDate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportDataByDateMockMvc.perform(post("/api/report-data-by-dates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDataByDateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportDataByDate in the database
        List<ReportDataByDate> reportDataByDateList = reportDataByDateRepository.findAll();
        assertThat(reportDataByDateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportDataByDateRepository.findAll().size();
        // set the field null
        reportDataByDate.setDate(null);

        // Create the ReportDataByDate, which fails.
        ReportDataByDateDTO reportDataByDateDTO = reportDataByDateMapper.toDto(reportDataByDate);

        restReportDataByDateMockMvc.perform(post("/api/report-data-by-dates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDataByDateDTO)))
            .andExpect(status().isBadRequest());

        List<ReportDataByDate> reportDataByDateList = reportDataByDateRepository.findAll();
        assertThat(reportDataByDateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportDataByDateRepository.findAll().size();
        // set the field null
        reportDataByDate.setMonth(null);

        // Create the ReportDataByDate, which fails.
        ReportDataByDateDTO reportDataByDateDTO = reportDataByDateMapper.toDto(reportDataByDate);

        restReportDataByDateMockMvc.perform(post("/api/report-data-by-dates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDataByDateDTO)))
            .andExpect(status().isBadRequest());

        List<ReportDataByDate> reportDataByDateList = reportDataByDateRepository.findAll();
        assertThat(reportDataByDateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHasOperationIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportDataByDateRepository.findAll().size();
        // set the field null
        reportDataByDate.setHasOperation(null);

        // Create the ReportDataByDate, which fails.
        ReportDataByDateDTO reportDataByDateDTO = reportDataByDateMapper.toDto(reportDataByDate);

        restReportDataByDateMockMvc.perform(post("/api/report-data-by-dates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDataByDateDTO)))
            .andExpect(status().isBadRequest());

        List<ReportDataByDate> reportDataByDateList = reportDataByDateRepository.findAll();
        assertThat(reportDataByDateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReportDataByDates() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList
        restReportDataByDateMockMvc.perform(get("/api/report-data-by-dates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportDataByDate.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].hasOperation").value(hasItem(DEFAULT_HAS_OPERATION.booleanValue())))
            .andExpect(jsonPath("$.[*].operationAmount").value(hasItem(DEFAULT_OPERATION_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].budgetSmoothedAmount").value(hasItem(DEFAULT_BUDGET_SMOOTHED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].budgetUnsmoothedMarkedAmount").value(hasItem(DEFAULT_BUDGET_UNSMOOTHED_MARKED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].budgetUnsmoothedUnmarkedAmount").value(hasItem(DEFAULT_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getReportDataByDate() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get the reportDataByDate
        restReportDataByDateMockMvc.perform(get("/api/report-data-by-dates/{id}", reportDataByDate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reportDataByDate.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.hasOperation").value(DEFAULT_HAS_OPERATION.booleanValue()))
            .andExpect(jsonPath("$.operationAmount").value(DEFAULT_OPERATION_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.budgetSmoothedAmount").value(DEFAULT_BUDGET_SMOOTHED_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.budgetUnsmoothedMarkedAmount").value(DEFAULT_BUDGET_UNSMOOTHED_MARKED_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.budgetUnsmoothedUnmarkedAmount").value(DEFAULT_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void refreshReportDataByDate() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get the reportDataByDate
        restReportDataByDateMockMvc.perform(get("/api/refresh-report-data/{accountId}", reportDataByDate.getAccount().getId()))
            .andExpect(status().isOk());
    }


    @Test
    @Transactional
    public void getAllReportDataByDatesByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where date equals to DEFAULT_DATE
        defaultReportDataByDateShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the reportDataByDateList where date equals to UPDATED_DATE
        defaultReportDataByDateShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByDateIsInShouldWork() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where date in DEFAULT_DATE or UPDATED_DATE
        defaultReportDataByDateShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the reportDataByDateList where date equals to UPDATED_DATE
        defaultReportDataByDateShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where date is not null
        defaultReportDataByDateShouldBeFound("date.specified=true");

        // Get all the reportDataByDateList where date is null
        defaultReportDataByDateShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where date greater than or equals to DEFAULT_DATE
        defaultReportDataByDateShouldBeFound("date.greaterOrEqualThan=" + DEFAULT_DATE);

        // Get all the reportDataByDateList where date greater than or equals to UPDATED_DATE
        defaultReportDataByDateShouldNotBeFound("date.greaterOrEqualThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where date less than or equals to DEFAULT_DATE
        defaultReportDataByDateShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the reportDataByDateList where date less than or equals to UPDATED_DATE
        defaultReportDataByDateShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllReportDataByDatesByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where month equals to DEFAULT_MONTH
        defaultReportDataByDateShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the reportDataByDateList where month equals to UPDATED_MONTH
        defaultReportDataByDateShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultReportDataByDateShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the reportDataByDateList where month equals to UPDATED_MONTH
        defaultReportDataByDateShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where month is not null
        defaultReportDataByDateShouldBeFound("month.specified=true");

        // Get all the reportDataByDateList where month is null
        defaultReportDataByDateShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByMonthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where month greater than or equals to DEFAULT_MONTH
        defaultReportDataByDateShouldBeFound("month.greaterOrEqualThan=" + DEFAULT_MONTH);

        // Get all the reportDataByDateList where month greater than or equals to UPDATED_MONTH
        defaultReportDataByDateShouldNotBeFound("month.greaterOrEqualThan=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByMonthIsLessThanSomething() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where month less than or equals to DEFAULT_MONTH
        defaultReportDataByDateShouldNotBeFound("month.lessThan=" + DEFAULT_MONTH);

        // Get all the reportDataByDateList where month less than or equals to UPDATED_MONTH
        defaultReportDataByDateShouldBeFound("month.lessThan=" + UPDATED_MONTH);
    }


    @Test
    @Transactional
    public void getAllReportDataByDatesByHasOperationIsEqualToSomething() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where hasOperation equals to DEFAULT_HAS_OPERATION
        defaultReportDataByDateShouldBeFound("hasOperation.equals=" + DEFAULT_HAS_OPERATION);

        // Get all the reportDataByDateList where hasOperation equals to UPDATED_HAS_OPERATION
        defaultReportDataByDateShouldNotBeFound("hasOperation.equals=" + UPDATED_HAS_OPERATION);
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByHasOperationIsInShouldWork() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where hasOperation in DEFAULT_HAS_OPERATION or UPDATED_HAS_OPERATION
        defaultReportDataByDateShouldBeFound("hasOperation.in=" + DEFAULT_HAS_OPERATION + "," + UPDATED_HAS_OPERATION);

        // Get all the reportDataByDateList where hasOperation equals to UPDATED_HAS_OPERATION
        defaultReportDataByDateShouldNotBeFound("hasOperation.in=" + UPDATED_HAS_OPERATION);
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByHasOperationIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where hasOperation is not null
        defaultReportDataByDateShouldBeFound("hasOperation.specified=true");

        // Get all the reportDataByDateList where hasOperation is null
        defaultReportDataByDateShouldNotBeFound("hasOperation.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByOperationAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where operationAmount equals to DEFAULT_OPERATION_AMOUNT
        defaultReportDataByDateShouldBeFound("operationAmount.equals=" + DEFAULT_OPERATION_AMOUNT);

        // Get all the reportDataByDateList where operationAmount equals to UPDATED_OPERATION_AMOUNT
        defaultReportDataByDateShouldNotBeFound("operationAmount.equals=" + UPDATED_OPERATION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByOperationAmountIsInShouldWork() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where operationAmount in DEFAULT_OPERATION_AMOUNT or UPDATED_OPERATION_AMOUNT
        defaultReportDataByDateShouldBeFound("operationAmount.in=" + DEFAULT_OPERATION_AMOUNT + "," + UPDATED_OPERATION_AMOUNT);

        // Get all the reportDataByDateList where operationAmount equals to UPDATED_OPERATION_AMOUNT
        defaultReportDataByDateShouldNotBeFound("operationAmount.in=" + UPDATED_OPERATION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByOperationAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where operationAmount is not null
        defaultReportDataByDateShouldBeFound("operationAmount.specified=true");

        // Get all the reportDataByDateList where operationAmount is null
        defaultReportDataByDateShouldNotBeFound("operationAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByBudgetSmoothedAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where budgetSmoothedAmount equals to DEFAULT_BUDGET_SMOOTHED_AMOUNT
        defaultReportDataByDateShouldBeFound("budgetSmoothedAmount.equals=" + DEFAULT_BUDGET_SMOOTHED_AMOUNT);

        // Get all the reportDataByDateList where budgetSmoothedAmount equals to UPDATED_BUDGET_SMOOTHED_AMOUNT
        defaultReportDataByDateShouldNotBeFound("budgetSmoothedAmount.equals=" + UPDATED_BUDGET_SMOOTHED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByBudgetSmoothedAmountIsInShouldWork() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where budgetSmoothedAmount in DEFAULT_BUDGET_SMOOTHED_AMOUNT or UPDATED_BUDGET_SMOOTHED_AMOUNT
        defaultReportDataByDateShouldBeFound("budgetSmoothedAmount.in=" + DEFAULT_BUDGET_SMOOTHED_AMOUNT + "," + UPDATED_BUDGET_SMOOTHED_AMOUNT);

        // Get all the reportDataByDateList where budgetSmoothedAmount equals to UPDATED_BUDGET_SMOOTHED_AMOUNT
        defaultReportDataByDateShouldNotBeFound("budgetSmoothedAmount.in=" + UPDATED_BUDGET_SMOOTHED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByBudgetSmoothedAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where budgetSmoothedAmount is not null
        defaultReportDataByDateShouldBeFound("budgetSmoothedAmount.specified=true");

        // Get all the reportDataByDateList where budgetSmoothedAmount is null
        defaultReportDataByDateShouldNotBeFound("budgetSmoothedAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByBudgetUnsmoothedMarkedAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where budgetUnsmoothedMarkedAmount equals to DEFAULT_BUDGET_UNSMOOTHED_MARKED_AMOUNT
        defaultReportDataByDateShouldBeFound("budgetUnsmoothedMarkedAmount.equals=" + DEFAULT_BUDGET_UNSMOOTHED_MARKED_AMOUNT);

        // Get all the reportDataByDateList where budgetUnsmoothedMarkedAmount equals to UPDATED_BUDGET_UNSMOOTHED_MARKED_AMOUNT
        defaultReportDataByDateShouldNotBeFound("budgetUnsmoothedMarkedAmount.equals=" + UPDATED_BUDGET_UNSMOOTHED_MARKED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByBudgetUnsmoothedMarkedAmountIsInShouldWork() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where budgetUnsmoothedMarkedAmount in DEFAULT_BUDGET_UNSMOOTHED_MARKED_AMOUNT or UPDATED_BUDGET_UNSMOOTHED_MARKED_AMOUNT
        defaultReportDataByDateShouldBeFound("budgetUnsmoothedMarkedAmount.in=" + DEFAULT_BUDGET_UNSMOOTHED_MARKED_AMOUNT + "," + UPDATED_BUDGET_UNSMOOTHED_MARKED_AMOUNT);

        // Get all the reportDataByDateList where budgetUnsmoothedMarkedAmount equals to UPDATED_BUDGET_UNSMOOTHED_MARKED_AMOUNT
        defaultReportDataByDateShouldNotBeFound("budgetUnsmoothedMarkedAmount.in=" + UPDATED_BUDGET_UNSMOOTHED_MARKED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByBudgetUnsmoothedMarkedAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where budgetUnsmoothedMarkedAmount is not null
        defaultReportDataByDateShouldBeFound("budgetUnsmoothedMarkedAmount.specified=true");

        // Get all the reportDataByDateList where budgetUnsmoothedMarkedAmount is null
        defaultReportDataByDateShouldNotBeFound("budgetUnsmoothedMarkedAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByBudgetUnsmoothedUnmarkedAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where budgetUnsmoothedUnmarkedAmount equals to DEFAULT_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT
        defaultReportDataByDateShouldBeFound("budgetUnsmoothedUnmarkedAmount.equals=" + DEFAULT_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT);

        // Get all the reportDataByDateList where budgetUnsmoothedUnmarkedAmount equals to UPDATED_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT
        defaultReportDataByDateShouldNotBeFound("budgetUnsmoothedUnmarkedAmount.equals=" + UPDATED_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByBudgetUnsmoothedUnmarkedAmountIsInShouldWork() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where budgetUnsmoothedUnmarkedAmount in DEFAULT_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT or UPDATED_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT
        defaultReportDataByDateShouldBeFound("budgetUnsmoothedUnmarkedAmount.in=" + DEFAULT_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT + "," + UPDATED_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT);

        // Get all the reportDataByDateList where budgetUnsmoothedUnmarkedAmount equals to UPDATED_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT
        defaultReportDataByDateShouldNotBeFound("budgetUnsmoothedUnmarkedAmount.in=" + UPDATED_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByBudgetUnsmoothedUnmarkedAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        // Get all the reportDataByDateList where budgetUnsmoothedUnmarkedAmount is not null
        defaultReportDataByDateShouldBeFound("budgetUnsmoothedUnmarkedAmount.specified=true");

        // Get all the reportDataByDateList where budgetUnsmoothedUnmarkedAmount is null
        defaultReportDataByDateShouldNotBeFound("budgetUnsmoothedUnmarkedAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportDataByDatesByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        Category category = CategoryResourceIntTest.createEntity(em);
        em.persist(category);
        em.flush();
        reportDataByDate.setCategory(category);
        reportDataByDateRepository.saveAndFlush(reportDataByDate);
        Long categoryId = category.getId();

        // Get all the reportDataByDateList where category equals to categoryId
        defaultReportDataByDateShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the reportDataByDateList where category equals to categoryId + 1
        defaultReportDataByDateShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }


    @Test
    @Transactional
    public void getAllReportDataByDatesByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        User account = UserResourceIntTest.createEntity(em);
        em.persist(account);
        em.flush();
        reportDataByDate.setAccount(account);
        reportDataByDateRepository.saveAndFlush(reportDataByDate);
        Long accountId = account.getId();

        // Get all the reportDataByDateList where account equals to accountId
        defaultReportDataByDateShouldBeFound("accountId.equals=" + accountId);

        // Get all the reportDataByDateList where account equals to accountId + 1
        defaultReportDataByDateShouldNotBeFound("accountId.equals=" + (accountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultReportDataByDateShouldBeFound(String filter) throws Exception {
        restReportDataByDateMockMvc.perform(get("/api/report-data-by-dates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportDataByDate.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].hasOperation").value(hasItem(DEFAULT_HAS_OPERATION.booleanValue())))
            .andExpect(jsonPath("$.[*].operationAmount").value(hasItem(DEFAULT_OPERATION_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].budgetSmoothedAmount").value(hasItem(DEFAULT_BUDGET_SMOOTHED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].budgetUnsmoothedMarkedAmount").value(hasItem(DEFAULT_BUDGET_UNSMOOTHED_MARKED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].budgetUnsmoothedUnmarkedAmount").value(hasItem(DEFAULT_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultReportDataByDateShouldNotBeFound(String filter) throws Exception {
        restReportDataByDateMockMvc.perform(get("/api/report-data-by-dates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingReportDataByDate() throws Exception {
        // Get the reportDataByDate
        restReportDataByDateMockMvc.perform(get("/api/report-data-by-dates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReportDataByDate() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        int databaseSizeBeforeUpdate = reportDataByDateRepository.findAll().size();

        // Update the reportDataByDate
        ReportDataByDate updatedReportDataByDate = reportDataByDateRepository.findById(reportDataByDate.getId()).get();
        // Disconnect from session so that the updates on updatedReportDataByDate are not directly saved in db
        em.detach(updatedReportDataByDate);
        updatedReportDataByDate
            .date(UPDATED_DATE)
            .month(UPDATED_MONTH)
            .hasOperation(UPDATED_HAS_OPERATION)
            .operationAmount(UPDATED_OPERATION_AMOUNT)
            .budgetSmoothedAmount(UPDATED_BUDGET_SMOOTHED_AMOUNT)
            .budgetUnsmoothedMarkedAmount(UPDATED_BUDGET_UNSMOOTHED_MARKED_AMOUNT)
            .budgetUnsmoothedUnmarkedAmount(UPDATED_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT);
        ReportDataByDateDTO reportDataByDateDTO = reportDataByDateMapper.toDto(updatedReportDataByDate);

        restReportDataByDateMockMvc.perform(put("/api/report-data-by-dates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDataByDateDTO)))
            .andExpect(status().isOk());

        // Validate the ReportDataByDate in the database
        List<ReportDataByDate> reportDataByDateList = reportDataByDateRepository.findAll();
        assertThat(reportDataByDateList).hasSize(databaseSizeBeforeUpdate);
        ReportDataByDate testReportDataByDate = reportDataByDateList.get(reportDataByDateList.size() - 1);
        assertThat(testReportDataByDate.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testReportDataByDate.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testReportDataByDate.isHasOperation()).isEqualTo(UPDATED_HAS_OPERATION);
        assertThat(testReportDataByDate.getOperationAmount()).isEqualTo(UPDATED_OPERATION_AMOUNT);
        assertThat(testReportDataByDate.getBudgetSmoothedAmount()).isEqualTo(UPDATED_BUDGET_SMOOTHED_AMOUNT);
        assertThat(testReportDataByDate.getBudgetUnsmoothedMarkedAmount()).isEqualTo(UPDATED_BUDGET_UNSMOOTHED_MARKED_AMOUNT);
        assertThat(testReportDataByDate.getBudgetUnsmoothedUnmarkedAmount()).isEqualTo(UPDATED_BUDGET_UNSMOOTHED_UNMARKED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingReportDataByDate() throws Exception {
        int databaseSizeBeforeUpdate = reportDataByDateRepository.findAll().size();

        // Create the ReportDataByDate
        ReportDataByDateDTO reportDataByDateDTO = reportDataByDateMapper.toDto(reportDataByDate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReportDataByDateMockMvc.perform(put("/api/report-data-by-dates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDataByDateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportDataByDate in the database
        List<ReportDataByDate> reportDataByDateList = reportDataByDateRepository.findAll();
        assertThat(reportDataByDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReportDataByDate() throws Exception {
        // Initialize the database
        reportDataByDateRepository.saveAndFlush(reportDataByDate);

        int databaseSizeBeforeDelete = reportDataByDateRepository.findAll().size();

        // Get the reportDataByDate
        restReportDataByDateMockMvc.perform(delete("/api/report-data-by-dates/{id}", reportDataByDate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReportDataByDate> reportDataByDateList = reportDataByDateRepository.findAll();
        assertThat(reportDataByDateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportDataByDate.class);
        ReportDataByDate reportDataByDate1 = new ReportDataByDate();
        reportDataByDate1.setId(1L);
        ReportDataByDate reportDataByDate2 = new ReportDataByDate();
        reportDataByDate2.setId(reportDataByDate1.getId());
        assertThat(reportDataByDate1).isEqualTo(reportDataByDate2);
        reportDataByDate2.setId(2L);
        assertThat(reportDataByDate1).isNotEqualTo(reportDataByDate2);
        reportDataByDate1.setId(null);
        assertThat(reportDataByDate1).isNotEqualTo(reportDataByDate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportDataByDateDTO.class);
        ReportDataByDateDTO reportDataByDateDTO1 = new ReportDataByDateDTO();
        reportDataByDateDTO1.setId(1L);
        ReportDataByDateDTO reportDataByDateDTO2 = new ReportDataByDateDTO();
        assertThat(reportDataByDateDTO1).isNotEqualTo(reportDataByDateDTO2);
        reportDataByDateDTO2.setId(reportDataByDateDTO1.getId());
        assertThat(reportDataByDateDTO1).isEqualTo(reportDataByDateDTO2);
        reportDataByDateDTO2.setId(2L);
        assertThat(reportDataByDateDTO1).isNotEqualTo(reportDataByDateDTO2);
        reportDataByDateDTO1.setId(null);
        assertThat(reportDataByDateDTO1).isNotEqualTo(reportDataByDateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reportDataByDateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reportDataByDateMapper.fromId(null)).isNull();
    }
}
