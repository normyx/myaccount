package org.mgoulene.service.dto;

import java.io.Serializable;
import org.mgoulene.domain.enumeration.CategoryType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Category entity. This class is used in CategoryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /categories?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategoryCriteria implements Serializable {
    /**
     * Class for filtering CategoryType
     */
    public static class CategoryTypeFilter extends Filter<CategoryType> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter categoryName;

    private CategoryTypeFilter categoryType;

    private LongFilter subCategoryId;

    public CategoryCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(StringFilter categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryTypeFilter getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryTypeFilter categoryType) {
        this.categoryType = categoryType;
    }

    public LongFilter getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(LongFilter subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    @Override
    public String toString() {
        return "CategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (categoryName != null ? "categoryName=" + categoryName + ", " : "") +
                (categoryType != null ? "categoryType=" + categoryType + ", " : "") +
                (subCategoryId != null ? "subCategoryId=" + subCategoryId + ", " : "") +
            "}";
    }

}
