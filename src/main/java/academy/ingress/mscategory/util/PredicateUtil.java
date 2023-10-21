package academy.ingress.mscategory.util;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public enum PredicateUtil {
  PREDICATE_UTIL;

  public <T, F> Predicate createPredicate(Root<T> root, CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder, F filter) {
    List<Predicate> predicates = new ArrayList<>();

    for (Field field : filter.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      try {
        if (field.get(filter) != null) {
          predicates.add(criteriaBuilder.equal(root.get(field.getName()), field.get(filter)));
        }
      } catch (IllegalAccessException e) {
        log.error("ActionLog.createPredicate.error exception: {}", e.getMessage());
      }
    }
    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
