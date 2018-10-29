package org.mgoulene.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mgoulene.service.SubCategoryService;
import org.mgoulene.web.rest.errors.BadRequestAlertException;
import org.mgoulene.web.rest.util.HeaderUtil;
import org.mgoulene.service.dto.SubCategoryDTO;
import org.mgoulene.service.dto.SubCategoryCriteria;
import org.mgoulene.service.SubCategoryQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SubCategory.
 */
@RestController
@RequestMapping("/api")
public class SubCategoryResource {

    private final Logger log = LoggerFactory.getLogger(SubCategoryResource.class);

    private static final String ENTITY_NAME = "subCategory";

    private SubCategoryService subCategoryService;

    private SubCategoryQueryService subCategoryQueryService;

    public SubCategoryResource(SubCategoryService subCategoryService, SubCategoryQueryService subCategoryQueryService) {
        this.subCategoryService = subCategoryService;
        this.subCategoryQueryService = subCategoryQueryService;
    }

    /**
     * POST  /sub-categories : Create a new subCategory.
     *
     * @param subCategoryDTO the subCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subCategoryDTO, or with status 400 (Bad Request) if the subCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sub-categories")
    @Timed
    public ResponseEntity<SubCategoryDTO> createSubCategory(@Valid @RequestBody SubCategoryDTO subCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save SubCategory : {}", subCategoryDTO);
        if (subCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new subCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubCategoryDTO result = subCategoryService.save(subCategoryDTO);
        return ResponseEntity.created(new URI("/api/sub-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sub-categories : Updates an existing subCategory.
     *
     * @param subCategoryDTO the subCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subCategoryDTO,
     * or with status 400 (Bad Request) if the subCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the subCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sub-categories")
    @Timed
    public ResponseEntity<SubCategoryDTO> updateSubCategory(@Valid @RequestBody SubCategoryDTO subCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update SubCategory : {}", subCategoryDTO);
        if (subCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubCategoryDTO result = subCategoryService.save(subCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sub-categories : get all the subCategories.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of subCategories in body
     */
    @GetMapping("/sub-categories")
    @Timed
    public ResponseEntity<List<SubCategoryDTO>> getAllSubCategories(SubCategoryCriteria criteria) {
        log.debug("REST request to get SubCategories by criteria: {}", criteria);
        List<SubCategoryDTO> entityList = subCategoryQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /sub-categories/count : count all the subCategories.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/sub-categories/count")
    @Timed
    public ResponseEntity<Long> countSubCategories(SubCategoryCriteria criteria) {
        log.debug("REST request to count SubCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(subCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /sub-categories/:id : get the "id" subCategory.
     *
     * @param id the id of the subCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sub-categories/{id}")
    @Timed
    public ResponseEntity<SubCategoryDTO> getSubCategory(@PathVariable Long id) {
        log.debug("REST request to get SubCategory : {}", id);
        Optional<SubCategoryDTO> subCategoryDTO = subCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subCategoryDTO);
    }

    /**
     * DELETE  /sub-categories/:id : delete the "id" subCategory.
     *
     * @param id the id of the subCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sub-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Long id) {
        log.debug("REST request to delete SubCategory : {}", id);
        subCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


}
