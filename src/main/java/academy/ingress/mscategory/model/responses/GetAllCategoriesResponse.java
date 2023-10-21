package academy.ingress.mscategory.model.responses;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllCategoriesResponse {

  private Long id;
  private String name;
  private Boolean isFavorite;
  private List<GetSubCategoryResponse> subCategories;
}
