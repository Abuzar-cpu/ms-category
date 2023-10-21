package academy.ingress.mscategory.service.concretes;

import static academy.ingress.mscategory.mapper.CategoryMapper.CATEGORY_MAPPER;
import static academy.ingress.mscategory.model.constants.ExceptionMessages.CATEGORY_NOT_FOUND;

import academy.ingress.mscategory.annotation.Loggable;
import academy.ingress.mscategory.dao.entity.Category;
import academy.ingress.mscategory.dao.entity.SubCategory;
import academy.ingress.mscategory.dao.repository.CategoryRepository;
import academy.ingress.mscategory.exception.NotFoundException;
import academy.ingress.mscategory.model.requests.CreateCategoryRequest;
import academy.ingress.mscategory.model.requests.PageRequestDto;
import academy.ingress.mscategory.model.requests.UpdateCategoryRequest;
import academy.ingress.mscategory.model.responses.CreateCategoryResponse;
import academy.ingress.mscategory.model.responses.GetAllCategoriesResponse;
import academy.ingress.mscategory.service.abstracts.CategoryService;
import academy.ingress.mscategory.specification.CategorySpecification;
import academy.ingress.mscategory.specification.CategorySpecificationDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable
public class CategoryServiceHandler implements CategoryService {

  private final CategoryRepository categoryRepository;

  public CreateCategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) {
    var category = CATEGORY_MAPPER.buildCategoryFromCreateRequest(createCategoryRequest);
    categoryRepository.save(category);
    return CATEGORY_MAPPER.mapEntityToCreateResponse(category);
  }

  public void updateCategory(Long id, UpdateCategoryRequest updateCategoryRequest) {
    var category = getCategoryIfExists(id);
    categoryRepository.save(CATEGORY_MAPPER.rebuildCategoryFromUpdateRequest(category, updateCategoryRequest));
  }

  public void deleteCategory(Long id) {
    categoryRepository.deleteById(id);
  }

  public List<GetAllCategoriesResponse> getAllCategoriesBySpecifications(
      CategorySpecificationDto categorySpecificationDto,
      PageRequestDto pageRequestDto) {

    var searchSpec = new CategorySpecification(categorySpecificationDto);
    var categories = categoryRepository.findAll(searchSpec,
        PageRequest.of(pageRequestDto.getPage() - 1, pageRequestDto.getSize()));
    categories.get().forEach(category -> category.setSubCategories(category.getSubCategories().stream().filter(
        SubCategory::getIsVisible).toList()));
    return categories.get().map(CATEGORY_MAPPER::mapEntityToGetResponse).toList();
  }

  public Category getCategoryIfExists(Long id) {
    return categoryRepository.findCategoryById(id).orElseThrow(() -> new NotFoundException(
        String.format(CATEGORY_NOT_FOUND.getMessage(), id)));
  }
}
