package academy.ingress.mscategory.specification;

import static academy.ingress.mscategory.util.PredicateUtil.PREDICATE_UTIL;

import academy.ingress.mscategory.dao.entity.Category;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public record CategorySpecification(CategorySpecificationDto categorySpecificationDto) implements
    Specification<Category> {

  @Override
  public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {

    if (query.getResultType() != Long.class) {
      root.fetch("subCategories", JoinType.LEFT);
    }

    return PREDICATE_UTIL.createPredicate(root, query, criteriaBuilder, categorySpecificationDto);
  }

}
