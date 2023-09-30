package academy.ingress.mscategory.dao.repository;

import academy.ingress.mscategory.dao.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>,
    JpaSpecificationExecutor<Category>, PagingAndSortingRepository<Category, Long> {

  Optional<Category> findCategoryById(Long id);

}
