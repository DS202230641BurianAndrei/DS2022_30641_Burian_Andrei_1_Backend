package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.RolesDTO;
import ro.tuc.ds2020.entities.Roles;

public class RolesBuilder {

    private RolesBuilder(){

    }

    public static RolesDTO toRolesDTO(Roles role){
        return new RolesDTO(role.getId(), role.getRoleName());
    }

    public static Roles toEntity(RolesDTO rolesDTO){
        return new Roles(rolesDTO.getId(), rolesDTO.getRoleName());
    }
}
