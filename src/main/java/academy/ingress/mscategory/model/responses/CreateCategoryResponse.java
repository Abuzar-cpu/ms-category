package academy.ingress.mscategory.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryResponse {

  private String name;
  private Boolean isFavorite;
  private Boolean isVisible;
}
