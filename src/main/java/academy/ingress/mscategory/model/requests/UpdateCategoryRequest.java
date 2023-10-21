package academy.ingress.mscategory.model.requests;

import jakarta.validation.constraints.Digits;
import java.util.Objects;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString
public class UpdateCategoryRequest {

  private String name;
  @Digits(message = "parentId should be a number", fraction = 0, integer = 10)
  private Long parentId;
  @BooleanFlag
  private Boolean isFavorite;
  @BooleanFlag
  private Boolean isVisible;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateCategoryRequest that = (UpdateCategoryRequest) o;
    return Objects.equals(name, that.name) && Objects.equals(parentId,
        that.parentId) && Objects.equals(isFavorite, that.isFavorite)
        && Objects.equals(isVisible, that.isVisible);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, parentId, isFavorite, isVisible);
  }
}
