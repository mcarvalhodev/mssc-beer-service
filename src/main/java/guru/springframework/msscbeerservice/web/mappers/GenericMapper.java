package guru.springframework.msscbeerservice.web.mappers;

public interface GenericMapper<D, E> {

  E toEntity(D source);

  D toDto(E source);
}
