package academy.ingress.mscategory.dao.repository;

import academy.ingress.mscategory.dao.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface SubCategoryRepository extends CrudRepository<SubCategory, Long>,
    JpaSpecificationExecutor<SubCategory> {

}
