package academy.ingress.mscategory.model.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubCategoryRequest {

  @NotBlank(message = "name is mandatory")
  private String name;

  @Min(value = 1, message = "parent id must be greater than 0")
  private Long parentId;
}
