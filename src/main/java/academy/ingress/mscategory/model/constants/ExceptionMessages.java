package academy.ingress.mscategory.model.constants;

import lombok.Getter;

@Getter
public enum ExceptionMessages {
  INTERNAL_SERVER_ERROR_EXCEPTION("unknown exception occurred"),
  CATEGORY_NOT_FOUND("requested category not found");
  private final String message;

  ExceptionMessages(String message) {
    this.message = message;
  }
}
