package guru.springframework.msscbeerservice.services.order;

public interface Validator<T> {

  boolean validate(T object);
}
