package com.FireSale.api.mapper;

import com.FireSale.api.dto.user.*;
import com.FireSale.api.model.Image;
import com.FireSale.api.model.User;
import jdk.jfr.Name;
import org.mapstruct.*;

import java.util.Arrays;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends ModelToDTOMapper<User, UserDTO> {

    @Override
    @Mapping(target = "avatar", ignore = true)
    User toModel(UserDTO user);
    User toModel(RegisterDTO register);
    User toModel(PatchUserDTO patch);
    User toModel(UpdateUserDTO update);

    @Mapping(target = "image", source = "avatar.path")
    UserProfileDTO toProfile(User user);

    default byte[] map(Image value) {
        if(value == null) return null;
        return value.getPath();
    }
}