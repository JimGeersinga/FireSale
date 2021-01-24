package com.FireSale.api.mapper;

import com.FireSale.api.dto.address.AddressDTO;
import com.FireSale.api.dto.address.PatchAddressDTO;
import com.FireSale.api.dto.address.UpdateAddressDTO;
import com.FireSale.api.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper extends ModelToDTOMapper<Address, AddressDTO> {
    Address toModel(PatchAddressDTO patch);

    Address toModel(UpdateAddressDTO update);
}