package academy.ingress.mscategory.specification;

import static academy.ingress.mscategory.util.PredicateUtil.PREDICATE_UTIL;

import academy.ingress.mscategory.dao.entity.SubCategory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;


public record SubCategorySpecification(
    SubCategorySpecificationDto subCategorySpecificationDto) implements Specification<SubCategory> {

  @Override
  public Predicate toPredicate(@NonNull Root<SubCategory> root, @NonNull CriteriaQuery<?> query,
      @NonNull CriteriaBuilder criteriaBuilder) {
    return PREDICATE_UTIL.createPredicate(root, query, criteriaBuilder,
        subCategorySpecificationDto);
  }
}
