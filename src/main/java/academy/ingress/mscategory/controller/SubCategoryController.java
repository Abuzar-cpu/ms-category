package academy.ingress.mscategory.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import academy.ingress.mscategory.model.requests.CreateSubCategoryRequest;
import academy.ingress.mscategory.model.requests.PageRequestDto;
import academy.ingress.mscategory.model.responses.CreateSubCategoryResponse;
import academy.ingress.mscategory.model.responses.GetSubCategoryResponse;
import academy.ingress.mscategory.model.responses.UpdateSubCategoryRequest;
import academy.ingress.mscategory.service.abstracts.SubCategoryService;
import academy.ingress.mscategory.specification.SubCategorySpecificationDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v1/subCategories")
@RequiredArgsConstructor
public class SubCategoryController {

  private final SubCategoryService subCategoryService;

  @ResponseStatus(CREATED)
  @PostMapping
  public CreateSubCategoryResponse createSubCategory(
      @Valid @RequestBody CreateSubCategoryRequest category) {
    return subCategoryService.createSubCategory(category);
  }

  @GetMapping
  public List<GetSubCategoryResponse> getAllSubCategoriesBySpecifications(
      SubCategorySpecificationDto specs,
      @Valid PageRequestDto page) {
    return subCategoryService.getAllSubCategoriesBySpecifications(specs, page);
  }

  @PutMapping("/{id}")
  @ResponseStatus(NO_CONTENT)
  public void updateSubCategory(@PathVariable Long id,
      @RequestBody @Valid UpdateSubCategoryRequest request) {
    subCategoryService.updateSubCategory(id, request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(NO_CONTENT)
  public void deleteSubCategory(@PathVariable Long id) {
    subCategoryService.deleteSubCategory(id);
  }
}
