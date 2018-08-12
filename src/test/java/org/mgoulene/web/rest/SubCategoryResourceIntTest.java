package org.mgoulene.web.rest;

import org.mgoulene.MyaccountApp;

import org.mgoulene.domain.SubCategory;
import org.mgoulene.domain.Category;
import org.mgoulene.repository.SubCategoryRepository;
import org.mgoulene.service.SubCategoryService;
import org.mgoulene.service.dto.SubCategoryDTO;
import org.mgoulene.service.mapper.SubCategoryMapper;
import org.mgoulene.web.rest.errors.ExceptionTranslator;
import org.mgoulene.service.dto.SubCategoryCriteria;
import org.mgoulene.service.SubCategoryQueryService;

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
import java.util.List;


import static org.mgoulene.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SubCategoryResource REST controller.
 *
 * @see SubCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyaccountApp.class)
public class SubCategoryResourceIntTest {

    private static final String DEFAULT_SUB_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUB_CATEGORY_NAME = "BBBBBBBBBB";

    @Autowired
    private SubCategoryRepository subCategoryRepository;


    @Autowired
    private SubCategoryMapper subCategoryMapper;
    

    @Autowired
    private SubCategoryService subCategoryService;

 
    @Autowired
    private SubCategoryQueryService subCategoryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSubCategoryMockMvc;

    private SubCategory subCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubCategoryResource subCategoryResource = new SubCategoryResource(subCategoryService, subCategoryQueryService);
        this.restSubCategoryMockMvc = MockMvcBuilders.standaloneSetup(subCategoryResource)
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
    public static SubCategory createEntity(EntityManager em) {
        SubCategory subCategory = new SubCategory()
            .subCategoryName(DEFAULT_SUB_CATEGORY_NAME);
        return subCategory;
    }

    @Before
    public void initTest() {
        subCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubCategory() throws Exception {
        int databaseSizeBeforeCreate = subCategoryRepository.findAll().size();

        // Create the SubCategory
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);
        restSubCategoryMockMvc.perform(post("/api/sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        SubCategory testSubCategory = subCategoryList.get(subCategoryList.size() - 1);
        assertThat(testSubCategory.getSubCategoryName()).isEqualTo(DEFAULT_SUB_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void createSubCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subCategoryRepository.findAll().size();

        // Create the SubCategory with an existing ID
        subCategory.setId(1L);
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubCategoryMockMvc.perform(post("/api/sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSubCategoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subCategoryRepository.findAll().size();
        // set the field null
        subCategory.setSubCategoryName(null);

        // Create the SubCategory, which fails.
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        restSubCategoryMockMvc.perform(post("/api/sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubCategories() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        // Get all the subCategoryList
        restSubCategoryMockMvc.perform(get("/api/sub-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].subCategoryName").value(hasItem(DEFAULT_SUB_CATEGORY_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getSubCategory() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        // Get the subCategory
        restSubCategoryMockMvc.perform(get("/api/sub-categories/{id}", subCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subCategory.getId().intValue()))
            .andExpect(jsonPath("$.subCategoryName").value(DEFAULT_SUB_CATEGORY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllSubCategoriesBySubCategoryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        // Get all the subCategoryList where subCategoryName equals to DEFAULT_SUB_CATEGORY_NAME
        defaultSubCategoryShouldBeFound("subCategoryName.equals=" + DEFAULT_SUB_CATEGORY_NAME);

        // Get all the subCategoryList where subCategoryName equals to UPDATED_SUB_CATEGORY_NAME
        defaultSubCategoryShouldNotBeFound("subCategoryName.equals=" + UPDATED_SUB_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllSubCategoriesBySubCategoryNameIsInShouldWork() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        // Get all the subCategoryList where subCategoryName in DEFAULT_SUB_CATEGORY_NAME or UPDATED_SUB_CATEGORY_NAME
        defaultSubCategoryShouldBeFound("subCategoryName.in=" + DEFAULT_SUB_CATEGORY_NAME + "," + UPDATED_SUB_CATEGORY_NAME);

        // Get all the subCategoryList where subCategoryName equals to UPDATED_SUB_CATEGORY_NAME
        defaultSubCategoryShouldNotBeFound("subCategoryName.in=" + UPDATED_SUB_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllSubCategoriesBySubCategoryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        // Get all the subCategoryList where subCategoryName is not null
        defaultSubCategoryShouldBeFound("subCategoryName.specified=true");

        // Get all the subCategoryList where subCategoryName is null
        defaultSubCategoryShouldNotBeFound("subCategoryName.specified=false");
    }

    @Test
    @Transactional
    public void getAllSubCategoriesByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        Category category = CategoryResourceIntTest.createEntity(em);
        em.persist(category);
        em.flush();
        subCategory.setCategory(category);
        subCategoryRepository.saveAndFlush(subCategory);
        Long categoryId = category.getId();

        // Get all the subCategoryList where category equals to categoryId
        defaultSubCategoryShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the subCategoryList where category equals to categoryId + 1
        defaultSubCategoryShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSubCategoryShouldBeFound(String filter) throws Exception {
        restSubCategoryMockMvc.perform(get("/api/sub-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].subCategoryName").value(hasItem(DEFAULT_SUB_CATEGORY_NAME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSubCategoryShouldNotBeFound(String filter) throws Exception {
        restSubCategoryMockMvc.perform(get("/api/sub-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingSubCategory() throws Exception {
        // Get the subCategory
        restSubCategoryMockMvc.perform(get("/api/sub-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubCategory() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();

        // Update the subCategory
        SubCategory updatedSubCategory = subCategoryRepository.findById(subCategory.getId()).get();
        // Disconnect from session so that the updates on updatedSubCategory are not directly saved in db
        em.detach(updatedSubCategory);
        updatedSubCategory
            .subCategoryName(UPDATED_SUB_CATEGORY_NAME);
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(updatedSubCategory);

        restSubCategoryMockMvc.perform(put("/api/sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
        SubCategory testSubCategory = subCategoryList.get(subCategoryList.size() - 1);
        assertThat(testSubCategory.getSubCategoryName()).isEqualTo(UPDATED_SUB_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();

        // Create the SubCategory
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSubCategoryMockMvc.perform(put("/api/sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubCategory() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        int databaseSizeBeforeDelete = subCategoryRepository.findAll().size();

        // Get the subCategory
        restSubCategoryMockMvc.perform(delete("/api/sub-categories/{id}", subCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubCategory.class);
        SubCategory subCategory1 = new SubCategory();
        subCategory1.setId(1L);
        SubCategory subCategory2 = new SubCategory();
        subCategory2.setId(subCategory1.getId());
        assertThat(subCategory1).isEqualTo(subCategory2);
        subCategory2.setId(2L);
        assertThat(subCategory1).isNotEqualTo(subCategory2);
        subCategory1.setId(null);
        assertThat(subCategory1).isNotEqualTo(subCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubCategoryDTO.class);
        SubCategoryDTO subCategoryDTO1 = new SubCategoryDTO();
        subCategoryDTO1.setId(1L);
        SubCategoryDTO subCategoryDTO2 = new SubCategoryDTO();
        assertThat(subCategoryDTO1).isNotEqualTo(subCategoryDTO2);
        subCategoryDTO2.setId(subCategoryDTO1.getId());
        assertThat(subCategoryDTO1).isEqualTo(subCategoryDTO2);
        subCategoryDTO2.setId(2L);
        assertThat(subCategoryDTO1).isNotEqualTo(subCategoryDTO2);
        subCategoryDTO1.setId(null);
        assertThat(subCategoryDTO1).isNotEqualTo(subCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(subCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(subCategoryMapper.fromId(null)).isNull();
    }
}
