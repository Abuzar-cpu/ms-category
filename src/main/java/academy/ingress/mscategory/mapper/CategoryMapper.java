package academy.ingress.mscategory.mapper;

import static academy.ingress.mscategory.mapper.SubCategoryMapper.SUB_CATEGORY_MAPPER;

import academy.ingress.mscategory.dao.entity.Category;
import academy.ingress.mscategory.model.requests.CreateCategoryRequest;
import academy.ingress.mscategory.model.requests.UpdateCategoryRequest;
import academy.ingress.mscategory.model.responses.CreateCategoryResponse;
import academy.ingress.mscategory.model.responses.GetAllCategoriesResponse;

public enum CategoryMapper {
  CATEGORY_MAPPER;

  public CreateCategoryResponse mapEntityToCreateResponse(Category category) {
    return CreateCategoryResponse.builder()
        .name(category.getName())
        .isFavorite(category.getIsFavorite())
        .isVisible(category.getIsVisible())
        .build();
  }

  public Category rebuildCategoryFromUpdateRequest(Category category,
      UpdateCategoryRequest request) {
    category.setName(request.getName() == null ? category.getName() : request.getName());
    category.setIsVisible(
        request.getIsVisible() == null ? category.getIsVisible() : request.getIsVisible());
    category.setIsFavorite(request.getIsFavorite() == null ? category.getIsFavorite() :
        request.getIsFavorite());
    category.setId(category.getId());

    return category;
  }

  public GetAllCategoriesResponse mapEntityToGetResponse(Category category) {
    return GetAllCategoriesResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .isFavorite(category.getIsFavorite())
        .subCategories(
            category.getSubCategories().stream().map(SUB_CATEGORY_MAPPER::mapEntityToGetResponse)
                .toList())
        .build();
  }

  public Category buildCategoryFromCreateRequest(CreateCategoryRequest request) {
    return Category.builder()
        .name(request.getName())
        .isFavorite(false)
        .isVisible(true)
        .build();
  }
}
