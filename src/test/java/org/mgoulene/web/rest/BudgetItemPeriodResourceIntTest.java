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
import org.mgoulene.domain.BudgetItem;
import org.mgoulene.domain.BudgetItemPeriod;
import org.mgoulene.domain.Operation;
import org.mgoulene.repository.BudgetItemPeriodRepository;
import org.mgoulene.service.BudgetItemPeriodQueryService;
import org.mgoulene.service.BudgetItemPeriodService;
import org.mgoulene.service.dto.BudgetItemPeriodDTO;
import org.mgoulene.service.mapper.BudgetItemPeriodMapper;
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
 * Test class for the BudgetItemPeriodResource REST controller.
 *
 * @see BudgetItemPeriodResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyaccountApp.class)
public class BudgetItemPeriodResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MONTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MONTH = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;

    private static final Boolean DEFAULT_IS_SMOOTHED = false;
    private static final Boolean UPDATED_IS_SMOOTHED = true;

    private static final Boolean DEFAULT_IS_RECURRENT = false;
    private static final Boolean UPDATED_IS_RECURRENT = true;

    @Autowired
    private BudgetItemPeriodRepository budgetItemPeriodRepository;


    @Autowired
    private BudgetItemPeriodMapper budgetItemPeriodMapper;
    

    @Autowired
    private BudgetItemPeriodService budgetItemPeriodService;

    @Autowired
    private BudgetItemPeriodQueryService budgetItemPeriodQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBudgetItemPeriodMockMvc;

    private BudgetItemPeriod budgetItemPeriod;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BudgetItemPeriodResource budgetItemPeriodResource = new BudgetItemPeriodResource(budgetItemPeriodService, budgetItemPeriodQueryService);
        this.restBudgetItemPeriodMockMvc = MockMvcBuilders.standaloneSetup(budgetItemPeriodResource)
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
    public static BudgetItemPeriod createEntity(EntityManager em) {
        BudgetItemPeriod budgetItemPeriod = new BudgetItemPeriod()
            .date(DEFAULT_DATE)
            .month(DEFAULT_MONTH)
            .amount(DEFAULT_AMOUNT)
            .isSmoothed(DEFAULT_IS_SMOOTHED)
            .isRecurrent(DEFAULT_IS_RECURRENT);
        return budgetItemPeriod;
    }

    @Before
    public void initTest() {
        budgetItemPeriod = createEntity(em);
    }

    @Test
    @Transactional
    public void createBudgetItemPeriod() throws Exception {
        int databaseSizeBeforeCreate = budgetItemPeriodRepository.findAll().size();

        // Create the BudgetItemPeriod
        BudgetItemPeriodDTO budgetItemPeriodDTO = budgetItemPeriodMapper.toDto(budgetItemPeriod);
        restBudgetItemPeriodMockMvc.perform(post("/api/budget-item-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budgetItemPeriodDTO)))
            .andExpect(status().isCreated());

        // Validate the BudgetItemPeriod in the database
        List<BudgetItemPeriod> budgetItemPeriodList = budgetItemPeriodRepository.findAll();
        assertThat(budgetItemPeriodList).hasSize(databaseSizeBeforeCreate + 1);
        BudgetItemPeriod testBudgetItemPeriod = budgetItemPeriodList.get(budgetItemPeriodList.size() - 1);
        assertThat(testBudgetItemPeriod.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testBudgetItemPeriod.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testBudgetItemPeriod.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testBudgetItemPeriod.isIsSmoothed()).isEqualTo(DEFAULT_IS_SMOOTHED);
        assertThat(testBudgetItemPeriod.isIsRecurrent()).isEqualTo(DEFAULT_IS_RECURRENT);
    }

    @Test
    @Transactional
    public void createBudgetItemPeriodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = budgetItemPeriodRepository.findAll().size();

        // Create the BudgetItemPeriod with an existing ID
        budgetItemPeriod.setId(1L);
        BudgetItemPeriodDTO budgetItemPeriodDTO = budgetItemPeriodMapper.toDto(budgetItemPeriod);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBudgetItemPeriodMockMvc.perform(post("/api/budget-item-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budgetItemPeriodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BudgetItemPeriod in the database
        List<BudgetItemPeriod> budgetItemPeriodList = budgetItemPeriodRepository.findAll();
        assertThat(budgetItemPeriodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = budgetItemPeriodRepository.findAll().size();
        // set the field null
        budgetItemPeriod.setMonth(null);

        // Create the BudgetItemPeriod, which fails.
        BudgetItemPeriodDTO budgetItemPeriodDTO = budgetItemPeriodMapper.toDto(budgetItemPeriod);

        restBudgetItemPeriodMockMvc.perform(post("/api/budget-item-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budgetItemPeriodDTO)))
            .andExpect(status().isBadRequest());

        List<BudgetItemPeriod> budgetItemPeriodList = budgetItemPeriodRepository.findAll();
        assertThat(budgetItemPeriodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = budgetItemPeriodRepository.findAll().size();
        // set the field null
        budgetItemPeriod.setAmount(null);

        // Create the BudgetItemPeriod, which fails.
        BudgetItemPeriodDTO budgetItemPeriodDTO = budgetItemPeriodMapper.toDto(budgetItemPeriod);

        restBudgetItemPeriodMockMvc.perform(post("/api/budget-item-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budgetItemPeriodDTO)))
            .andExpect(status().isBadRequest());

        List<BudgetItemPeriod> budgetItemPeriodList = budgetItemPeriodRepository.findAll();
        assertThat(budgetItemPeriodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriods() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList
        restBudgetItemPeriodMockMvc.perform(get("/api/budget-item-periods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(budgetItemPeriod.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].isSmoothed").value(hasItem(DEFAULT_IS_SMOOTHED.booleanValue())))
            .andExpect(jsonPath("$.[*].isRecurrent").value(hasItem(DEFAULT_IS_RECURRENT.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getBudgetItemPeriod() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get the budgetItemPeriod
        restBudgetItemPeriodMockMvc.perform(get("/api/budget-item-periods/{id}", budgetItemPeriod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(budgetItemPeriod.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.isSmoothed").value(DEFAULT_IS_SMOOTHED.booleanValue()))
            .andExpect(jsonPath("$.isRecurrent").value(DEFAULT_IS_RECURRENT.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where date equals to DEFAULT_DATE
        defaultBudgetItemPeriodShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the budgetItemPeriodList where date equals to UPDATED_DATE
        defaultBudgetItemPeriodShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where date in DEFAULT_DATE or UPDATED_DATE
        defaultBudgetItemPeriodShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the budgetItemPeriodList where date equals to UPDATED_DATE
        defaultBudgetItemPeriodShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where date is not null
        defaultBudgetItemPeriodShouldBeFound("date.specified=true");

        // Get all the budgetItemPeriodList where date is null
        defaultBudgetItemPeriodShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where date greater than or equals to DEFAULT_DATE
        defaultBudgetItemPeriodShouldBeFound("date.greaterOrEqualThan=" + DEFAULT_DATE);

        // Get all the budgetItemPeriodList where date greater than or equals to UPDATED_DATE
        defaultBudgetItemPeriodShouldNotBeFound("date.greaterOrEqualThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where date less than or equals to DEFAULT_DATE
        defaultBudgetItemPeriodShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the budgetItemPeriodList where date less than or equals to UPDATED_DATE
        defaultBudgetItemPeriodShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where month equals to DEFAULT_MONTH
        defaultBudgetItemPeriodShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the budgetItemPeriodList where month equals to UPDATED_MONTH
        defaultBudgetItemPeriodShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultBudgetItemPeriodShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the budgetItemPeriodList where month equals to UPDATED_MONTH
        defaultBudgetItemPeriodShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where month is not null
        defaultBudgetItemPeriodShouldBeFound("month.specified=true");

        // Get all the budgetItemPeriodList where month is null
        defaultBudgetItemPeriodShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByMonthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where month greater than or equals to DEFAULT_MONTH
        defaultBudgetItemPeriodShouldBeFound("month.greaterOrEqualThan=" + DEFAULT_MONTH);

        // Get all the budgetItemPeriodList where month greater than or equals to UPDATED_MONTH
        defaultBudgetItemPeriodShouldNotBeFound("month.greaterOrEqualThan=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByMonthIsLessThanSomething() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where month less than or equals to DEFAULT_MONTH
        defaultBudgetItemPeriodShouldNotBeFound("month.lessThan=" + DEFAULT_MONTH);

        // Get all the budgetItemPeriodList where month less than or equals to UPDATED_MONTH
        defaultBudgetItemPeriodShouldBeFound("month.lessThan=" + UPDATED_MONTH);
    }


    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where amount equals to DEFAULT_AMOUNT
        defaultBudgetItemPeriodShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the budgetItemPeriodList where amount equals to UPDATED_AMOUNT
        defaultBudgetItemPeriodShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultBudgetItemPeriodShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the budgetItemPeriodList where amount equals to UPDATED_AMOUNT
        defaultBudgetItemPeriodShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where amount is not null
        defaultBudgetItemPeriodShouldBeFound("amount.specified=true");

        // Get all the budgetItemPeriodList where amount is null
        defaultBudgetItemPeriodShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByIsSmoothedIsEqualToSomething() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where isSmoothed equals to DEFAULT_IS_SMOOTHED
        defaultBudgetItemPeriodShouldBeFound("isSmoothed.equals=" + DEFAULT_IS_SMOOTHED);

        // Get all the budgetItemPeriodList where isSmoothed equals to UPDATED_IS_SMOOTHED
        defaultBudgetItemPeriodShouldNotBeFound("isSmoothed.equals=" + UPDATED_IS_SMOOTHED);
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByIsSmoothedIsInShouldWork() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where isSmoothed in DEFAULT_IS_SMOOTHED or UPDATED_IS_SMOOTHED
        defaultBudgetItemPeriodShouldBeFound("isSmoothed.in=" + DEFAULT_IS_SMOOTHED + "," + UPDATED_IS_SMOOTHED);

        // Get all the budgetItemPeriodList where isSmoothed equals to UPDATED_IS_SMOOTHED
        defaultBudgetItemPeriodShouldNotBeFound("isSmoothed.in=" + UPDATED_IS_SMOOTHED);
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByIsSmoothedIsNullOrNotNull() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where isSmoothed is not null
        defaultBudgetItemPeriodShouldBeFound("isSmoothed.specified=true");

        // Get all the budgetItemPeriodList where isSmoothed is null
        defaultBudgetItemPeriodShouldNotBeFound("isSmoothed.specified=false");
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByIsRecurrentIsEqualToSomething() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where isRecurrent equals to DEFAULT_IS_RECURRENT
        defaultBudgetItemPeriodShouldBeFound("isRecurrent.equals=" + DEFAULT_IS_RECURRENT);

        // Get all the budgetItemPeriodList where isRecurrent equals to UPDATED_IS_RECURRENT
        defaultBudgetItemPeriodShouldNotBeFound("isRecurrent.equals=" + UPDATED_IS_RECURRENT);
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByIsRecurrentIsInShouldWork() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where isRecurrent in DEFAULT_IS_RECURRENT or UPDATED_IS_RECURRENT
        defaultBudgetItemPeriodShouldBeFound("isRecurrent.in=" + DEFAULT_IS_RECURRENT + "," + UPDATED_IS_RECURRENT);

        // Get all the budgetItemPeriodList where isRecurrent equals to UPDATED_IS_RECURRENT
        defaultBudgetItemPeriodShouldNotBeFound("isRecurrent.in=" + UPDATED_IS_RECURRENT);
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByIsRecurrentIsNullOrNotNull() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        // Get all the budgetItemPeriodList where isRecurrent is not null
        defaultBudgetItemPeriodShouldBeFound("isRecurrent.specified=true");

        // Get all the budgetItemPeriodList where isRecurrent is null
        defaultBudgetItemPeriodShouldNotBeFound("isRecurrent.specified=false");
    }

    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByBudgetItemIsEqualToSomething() throws Exception {
        // Initialize the database
        BudgetItem budgetItem = BudgetItemResourceIntTest.createEntity(em);
        em.persist(budgetItem);
        em.flush();
        budgetItemPeriod.setBudgetItem(budgetItem);
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);
        Long budgetItemId = budgetItem.getId();

        // Get all the budgetItemPeriodList where budgetItem equals to budgetItemId
        defaultBudgetItemPeriodShouldBeFound("budgetItemId.equals=" + budgetItemId);

        // Get all the budgetItemPeriodList where budgetItem equals to budgetItemId + 1
        defaultBudgetItemPeriodShouldNotBeFound("budgetItemId.equals=" + (budgetItemId + 1));
    }


    @Test
    @Transactional
    public void getAllBudgetItemPeriodsByOperationIsEqualToSomething() throws Exception {
        // Initialize the database
        Operation operation = OperationResourceIntTest.createEntity(em);
        em.persist(operation);
        em.flush();
        budgetItemPeriod.setOperation(operation);
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);
        Long operationId = operation.getId();

        // Get all the budgetItemPeriodList where operation equals to operationId
        defaultBudgetItemPeriodShouldBeFound("operationId.equals=" + operationId);

        // Get all the budgetItemPeriodList where operation equals to operationId + 1
        defaultBudgetItemPeriodShouldNotBeFound("operationId.equals=" + (operationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBudgetItemPeriodShouldBeFound(String filter) throws Exception {
        restBudgetItemPeriodMockMvc.perform(get("/api/budget-item-periods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(budgetItemPeriod.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].isSmoothed").value(hasItem(DEFAULT_IS_SMOOTHED.booleanValue())))
            .andExpect(jsonPath("$.[*].isRecurrent").value(hasItem(DEFAULT_IS_RECURRENT.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBudgetItemPeriodShouldNotBeFound(String filter) throws Exception {
        restBudgetItemPeriodMockMvc.perform(get("/api/budget-item-periods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingBudgetItemPeriod() throws Exception {
        // Get the budgetItemPeriod
        restBudgetItemPeriodMockMvc.perform(get("/api/budget-item-periods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBudgetItemPeriod() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        int databaseSizeBeforeUpdate = budgetItemPeriodRepository.findAll().size();

        // Update the budgetItemPeriod
        BudgetItemPeriod updatedBudgetItemPeriod = budgetItemPeriodRepository.findById(budgetItemPeriod.getId()).get();
        // Disconnect from session so that the updates on updatedBudgetItemPeriod are not directly saved in db
        em.detach(updatedBudgetItemPeriod);
        updatedBudgetItemPeriod
            .date(UPDATED_DATE)
            .month(UPDATED_MONTH)
            .amount(UPDATED_AMOUNT)
            .isSmoothed(UPDATED_IS_SMOOTHED)
            .isRecurrent(UPDATED_IS_RECURRENT);
        BudgetItemPeriodDTO budgetItemPeriodDTO = budgetItemPeriodMapper.toDto(updatedBudgetItemPeriod);

        restBudgetItemPeriodMockMvc.perform(put("/api/budget-item-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budgetItemPeriodDTO)))
            .andExpect(status().isOk());

        // Validate the BudgetItemPeriod in the database
        List<BudgetItemPeriod> budgetItemPeriodList = budgetItemPeriodRepository.findAll();
        assertThat(budgetItemPeriodList).hasSize(databaseSizeBeforeUpdate);
        BudgetItemPeriod testBudgetItemPeriod = budgetItemPeriodList.get(budgetItemPeriodList.size() - 1);
        assertThat(testBudgetItemPeriod.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testBudgetItemPeriod.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testBudgetItemPeriod.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBudgetItemPeriod.isIsSmoothed()).isEqualTo(UPDATED_IS_SMOOTHED);
        assertThat(testBudgetItemPeriod.isIsRecurrent()).isEqualTo(UPDATED_IS_RECURRENT);
    }

    @Test
    @Transactional
    public void updateNonExistingBudgetItemPeriod() throws Exception {
        int databaseSizeBeforeUpdate = budgetItemPeriodRepository.findAll().size();

        // Create the BudgetItemPeriod
        BudgetItemPeriodDTO budgetItemPeriodDTO = budgetItemPeriodMapper.toDto(budgetItemPeriod);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBudgetItemPeriodMockMvc.perform(put("/api/budget-item-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budgetItemPeriodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BudgetItemPeriod in the database
        List<BudgetItemPeriod> budgetItemPeriodList = budgetItemPeriodRepository.findAll();
        assertThat(budgetItemPeriodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBudgetItemPeriod() throws Exception {
        // Initialize the database
        budgetItemPeriodRepository.saveAndFlush(budgetItemPeriod);

        int databaseSizeBeforeDelete = budgetItemPeriodRepository.findAll().size();

        // Get the budgetItemPeriod
        restBudgetItemPeriodMockMvc.perform(delete("/api/budget-item-periods/{id}", budgetItemPeriod.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BudgetItemPeriod> budgetItemPeriodList = budgetItemPeriodRepository.findAll();
        assertThat(budgetItemPeriodList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BudgetItemPeriod.class);
        BudgetItemPeriod budgetItemPeriod1 = new BudgetItemPeriod();
        budgetItemPeriod1.setId(1L);
        BudgetItemPeriod budgetItemPeriod2 = new BudgetItemPeriod();
        budgetItemPeriod2.setId(budgetItemPeriod1.getId());
        assertThat(budgetItemPeriod1).isEqualTo(budgetItemPeriod2);
        budgetItemPeriod2.setId(2L);
        assertThat(budgetItemPeriod1).isNotEqualTo(budgetItemPeriod2);
        budgetItemPeriod1.setId(null);
        assertThat(budgetItemPeriod1).isNotEqualTo(budgetItemPeriod2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BudgetItemPeriodDTO.class);
        BudgetItemPeriodDTO budgetItemPeriodDTO1 = new BudgetItemPeriodDTO();
        budgetItemPeriodDTO1.setId(1L);
        BudgetItemPeriodDTO budgetItemPeriodDTO2 = new BudgetItemPeriodDTO();
        assertThat(budgetItemPeriodDTO1).isNotEqualTo(budgetItemPeriodDTO2);
        budgetItemPeriodDTO2.setId(budgetItemPeriodDTO1.getId());
        assertThat(budgetItemPeriodDTO1).isEqualTo(budgetItemPeriodDTO2);
        budgetItemPeriodDTO2.setId(2L);
        assertThat(budgetItemPeriodDTO1).isNotEqualTo(budgetItemPeriodDTO2);
        budgetItemPeriodDTO1.setId(null);
        assertThat(budgetItemPeriodDTO1).isNotEqualTo(budgetItemPeriodDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(budgetItemPeriodMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(budgetItemPeriodMapper.fromId(null)).isNull();
    }
}
