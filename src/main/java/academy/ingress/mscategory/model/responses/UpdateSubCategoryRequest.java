package academy.ingress.mscategory.model.responses;

import jakarta.validation.constraints.Min;
import java.util.Objects;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSubCategoryRequest {

  private String name;

  @BooleanFlag
  private Boolean isFavorite;

  @BooleanFlag
  private Boolean isVisible;

  @Min(value = 1, message = "parent id must be equal to or greater that 1 ")
  private Long parentId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateSubCategoryRequest that = (UpdateSubCategoryRequest) o;
    return Objects.equals(name, that.name) && Objects.equals(isFavorite,
        that.isFavorite) && Objects.equals(isVisible, that.isVisible)
        && Objects.equals(parentId, that.parentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, isFavorite, isVisible, parentId);
  }
}
