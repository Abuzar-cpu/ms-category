package academy.ingress.mscategory.model.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCategoryResponse {

  private Long id;
  private String name;
  private Boolean isFavorite;
}
