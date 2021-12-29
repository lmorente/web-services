package es.toposervice.services.mappers;

import es.toposervice.dto.TopographicDetailDto;
import es.toposervice.persistence.model.TopographicDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopographicMapper {

    TopographicDetailDto toDTO(TopographicDetail topographicDetail);
}
