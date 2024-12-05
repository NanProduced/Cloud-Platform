package tech.nan.demo.auth.model.converter;

import org.mapstruct.*;
import tech.nan.demo.auth.client.dto.UserDTO;
import tech.nan.demo.auth.model.DO.UserDO;
import tech.nan.demo.auth.model.UserPrincipal;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserConverter {

    @Mapping(source = "id", target = "userId")
    UserPrincipal convert2User(UserDO userDO);

    UserDO convert2UserDO(UserDTO userDTO);

    @AfterMapping
    default void setAuthCode(UserDTO userDTO, @MappingTarget UserDO userDO) {
        int rolesCode = userDTO.getRoles().stream().reduce(0, (a, b) -> a | b);
        userDO.setRolesCode(rolesCode);
    }


}
