package com.FireSale.api.mapper;

import com.FireSale.api.dto.RegisterRequest;
import com.FireSale.api.dto.UserDTO;
import com.FireSale.api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends ModelToDTOMapper<User, UserDTO> {
    User registerToUser(RegisterRequest register);

}