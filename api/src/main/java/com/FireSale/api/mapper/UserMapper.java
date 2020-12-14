package com.FireSale.api.mapper;

import com.FireSale.api.dto.user.*;
import com.FireSale.api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends ModelToDTOMapper<User, UserDTO> {
    @Mapping(target= "avatar", source = "avatar.path")
    UserProfileDTO toProfile(User user);
    User toModel(RegisterDTO register);
    User toModel(PatchUserDTO patch);
    User toModel(UpdateUserDTO update);

    @Override
    @Mapping(target= "avatar", source = "avatar.path")
    UserDTO toDTO(User user);

    @Override
    @Mapping(target = "avatar.path", source = "avatar", ignore = true)
    User toModel(UserDTO user);
}