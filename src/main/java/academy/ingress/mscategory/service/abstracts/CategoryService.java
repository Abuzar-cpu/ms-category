package academy.ingress.mscategory.service.abstracts;

import academy.ingress.mscategory.dao.entity.Category;
import academy.ingress.mscategory.model.requests.CreateCategoryRequest;
import academy.ingress.mscategory.model.requests.PageRequestDto;
import academy.ingress.mscategory.model.requests.UpdateCategoryRequest;
import academy.ingress.mscategory.model.responses.CreateCategoryResponse;
import academy.ingress.mscategory.model.responses.GetAllCategoriesResponse;
import academy.ingress.mscategory.specification.CategorySpecificationDto;
import java.util.List;

public interface CategoryService {

  CreateCategoryResponse createCategory(CreateCategoryRequest request);


  Category getCategoryIfExists(Long id);


  List<GetAllCategoriesResponse> getAllCategoriesBySpecifications(CategorySpecificationDto specs,
      PageRequestDto page);

  void updateCategory(Long id, UpdateCategoryRequest request);

  void deleteCategory(Long id);
}