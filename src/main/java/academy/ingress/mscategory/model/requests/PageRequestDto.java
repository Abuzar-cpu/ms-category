package academy.ingress.mscategory.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PageRequestDto {

  private int page;

  private int size;
}
