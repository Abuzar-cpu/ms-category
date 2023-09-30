package academy.ingress.mscategory.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import academy.ingress.mscategory.model.requests.CreateCategoryRequest;
import academy.ingress.mscategory.model.requests.PageRequestDto;
import academy.ingress.mscategory.model.requests.UpdateCategoryRequest;
import academy.ingress.mscategory.model.responses.CreateCategoryResponse;
import academy.ingress.mscategory.model.responses.GetAllCategoriesResponse;
import academy.ingress.mscategory.service.abstracts.CategoryService;
import academy.ingress.mscategory.specification.CategorySpecificationDto;
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
@RequestMapping("/internal/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @ResponseStatus(CREATED)
  @PostMapping
  public CreateCategoryResponse createCategory(@RequestBody @Valid CreateCategoryRequest category) {
    return categoryService.createCategory(category);
  }

  @GetMapping
  public List<GetAllCategoriesResponse> getAllCategoriesBySpecifications(
      CategorySpecificationDto specificationDto, PageRequestDto page) {
    return categoryService.getAllCategoriesBySpecifications(specificationDto, page);
  }

  @PutMapping("/{id}")
  @ResponseStatus(NO_CONTENT)
  public void updateCategory(@PathVariable Long id,
      @RequestBody @Valid UpdateCategoryRequest updateCategoryRequest) {
    categoryService.updateCategory(id, updateCategoryRequest);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(NO_CONTENT)
  public void deleteCategory(@PathVariable Long id) {
    categoryService.deleteCategory(id);
  }
}
