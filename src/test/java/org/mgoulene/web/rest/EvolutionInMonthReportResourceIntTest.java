package org.mgoulene.web.rest;

import org.mgoulene.MyaccountApp;

import org.mgoulene.domain.EvolutionInMonthReport;
import org.mgoulene.domain.User;
import org.mgoulene.repository.EvolutionInMonthReportRepository;
import org.mgoulene.service.EvolutionInMonthReportService;
import org.mgoulene.service.dto.EvolutionInMonthReportDTO;
import org.mgoulene.service.mapper.EvolutionInMonthReportMapper;
import org.mgoulene.web.rest.errors.ExceptionTranslator;
import org.mgoulene.service.dto.EvolutionInMonthReportCriteria;
import org.mgoulene.service.EvolutionInMonthReportQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static org.mgoulene.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EvolutionInMonthReportResource REST controller.
 *
 * @see EvolutionInMonthReportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyaccountApp.class)
public class EvolutionInMonthReportResourceIntTest {

    private static final LocalDate DEFAULT_MONTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MONTH = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_OPERATION = 1F;
    private static final Float UPDATED_OPERATION = 2F;

    private static final Float DEFAULT_BUDGET = 1F;
    private static final Float UPDATED_BUDGET = 2F;

    @Autowired
    private EvolutionInMonthReportRepository evolutionInMonthReportRepository;


    @Autowired
    private EvolutionInMonthReportMapper evolutionInMonthReportMapper;
    

    @Autowired
    private EvolutionInMonthReportService evolutionInMonthReportService;

    @Autowired
    private EvolutionInMonthReportQueryService evolutionInMonthReportQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEvolutionInMonthReportMockMvc;

    private EvolutionInMonthReport evolutionInMonthReport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EvolutionInMonthReportResource evolutionInMonthReportResource = new EvolutionInMonthReportResource(evolutionInMonthReportService, evolutionInMonthReportQueryService);
        this.restEvolutionInMonthReportMockMvc = MockMvcBuilders.standaloneSetup(evolutionInMonthReportResource)
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
    public static EvolutionInMonthReport createEntity(EntityManager em) {
        EvolutionInMonthReport evolutionInMonthReport = new EvolutionInMonthReport()
            .month(DEFAULT_MONTH)
            .operation(DEFAULT_OPERATION)
            .budget(DEFAULT_BUDGET);
        return evolutionInMonthReport;
    }

    @Before
    public void initTest() {
        evolutionInMonthReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvolutionInMonthReport() throws Exception {
        int databaseSizeBeforeCreate = evolutionInMonthReportRepository.findAll().size();

        // Create the EvolutionInMonthReport
        EvolutionInMonthReportDTO evolutionInMonthReportDTO = evolutionInMonthReportMapper.toDto(evolutionInMonthReport);
        restEvolutionInMonthReportMockMvc.perform(post("/api/evolution-in-month-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evolutionInMonthReportDTO)))
            .andExpect(status().isCreated());

        // Validate the EvolutionInMonthReport in the database
        List<EvolutionInMonthReport> evolutionInMonthReportList = evolutionInMonthReportRepository.findAll();
        assertThat(evolutionInMonthReportList).hasSize(databaseSizeBeforeCreate + 1);
        EvolutionInMonthReport testEvolutionInMonthReport = evolutionInMonthReportList.get(evolutionInMonthReportList.size() - 1);
        assertThat(testEvolutionInMonthReport.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testEvolutionInMonthReport.getOperation()).isEqualTo(DEFAULT_OPERATION);
        assertThat(testEvolutionInMonthReport.getBudget()).isEqualTo(DEFAULT_BUDGET);
    }

    @Test
    @Transactional
    public void createEvolutionInMonthReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evolutionInMonthReportRepository.findAll().size();

        // Create the EvolutionInMonthReport with an existing ID
        evolutionInMonthReport.setId(1L);
        EvolutionInMonthReportDTO evolutionInMonthReportDTO = evolutionInMonthReportMapper.toDto(evolutionInMonthReport);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvolutionInMonthReportMockMvc.perform(post("/api/evolution-in-month-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evolutionInMonthReportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EvolutionInMonthReport in the database
        List<EvolutionInMonthReport> evolutionInMonthReportList = evolutionInMonthReportRepository.findAll();
        assertThat(evolutionInMonthReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = evolutionInMonthReportRepository.findAll().size();
        // set the field null
        evolutionInMonthReport.setMonth(null);

        // Create the EvolutionInMonthReport, which fails.
        EvolutionInMonthReportDTO evolutionInMonthReportDTO = evolutionInMonthReportMapper.toDto(evolutionInMonthReport);

        restEvolutionInMonthReportMockMvc.perform(post("/api/evolution-in-month-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evolutionInMonthReportDTO)))
            .andExpect(status().isBadRequest());

        List<EvolutionInMonthReport> evolutionInMonthReportList = evolutionInMonthReportRepository.findAll();
        assertThat(evolutionInMonthReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOperationIsRequired() throws Exception {
        int databaseSizeBeforeTest = evolutionInMonthReportRepository.findAll().size();
        // set the field null
        evolutionInMonthReport.setOperation(null);

        // Create the EvolutionInMonthReport, which fails.
        EvolutionInMonthReportDTO evolutionInMonthReportDTO = evolutionInMonthReportMapper.toDto(evolutionInMonthReport);

        restEvolutionInMonthReportMockMvc.perform(post("/api/evolution-in-month-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evolutionInMonthReportDTO)))
            .andExpect(status().isBadRequest());

        List<EvolutionInMonthReport> evolutionInMonthReportList = evolutionInMonthReportRepository.findAll();
        assertThat(evolutionInMonthReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBudgetIsRequired() throws Exception {
        int databaseSizeBeforeTest = evolutionInMonthReportRepository.findAll().size();
        // set the field null
        evolutionInMonthReport.setBudget(null);

        // Create the EvolutionInMonthReport, which fails.
        EvolutionInMonthReportDTO evolutionInMonthReportDTO = evolutionInMonthReportMapper.toDto(evolutionInMonthReport);

        restEvolutionInMonthReportMockMvc.perform(post("/api/evolution-in-month-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evolutionInMonthReportDTO)))
            .andExpect(status().isBadRequest());

        List<EvolutionInMonthReport> evolutionInMonthReportList = evolutionInMonthReportRepository.findAll();
        assertThat(evolutionInMonthReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEvolutionInMonthReports() throws Exception {
        // Initialize the database
        evolutionInMonthReportRepository.saveAndFlush(evolutionInMonthReport);

        // Get all the evolutionInMonthReportList
        restEvolutionInMonthReportMockMvc.perform(get("/api/evolution-in-month-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evolutionInMonthReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].operation").value(hasItem(DEFAULT_OPERATION.doubleValue())))
            .andExpect(jsonPath("$.[*].budget").value(hasItem(DEFAULT_BUDGET.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getEvolutionInMonthReport() throws Exception {
        // Initialize the database
        evolutionInMonthReportRepository.saveAndFlush(evolutionInMonthReport);

        // Get the evolutionInMonthReport
        restEvolutionInMonthReportMockMvc.perform(get("/api/evolution-in-month-reports/{id}", evolutionInMonthReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evolutionInMonthReport.getId().intValue()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.operation").value(DEFAULT_OPERATION.doubleValue()))
            .andExpect(jsonPath("$.budget").value(DEFAULT_BUDGET.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllEvolutionInMonthReportsByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        evolutionInMonthReportRepository.saveAndFlush(evolutionInMonthReport);

        // Get all the evolutionInMonthReportList where month equals to DEFAULT_MONTH
        defaultEvolutionInMonthReportShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the evolutionInMonthReportList where month equals to UPDATED_MONTH
        defaultEvolutionInMonthReportShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllEvolutionInMonthReportsByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        evolutionInMonthReportRepository.saveAndFlush(evolutionInMonthReport);

        // Get all the evolutionInMonthReportList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultEvolutionInMonthReportShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the evolutionInMonthReportList where month equals to UPDATED_MONTH
        defaultEvolutionInMonthReportShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllEvolutionInMonthReportsByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        evolutionInMonthReportRepository.saveAndFlush(evolutionInMonthReport);

        // Get all the evolutionInMonthReportList where month is not null
        defaultEvolutionInMonthReportShouldBeFound("month.specified=true");

        // Get all the evolutionInMonthReportList where month is null
        defaultEvolutionInMonthReportShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    public void getAllEvolutionInMonthReportsByMonthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evolutionInMonthReportRepository.saveAndFlush(evolutionInMonthReport);

        // Get all the evolutionInMonthReportList where month greater than or equals to DEFAULT_MONTH
        defaultEvolutionInMonthReportShouldBeFound("month.greaterOrEqualThan=" + DEFAULT_MONTH);

        // Get all the evolutionInMonthReportList where month greater than or equals to UPDATED_MONTH
        defaultEvolutionInMonthReportShouldNotBeFound("month.greaterOrEqualThan=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllEvolutionInMonthReportsByMonthIsLessThanSomething() throws Exception {
        // Initialize the database
        evolutionInMonthReportRepository.saveAndFlush(evolutionInMonthReport);

        // Get all the evolutionInMonthReportList where month less than or equals to DEFAULT_MONTH
        defaultEvolutionInMonthReportShouldNotBeFound("month.lessThan=" + DEFAULT_MONTH);

        // Get all the evolutionInMonthReportList where month less than or equals to UPDATED_MONTH
        defaultEvolutionInMonthReportShouldBeFound("month.lessThan=" + UPDATED_MONTH);
    }


    @Test
    @Transactional
    public void getAllEvolutionInMonthReportsByOperationIsEqualToSomething() throws Exception {
        // Initialize the database
        evolutionInMonthReportRepository.saveAndFlush(evolutionInMonthReport);

        // Get all the evolutionInMonthReportList where operation equals to DEFAULT_OPERATION
        defaultEvolutionInMonthReportShouldBeFound("operation.equals=" + DEFAULT_OPERATION);

        // Get all the evolutionInMonthReportList where operation equals to UPDATED_OPERATION
        defaultEvolutionInMonthReportShouldNotBeFound("operation.equals=" + UPDATED_OPERATION);
    }

    @Test
    @Transactional
    public void getAllEvolutionInMonthReportsByOperationIsInShouldWork() throws Exception {
        // Initialize the database
        evolutionInMonthReportRepository.saveAndFlush(evolutionInMonthReport);

        // Get all the evolutionInMonthReportList where operation in DEFAULT_OPERATION or UPDATED_OPERATION
        defaultEvolutionInMonthReportShouldBeFound("operation.in=" + DEFAULT_OPERATION + "," + UPDATED_OPERATION);

        // Get all the evolutionInMonthReportList where operation equals to UPDATED_OPERATION
        defaultEvolutionInMonthReportShouldNotBeFound("operation.in=" + UPDATED_OPERATION);
    }

    @Test
    @Transactional
    public void getAllEvolutionInMonthReportsByOperationIsNullOrNotNull() throws Exception {
        // Initialize the database
        evolutionInMonthReportRepository.saveAndFlush(evolutionInMonthReport);

        // Get all the evolutionInMonthReportList where operation is not null
        defaultEvolutionInMonthReportShouldBeFound("operation.specified=true");

        // Get all the evolutionInMonthReportList where operation is null
        defaultEvolutionInMonthReportShouldNotBeFound("operation.specified=false");
    }

    @Test
    @Transactional
    public void getAllEvolutionInMonthReportsByBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        evolutionInMonthReportRepository.saveAndFlush(evolutionInMonthReport);

        // Get all the evolutionInMonthReportList where budget equals to DEFAULT_BUDGET
        defaultEvolutionInMonthReportShouldBeFound("budget.equals=" + DEFAULT_BUDGET);

        // Get all the evolutionInMonthReportList where budget equals to UPDATED_BUDGET
        defaultEvolutionInMonthReportShouldNotBeFound("budget.equals=" + UPDATED_BUDGET);
    }

    @Test
    @Transactional
    public void getAllEvolutionInMonthReportsByBudgetIsInShouldWork() throws Exception {
        // Initialize the database
        evolutionInMonthReportRepository.saveAndFlush(evolutionInMonthReport);

        // Get all the evolutionInMonthReportList where budget in DEFAULT_BUDGET or UPDATED_BUDGET
        defaultEvolutionInMonthReportShouldBeFound("budget.in=" + DEFAULT_BUDGET + "," + UPDATED_BUDGET);

        // Get all the evolutionInMonthReportList where budget equals to UPDATED_BUDGET
        defaultEvolutionInMonthReportShouldNotBeFound("budget.in=" + UPDATED_BUDGET);
    }

    @Test
    @Transactional
    public void getAllEvolutionInMonthReportsByBudgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        evolutionInMonthReportRepository.saveAndFlush(evolutionInMonthReport);

        // Get all the evolutionInMonthReportList where budget is not null
        defaultEvolutionInMonthReportShouldBeFound("budget.specified=true");

        // Get all the evolutionInMonthReportList where budget is null
        defaultEvolutionInMonthReportShouldNotBeFound("budget.specified=false");
    }

    @Test
    @Transactional
    public void getAllEvolutionInMonthReportsByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        User account = UserResourceIntTest.createEntity(em);
        em.persist(account);
        em.flush();
        evolutionInMonthReport.setAccount(account);
        evolutionInMonthReportRepository.saveAndFlush(evolutionInMonthReport);
        Long accountId = account.getId();

        // Get all the evolutionInMonthReportList where account equals to accountId
        defaultEvolutionInMonthReportShouldBeFound("accountId.equals=" + accountId);

        // Get all the evolutionInMonthReportList where account equals to accountId + 1
        defaultEvolutionInMonthReportShouldNotBeFound("accountId.equals=" + (accountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEvolutionInMonthReportShouldBeFound(String filter) throws Exception {
        restEvolutionInMonthReportMockMvc.perform(get("/api/evolution-in-month-reports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evolutionInMonthReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].operation").value(hasItem(DEFAULT_OPERATION.doubleValue())))
            .andExpect(jsonPath("$.[*].budget").value(hasItem(DEFAULT_BUDGET.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEvolutionInMonthReportShouldNotBeFound(String filter) throws Exception {
        restEvolutionInMonthReportMockMvc.perform(get("/api/evolution-in-month-reports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingEvolutionInMonthReport() throws Exception {
        // Get the evolutionInMonthReport
        restEvolutionInMonthReportMockMvc.perform(get("/api/evolution-in-month-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvolutionInMonthReport() throws Exception {
        // Initialize the database
        evolutionInMonthReportRepository.saveAndFlush(evolutionInMonthReport);

        int databaseSizeBeforeUpdate = evolutionInMonthReportRepository.findAll().size();

        // Update the evolutionInMonthReport
        EvolutionInMonthReport updatedEvolutionInMonthReport = evolutionInMonthReportRepository.findById(evolutionInMonthReport.getId()).get();
        // Disconnect from session so that the updates on updatedEvolutionInMonthReport are not directly saved in db
        em.detach(updatedEvolutionInMonthReport);
        updatedEvolutionInMonthReport
            .month(UPDATED_MONTH)
            .operation(UPDATED_OPERATION)
            .budget(UPDATED_BUDGET);
        EvolutionInMonthReportDTO evolutionInMonthReportDTO = evolutionInMonthReportMapper.toDto(updatedEvolutionInMonthReport);

        restEvolutionInMonthReportMockMvc.perform(put("/api/evolution-in-month-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evolutionInMonthReportDTO)))
            .andExpect(status().isOk());

        // Validate the EvolutionInMonthReport in the database
        List<EvolutionInMonthReport> evolutionInMonthReportList = evolutionInMonthReportRepository.findAll();
        assertThat(evolutionInMonthReportList).hasSize(databaseSizeBeforeUpdate);
        EvolutionInMonthReport testEvolutionInMonthReport = evolutionInMonthReportList.get(evolutionInMonthReportList.size() - 1);
        assertThat(testEvolutionInMonthReport.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testEvolutionInMonthReport.getOperation()).isEqualTo(UPDATED_OPERATION);
        assertThat(testEvolutionInMonthReport.getBudget()).isEqualTo(UPDATED_BUDGET);
    }

    @Test
    @Transactional
    public void updateNonExistingEvolutionInMonthReport() throws Exception {
        int databaseSizeBeforeUpdate = evolutionInMonthReportRepository.findAll().size();

        // Create the EvolutionInMonthReport
        EvolutionInMonthReportDTO evolutionInMonthReportDTO = evolutionInMonthReportMapper.toDto(evolutionInMonthReport);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEvolutionInMonthReportMockMvc.perform(put("/api/evolution-in-month-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evolutionInMonthReportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EvolutionInMonthReport in the database
        List<EvolutionInMonthReport> evolutionInMonthReportList = evolutionInMonthReportRepository.findAll();
        assertThat(evolutionInMonthReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEvolutionInMonthReport() throws Exception {
        // Initialize the database
        evolutionInMonthReportRepository.saveAndFlush(evolutionInMonthReport);

        int databaseSizeBeforeDelete = evolutionInMonthReportRepository.findAll().size();

        // Get the evolutionInMonthReport
        restEvolutionInMonthReportMockMvc.perform(delete("/api/evolution-in-month-reports/{id}", evolutionInMonthReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EvolutionInMonthReport> evolutionInMonthReportList = evolutionInMonthReportRepository.findAll();
        assertThat(evolutionInMonthReportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvolutionInMonthReport.class);
        EvolutionInMonthReport evolutionInMonthReport1 = new EvolutionInMonthReport();
        evolutionInMonthReport1.setId(1L);
        EvolutionInMonthReport evolutionInMonthReport2 = new EvolutionInMonthReport();
        evolutionInMonthReport2.setId(evolutionInMonthReport1.getId());
        assertThat(evolutionInMonthReport1).isEqualTo(evolutionInMonthReport2);
        evolutionInMonthReport2.setId(2L);
        assertThat(evolutionInMonthReport1).isNotEqualTo(evolutionInMonthReport2);
        evolutionInMonthReport1.setId(null);
        assertThat(evolutionInMonthReport1).isNotEqualTo(evolutionInMonthReport2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvolutionInMonthReportDTO.class);
        EvolutionInMonthReportDTO evolutionInMonthReportDTO1 = new EvolutionInMonthReportDTO();
        evolutionInMonthReportDTO1.setId(1L);
        EvolutionInMonthReportDTO evolutionInMonthReportDTO2 = new EvolutionInMonthReportDTO();
        assertThat(evolutionInMonthReportDTO1).isNotEqualTo(evolutionInMonthReportDTO2);
        evolutionInMonthReportDTO2.setId(evolutionInMonthReportDTO1.getId());
        assertThat(evolutionInMonthReportDTO1).isEqualTo(evolutionInMonthReportDTO2);
        evolutionInMonthReportDTO2.setId(2L);
        assertThat(evolutionInMonthReportDTO1).isNotEqualTo(evolutionInMonthReportDTO2);
        evolutionInMonthReportDTO1.setId(null);
        assertThat(evolutionInMonthReportDTO1).isNotEqualTo(evolutionInMonthReportDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(evolutionInMonthReportMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(evolutionInMonthReportMapper.fromId(null)).isNull();
    }
}
