package academy.ingress.mscategory.service.abstracts;

import academy.ingress.mscategory.model.requests.CreateSubCategoryRequest;
import academy.ingress.mscategory.model.requests.PageRequestDto;
import academy.ingress.mscategory.model.responses.CreateSubCategoryResponse;
import academy.ingress.mscategory.model.responses.GetSubCategoryResponse;
import academy.ingress.mscategory.model.responses.UpdateSubCategoryRequest;
import academy.ingress.mscategory.specification.SubCategorySpecificationDto;
import java.util.List;

public interface SubCategoryService {

  CreateSubCategoryResponse createSubCategory(CreateSubCategoryRequest request);

  List<GetSubCategoryResponse> getAllSubCategoriesBySpecifications(
      SubCategorySpecificationDto specs,
      PageRequestDto page);

  void updateSubCategory(Long id, UpdateSubCategoryRequest request);

  void deleteSubCategory(Long id);
}
