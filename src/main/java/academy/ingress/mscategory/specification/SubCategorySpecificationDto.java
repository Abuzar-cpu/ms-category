package academy.ingress.mscategory.specification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategorySpecificationDto {

  private Long id;
  private String name;
  private Boolean isFavorite;

  @Builder.Default
  @JsonIgnore
  private Boolean isVisible = true;
}
