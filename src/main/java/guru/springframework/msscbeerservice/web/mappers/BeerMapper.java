package guru.springframework.msscbeerservice.web.mappers;

import guru.springframework.brewery.model.BeerDto;
import guru.springframework.msscbeerservice.domain.Beer;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerMapper extends GenericMapper<BeerDto, Beer> {}
