package org.mgoulene.service.mapper;

import org.mgoulene.domain.*;
import org.mgoulene.service.dto.SubCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SubCategory and its DTO SubCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface SubCategoryMapper extends EntityMapper<SubCategoryDTO, SubCategory> {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.categoryName", target = "categoryCategoryName")
    SubCategoryDTO toDto(SubCategory subCategory);

    @Mapping(source = "categoryId", target = "category")
    SubCategory toEntity(SubCategoryDTO subCategoryDTO);

    default SubCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        SubCategory subCategory = new SubCategory();
        subCategory.setId(id);
        return subCategory;
    }
}
