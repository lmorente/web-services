package es.andaluces.ourCode.transformer;

import es.andaluces.ourCode.models.UserDto;
import es.andaluces.ourCode.persistences.entities.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDTO(final User user);

    User toMO(final UserDto dto);
}
