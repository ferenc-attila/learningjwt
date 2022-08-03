package io.getarrays.learningjwt.auth.mappers;

import io.getarrays.learningjwt.auth.dtos.InventoryAppRoleDetails;
import io.getarrays.learningjwt.auth.model.InventoryAppRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryAppRoleMapper {

    InventoryAppRoleDetails toInventoryAppRoleDetails(InventoryAppRole role);
}
