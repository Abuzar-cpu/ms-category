package academy.ingress.mscategory.model.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCategoryResponse {

  private String name;
  private Boolean isFavorite;
  private Boolean isVisible;
}
