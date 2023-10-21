package academy.ingress.mscategory.specification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CategorySpecificationDto {

  private Long id;
  private String name;
  private Boolean isFavorite;

  @Builder.Default
  @JsonIgnore
  private Boolean isVisible = true;
}
