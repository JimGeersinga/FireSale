package com.FireSale.api.mapper;

import com.FireSale.api.dto.AddressDTO;
import com.FireSale.api.dto.AddressRequest;
import com.FireSale.api.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper extends ModelToDTOMapper<Address, AddressDTO> {
    Address registerToAddress(AddressRequest addressRequest);

}