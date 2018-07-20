package org.mgoulene.web.rest;

import org.mgoulene.MyaccountApp;

import org.mgoulene.domain.MonthlyReport;
import org.mgoulene.domain.User;
import org.mgoulene.domain.Category;
import org.mgoulene.repository.MonthlyReportRepository;
import org.mgoulene.service.MonthlyReportService;
import org.mgoulene.service.dto.MonthlyReportDTO;
import org.mgoulene.service.mapper.MonthlyReportMapper;
import org.mgoulene.web.rest.errors.ExceptionTranslator;
import org.mgoulene.service.dto.MonthlyReportCriteria;
import org.mgoulene.service.MonthlyReportQueryService;

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
 * Test class for the MonthlyReportResource REST controller.
 *
 * @see MonthlyReportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyaccountApp.class)
public class MonthlyReportResourceIntTest {

    private static final LocalDate DEFAULT_MONTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MONTH = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_MONTH_VALUE = 1F;
    private static final Float UPDATED_MONTH_VALUE = 2F;

    private static final Float DEFAULT_MONTH_VALUE_AVG_3_MONTHS = 1F;
    private static final Float UPDATED_MONTH_VALUE_AVG_3_MONTHS = 2F;

    private static final Float DEFAULT_MONTH_VALUE_AVG_12_MONTHS = 1F;
    private static final Float UPDATED_MONTH_VALUE_AVG_12_MONTHS = 2F;

    @Autowired
    private MonthlyReportRepository monthlyReportRepository;


    @Autowired
    private MonthlyReportMapper monthlyReportMapper;
    

    @Autowired
    private MonthlyReportService monthlyReportService;

    @Autowired
    private MonthlyReportQueryService monthlyReportQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMonthlyReportMockMvc;

    private MonthlyReport monthlyReport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MonthlyReportResource monthlyReportResource = new MonthlyReportResource(monthlyReportService, monthlyReportQueryService);
        this.restMonthlyReportMockMvc = MockMvcBuilders.standaloneSetup(monthlyReportResource)
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
    public static MonthlyReport createEntity(EntityManager em) {
        MonthlyReport monthlyReport = new MonthlyReport()
            .month(DEFAULT_MONTH)
            .monthValue(DEFAULT_MONTH_VALUE)
            .monthValueAvg3Months(DEFAULT_MONTH_VALUE_AVG_3_MONTHS)
            .monthValueAvg12Months(DEFAULT_MONTH_VALUE_AVG_12_MONTHS);
        return monthlyReport;
    }

    @Before
    public void initTest() {
        monthlyReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonthlyReport() throws Exception {
        int databaseSizeBeforeCreate = monthlyReportRepository.findAll().size();

        // Create the MonthlyReport
        MonthlyReportDTO monthlyReportDTO = monthlyReportMapper.toDto(monthlyReport);
        restMonthlyReportMockMvc.perform(post("/api/monthly-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlyReportDTO)))
            .andExpect(status().isCreated());

        // Validate the MonthlyReport in the database
        List<MonthlyReport> monthlyReportList = monthlyReportRepository.findAll();
        assertThat(monthlyReportList).hasSize(databaseSizeBeforeCreate + 1);
        MonthlyReport testMonthlyReport = monthlyReportList.get(monthlyReportList.size() - 1);
        assertThat(testMonthlyReport.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testMonthlyReport.getMonthValue()).isEqualTo(DEFAULT_MONTH_VALUE);
        assertThat(testMonthlyReport.getMonthValueAvg3Months()).isEqualTo(DEFAULT_MONTH_VALUE_AVG_3_MONTHS);
        assertThat(testMonthlyReport.getMonthValueAvg12Months()).isEqualTo(DEFAULT_MONTH_VALUE_AVG_12_MONTHS);
    }

    @Test
    @Transactional
    public void createMonthlyReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monthlyReportRepository.findAll().size();

        // Create the MonthlyReport with an existing ID
        monthlyReport.setId(1L);
        MonthlyReportDTO monthlyReportDTO = monthlyReportMapper.toDto(monthlyReport);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonthlyReportMockMvc.perform(post("/api/monthly-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlyReportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MonthlyReport in the database
        List<MonthlyReport> monthlyReportList = monthlyReportRepository.findAll();
        assertThat(monthlyReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = monthlyReportRepository.findAll().size();
        // set the field null
        monthlyReport.setMonth(null);

        // Create the MonthlyReport, which fails.
        MonthlyReportDTO monthlyReportDTO = monthlyReportMapper.toDto(monthlyReport);

        restMonthlyReportMockMvc.perform(post("/api/monthly-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlyReportDTO)))
            .andExpect(status().isBadRequest());

        List<MonthlyReport> monthlyReportList = monthlyReportRepository.findAll();
        assertThat(monthlyReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonthValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = monthlyReportRepository.findAll().size();
        // set the field null
        monthlyReport.setMonthValue(null);

        // Create the MonthlyReport, which fails.
        MonthlyReportDTO monthlyReportDTO = monthlyReportMapper.toDto(monthlyReport);

        restMonthlyReportMockMvc.perform(post("/api/monthly-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlyReportDTO)))
            .andExpect(status().isBadRequest());

        List<MonthlyReport> monthlyReportList = monthlyReportRepository.findAll();
        assertThat(monthlyReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMonthlyReports() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        // Get all the monthlyReportList
        restMonthlyReportMockMvc.perform(get("/api/monthly-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlyReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].monthValue").value(hasItem(DEFAULT_MONTH_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].monthValueAvg3Months").value(hasItem(DEFAULT_MONTH_VALUE_AVG_3_MONTHS.doubleValue())))
            .andExpect(jsonPath("$.[*].monthValueAvg12Months").value(hasItem(DEFAULT_MONTH_VALUE_AVG_12_MONTHS.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getMonthlyReport() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        // Get the monthlyReport
        restMonthlyReportMockMvc.perform(get("/api/monthly-reports/{id}", monthlyReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(monthlyReport.getId().intValue()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.monthValue").value(DEFAULT_MONTH_VALUE.doubleValue()))
            .andExpect(jsonPath("$.monthValueAvg3Months").value(DEFAULT_MONTH_VALUE_AVG_3_MONTHS.doubleValue()))
            .andExpect(jsonPath("$.monthValueAvg12Months").value(DEFAULT_MONTH_VALUE_AVG_12_MONTHS.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllMonthlyReportsByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        // Get all the monthlyReportList where month equals to DEFAULT_MONTH
        defaultMonthlyReportShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the monthlyReportList where month equals to UPDATED_MONTH
        defaultMonthlyReportShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllMonthlyReportsByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        // Get all the monthlyReportList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultMonthlyReportShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the monthlyReportList where month equals to UPDATED_MONTH
        defaultMonthlyReportShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllMonthlyReportsByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        // Get all the monthlyReportList where month is not null
        defaultMonthlyReportShouldBeFound("month.specified=true");

        // Get all the monthlyReportList where month is null
        defaultMonthlyReportShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlyReportsByMonthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        // Get all the monthlyReportList where month greater than or equals to DEFAULT_MONTH
        defaultMonthlyReportShouldBeFound("month.greaterOrEqualThan=" + DEFAULT_MONTH);

        // Get all the monthlyReportList where month greater than or equals to UPDATED_MONTH
        defaultMonthlyReportShouldNotBeFound("month.greaterOrEqualThan=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllMonthlyReportsByMonthIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        // Get all the monthlyReportList where month less than or equals to DEFAULT_MONTH
        defaultMonthlyReportShouldNotBeFound("month.lessThan=" + DEFAULT_MONTH);

        // Get all the monthlyReportList where month less than or equals to UPDATED_MONTH
        defaultMonthlyReportShouldBeFound("month.lessThan=" + UPDATED_MONTH);
    }


    @Test
    @Transactional
    public void getAllMonthlyReportsByMonthValueIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        // Get all the monthlyReportList where monthValue equals to DEFAULT_MONTH_VALUE
        defaultMonthlyReportShouldBeFound("monthValue.equals=" + DEFAULT_MONTH_VALUE);

        // Get all the monthlyReportList where monthValue equals to UPDATED_MONTH_VALUE
        defaultMonthlyReportShouldNotBeFound("monthValue.equals=" + UPDATED_MONTH_VALUE);
    }

    @Test
    @Transactional
    public void getAllMonthlyReportsByMonthValueIsInShouldWork() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        // Get all the monthlyReportList where monthValue in DEFAULT_MONTH_VALUE or UPDATED_MONTH_VALUE
        defaultMonthlyReportShouldBeFound("monthValue.in=" + DEFAULT_MONTH_VALUE + "," + UPDATED_MONTH_VALUE);

        // Get all the monthlyReportList where monthValue equals to UPDATED_MONTH_VALUE
        defaultMonthlyReportShouldNotBeFound("monthValue.in=" + UPDATED_MONTH_VALUE);
    }

    @Test
    @Transactional
    public void getAllMonthlyReportsByMonthValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        // Get all the monthlyReportList where monthValue is not null
        defaultMonthlyReportShouldBeFound("monthValue.specified=true");

        // Get all the monthlyReportList where monthValue is null
        defaultMonthlyReportShouldNotBeFound("monthValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlyReportsByMonthValueAvg3MonthsIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        // Get all the monthlyReportList where monthValueAvg3Months equals to DEFAULT_MONTH_VALUE_AVG_3_MONTHS
        defaultMonthlyReportShouldBeFound("monthValueAvg3Months.equals=" + DEFAULT_MONTH_VALUE_AVG_3_MONTHS);

        // Get all the monthlyReportList where monthValueAvg3Months equals to UPDATED_MONTH_VALUE_AVG_3_MONTHS
        defaultMonthlyReportShouldNotBeFound("monthValueAvg3Months.equals=" + UPDATED_MONTH_VALUE_AVG_3_MONTHS);
    }

    @Test
    @Transactional
    public void getAllMonthlyReportsByMonthValueAvg3MonthsIsInShouldWork() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        // Get all the monthlyReportList where monthValueAvg3Months in DEFAULT_MONTH_VALUE_AVG_3_MONTHS or UPDATED_MONTH_VALUE_AVG_3_MONTHS
        defaultMonthlyReportShouldBeFound("monthValueAvg3Months.in=" + DEFAULT_MONTH_VALUE_AVG_3_MONTHS + "," + UPDATED_MONTH_VALUE_AVG_3_MONTHS);

        // Get all the monthlyReportList where monthValueAvg3Months equals to UPDATED_MONTH_VALUE_AVG_3_MONTHS
        defaultMonthlyReportShouldNotBeFound("monthValueAvg3Months.in=" + UPDATED_MONTH_VALUE_AVG_3_MONTHS);
    }

    @Test
    @Transactional
    public void getAllMonthlyReportsByMonthValueAvg3MonthsIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        // Get all the monthlyReportList where monthValueAvg3Months is not null
        defaultMonthlyReportShouldBeFound("monthValueAvg3Months.specified=true");

        // Get all the monthlyReportList where monthValueAvg3Months is null
        defaultMonthlyReportShouldNotBeFound("monthValueAvg3Months.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlyReportsByMonthValueAvg12MonthsIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        // Get all the monthlyReportList where monthValueAvg12Months equals to DEFAULT_MONTH_VALUE_AVG_12_MONTHS
        defaultMonthlyReportShouldBeFound("monthValueAvg12Months.equals=" + DEFAULT_MONTH_VALUE_AVG_12_MONTHS);

        // Get all the monthlyReportList where monthValueAvg12Months equals to UPDATED_MONTH_VALUE_AVG_12_MONTHS
        defaultMonthlyReportShouldNotBeFound("monthValueAvg12Months.equals=" + UPDATED_MONTH_VALUE_AVG_12_MONTHS);
    }

    @Test
    @Transactional
    public void getAllMonthlyReportsByMonthValueAvg12MonthsIsInShouldWork() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        // Get all the monthlyReportList where monthValueAvg12Months in DEFAULT_MONTH_VALUE_AVG_12_MONTHS or UPDATED_MONTH_VALUE_AVG_12_MONTHS
        defaultMonthlyReportShouldBeFound("monthValueAvg12Months.in=" + DEFAULT_MONTH_VALUE_AVG_12_MONTHS + "," + UPDATED_MONTH_VALUE_AVG_12_MONTHS);

        // Get all the monthlyReportList where monthValueAvg12Months equals to UPDATED_MONTH_VALUE_AVG_12_MONTHS
        defaultMonthlyReportShouldNotBeFound("monthValueAvg12Months.in=" + UPDATED_MONTH_VALUE_AVG_12_MONTHS);
    }

    @Test
    @Transactional
    public void getAllMonthlyReportsByMonthValueAvg12MonthsIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        // Get all the monthlyReportList where monthValueAvg12Months is not null
        defaultMonthlyReportShouldBeFound("monthValueAvg12Months.specified=true");

        // Get all the monthlyReportList where monthValueAvg12Months is null
        defaultMonthlyReportShouldNotBeFound("monthValueAvg12Months.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlyReportsByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        User account = UserResourceIntTest.createEntity(em);
        em.persist(account);
        em.flush();
        monthlyReport.setAccount(account);
        monthlyReportRepository.saveAndFlush(monthlyReport);
        Long accountId = account.getId();

        // Get all the monthlyReportList where account equals to accountId
        defaultMonthlyReportShouldBeFound("accountId.equals=" + accountId);

        // Get all the monthlyReportList where account equals to accountId + 1
        defaultMonthlyReportShouldNotBeFound("accountId.equals=" + (accountId + 1));
    }


    @Test
    @Transactional
    public void getAllMonthlyReportsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        Category category = CategoryResourceIntTest.createEntity(em);
        em.persist(category);
        em.flush();
        monthlyReport.setCategory(category);
        monthlyReportRepository.saveAndFlush(monthlyReport);
        Long categoryId = category.getId();

        // Get all the monthlyReportList where category equals to categoryId
        defaultMonthlyReportShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the monthlyReportList where category equals to categoryId + 1
        defaultMonthlyReportShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMonthlyReportShouldBeFound(String filter) throws Exception {
        restMonthlyReportMockMvc.perform(get("/api/monthly-reports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlyReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].monthValue").value(hasItem(DEFAULT_MONTH_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].monthValueAvg3Months").value(hasItem(DEFAULT_MONTH_VALUE_AVG_3_MONTHS.doubleValue())))
            .andExpect(jsonPath("$.[*].monthValueAvg12Months").value(hasItem(DEFAULT_MONTH_VALUE_AVG_12_MONTHS.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMonthlyReportShouldNotBeFound(String filter) throws Exception {
        restMonthlyReportMockMvc.perform(get("/api/monthly-reports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingMonthlyReport() throws Exception {
        // Get the monthlyReport
        restMonthlyReportMockMvc.perform(get("/api/monthly-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonthlyReport() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        int databaseSizeBeforeUpdate = monthlyReportRepository.findAll().size();

        // Update the monthlyReport
        MonthlyReport updatedMonthlyReport = monthlyReportRepository.findById(monthlyReport.getId()).get();
        // Disconnect from session so that the updates on updatedMonthlyReport are not directly saved in db
        em.detach(updatedMonthlyReport);
        updatedMonthlyReport
            .month(UPDATED_MONTH)
            .monthValue(UPDATED_MONTH_VALUE)
            .monthValueAvg3Months(UPDATED_MONTH_VALUE_AVG_3_MONTHS)
            .monthValueAvg12Months(UPDATED_MONTH_VALUE_AVG_12_MONTHS);
        MonthlyReportDTO monthlyReportDTO = monthlyReportMapper.toDto(updatedMonthlyReport);

        restMonthlyReportMockMvc.perform(put("/api/monthly-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlyReportDTO)))
            .andExpect(status().isOk());

        // Validate the MonthlyReport in the database
        List<MonthlyReport> monthlyReportList = monthlyReportRepository.findAll();
        assertThat(monthlyReportList).hasSize(databaseSizeBeforeUpdate);
        MonthlyReport testMonthlyReport = monthlyReportList.get(monthlyReportList.size() - 1);
        assertThat(testMonthlyReport.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testMonthlyReport.getMonthValue()).isEqualTo(UPDATED_MONTH_VALUE);
        assertThat(testMonthlyReport.getMonthValueAvg3Months()).isEqualTo(UPDATED_MONTH_VALUE_AVG_3_MONTHS);
        assertThat(testMonthlyReport.getMonthValueAvg12Months()).isEqualTo(UPDATED_MONTH_VALUE_AVG_12_MONTHS);
    }

    @Test
    @Transactional
    public void updateNonExistingMonthlyReport() throws Exception {
        int databaseSizeBeforeUpdate = monthlyReportRepository.findAll().size();

        // Create the MonthlyReport
        MonthlyReportDTO monthlyReportDTO = monthlyReportMapper.toDto(monthlyReport);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMonthlyReportMockMvc.perform(put("/api/monthly-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlyReportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MonthlyReport in the database
        List<MonthlyReport> monthlyReportList = monthlyReportRepository.findAll();
        assertThat(monthlyReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMonthlyReport() throws Exception {
        // Initialize the database
        monthlyReportRepository.saveAndFlush(monthlyReport);

        int databaseSizeBeforeDelete = monthlyReportRepository.findAll().size();

        // Get the monthlyReport
        restMonthlyReportMockMvc.perform(delete("/api/monthly-reports/{id}", monthlyReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MonthlyReport> monthlyReportList = monthlyReportRepository.findAll();
        assertThat(monthlyReportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonthlyReport.class);
        MonthlyReport monthlyReport1 = new MonthlyReport();
        monthlyReport1.setId(1L);
        MonthlyReport monthlyReport2 = new MonthlyReport();
        monthlyReport2.setId(monthlyReport1.getId());
        assertThat(monthlyReport1).isEqualTo(monthlyReport2);
        monthlyReport2.setId(2L);
        assertThat(monthlyReport1).isNotEqualTo(monthlyReport2);
        monthlyReport1.setId(null);
        assertThat(monthlyReport1).isNotEqualTo(monthlyReport2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonthlyReportDTO.class);
        MonthlyReportDTO monthlyReportDTO1 = new MonthlyReportDTO();
        monthlyReportDTO1.setId(1L);
        MonthlyReportDTO monthlyReportDTO2 = new MonthlyReportDTO();
        assertThat(monthlyReportDTO1).isNotEqualTo(monthlyReportDTO2);
        monthlyReportDTO2.setId(monthlyReportDTO1.getId());
        assertThat(monthlyReportDTO1).isEqualTo(monthlyReportDTO2);
        monthlyReportDTO2.setId(2L);
        assertThat(monthlyReportDTO1).isNotEqualTo(monthlyReportDTO2);
        monthlyReportDTO1.setId(null);
        assertThat(monthlyReportDTO1).isNotEqualTo(monthlyReportDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(monthlyReportMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(monthlyReportMapper.fromId(null)).isNull();
    }
}
