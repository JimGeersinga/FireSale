package com.FireSale.api.mapper;

import com.FireSale.api.dto.user.*;
import com.FireSale.api.model.Image;
import com.FireSale.api.model.User;
import com.FireSale.api.util.UrlUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends ModelToDTOMapper<User, UserDTO> {

    @Override
    @Mapping(target = "avatar.path", source = "avatar", ignore = true)
    User toModel(UserDTO user);

    User toModel(RegisterDTO register);
    User toModel(PatchUserDTO patch);
    User toModel(UpdateUserDTO update);

    @Mapping(target= "avatar", source = "avatar", qualifiedByName = "avatarWithBaseUrl")
    UserProfileDTO toProfile(User user);

    @Override
    @Mapping(target= "avatar", source = "avatar", qualifiedByName = "avatarWithBaseUrl")
    UserDTO toDTO(User user);

    @Named("avatarWithBaseUrl")
    public static String avatarWithBaseUrl(Image avatar) {
        return UrlUtil.getBaseUrl() + "file/image/" + avatar.getId();
    }
}