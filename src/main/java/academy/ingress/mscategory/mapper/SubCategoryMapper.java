package academy.ingress.mscategory.mapper;

import academy.ingress.mscategory.dao.entity.SubCategory;
import academy.ingress.mscategory.model.requests.CreateSubCategoryRequest;
import academy.ingress.mscategory.model.responses.CreateSubCategoryResponse;
import academy.ingress.mscategory.model.responses.GetSubCategoryResponse;
import academy.ingress.mscategory.model.responses.UpdateSubCategoryRequest;

public enum SubCategoryMapper {
  SUB_CATEGORY_MAPPER;

  public SubCategory buildSubCategoryFromCreateRequest(CreateSubCategoryRequest request) {
    return SubCategory.builder()
        .name(request.getName())
        .isVisible(true)
        .isFavorite(false)
        .build();
  }

  public CreateSubCategoryResponse mapEntityToCreateResponse(SubCategory saved) {
    return CreateSubCategoryResponse.builder()
        .isFavorite(saved.getIsFavorite())
        .isVisible(saved.getIsVisible())
        .name(saved.getName())
        .build();
  }

  public GetSubCategoryResponse mapEntityToGetResponse(SubCategory category) {
    return GetSubCategoryResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .isFavorite(category.getIsFavorite())
        .build();
  }

  public SubCategory rebuildSubCategoryFromUpdateRequest(SubCategory subCategory,
      UpdateSubCategoryRequest request) {
    subCategory.setName(request.getName() == null ? subCategory.getName() : request.getName());
    subCategory.setIsVisible(
        request.getIsVisible() == null ? subCategory.getIsVisible() : request.getIsVisible());
    subCategory.setIsFavorite(
        request.getIsFavorite() == null ? subCategory.getIsFavorite() : request.getIsFavorite());
    return subCategory;
  }
}
