package io.getarrays.learningjwt.auth.mappers;

import io.getarrays.learningjwt.auth.dtos.InventoryAppUserDetails;
import io.getarrays.learningjwt.auth.model.InventoryAppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryAppUserMapper {

    InventoryAppUserDetails toInventoryAppUserDetails(InventoryAppUser user);
}
