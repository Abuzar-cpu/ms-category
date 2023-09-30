package academy.ingress.mscategory.service.concretes;

import static academy.ingress.mscategory.mapper.SubCategoryMapper.SUB_CATEGORY_MAPPER;
import static academy.ingress.mscategory.model.constants.ExceptionMessages.CATEGORY_NOT_FOUND;

import academy.ingress.mscategory.annotation.Loggable;
import academy.ingress.mscategory.dao.entity.SubCategory;
import academy.ingress.mscategory.dao.repository.SubCategoryRepository;
import academy.ingress.mscategory.exception.NotFoundException;
import academy.ingress.mscategory.model.requests.CreateSubCategoryRequest;
import academy.ingress.mscategory.model.requests.PageRequestDto;
import academy.ingress.mscategory.model.responses.CreateSubCategoryResponse;
import academy.ingress.mscategory.model.responses.GetSubCategoryResponse;
import academy.ingress.mscategory.model.responses.UpdateSubCategoryRequest;
import academy.ingress.mscategory.service.abstracts.CategoryService;
import academy.ingress.mscategory.service.abstracts.SubCategoryService;
import academy.ingress.mscategory.specification.SubCategorySpecification;
import academy.ingress.mscategory.specification.SubCategorySpecificationDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Loggable
public class SubCategoryServiceHandler implements SubCategoryService {

  private final SubCategoryRepository subCategoryRepository;
  private final CategoryService categoryService;

  @Transactional
  public CreateSubCategoryResponse createSubCategory(CreateSubCategoryRequest request) {
    var subCategory = SUB_CATEGORY_MAPPER.buildSubCategoryFromCreateRequest(request);
    var parent = categoryService.getCategoryIfExists(request.getParentId());
    subCategory.setParent(parent);
    var saved = subCategoryRepository.save(subCategory);

    return SUB_CATEGORY_MAPPER.mapEntityToCreateResponse(saved);
  }

  public List<GetSubCategoryResponse> getAllSubCategoriesBySpecifications(
      SubCategorySpecificationDto specs,
      PageRequestDto page) {
    var searchSpec = new SubCategorySpecification(specs);
    var categories = subCategoryRepository.findAll(searchSpec,
        PageRequest.of(page.getPage() - 1, page.getSize()));
    if (categories.getSize() == 0) {
      throw new NotFoundException(CATEGORY_NOT_FOUND.getMessage());
    }
    return categories.stream().map(
        SUB_CATEGORY_MAPPER::mapEntityToGetResponse).toList();
  }

  //  @Transactional
  public void updateSubCategory(Long id, UpdateSubCategoryRequest request) {
    var subCategory = getSubCategoryIfExists(id);
    subCategoryRepository.save(
        SUB_CATEGORY_MAPPER.rebuildSubCategoryFromUpdateRequest(subCategory, request));
  }

  public void deleteSubCategory(Long id) {
    subCategoryRepository.deleteById(id);
  }

  private SubCategory getSubCategoryIfExists(Long id) {
    return subCategoryRepository.findById(id).orElseThrow(() -> new NotFoundException(
        String.format(CATEGORY_NOT_FOUND.getMessage(), id)));
  }
}
