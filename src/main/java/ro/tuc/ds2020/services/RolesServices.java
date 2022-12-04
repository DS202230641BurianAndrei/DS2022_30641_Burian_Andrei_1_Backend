package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.RolesDTO;
import ro.tuc.ds2020.dtos.builders.RolesBuilder;
import ro.tuc.ds2020.entities.Roles;
import ro.tuc.ds2020.repositories.RolesRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolesServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsService.class);
    private final RolesRepository rolesRepository;

    @Autowired
    public RolesServices(RolesRepository rolesRepository){
        this.rolesRepository = rolesRepository;
    }

    public List<RolesDTO> findAllRoles(){
        List<Roles> rolesList = rolesRepository.findAll();
        return rolesList.stream()
                .map(RolesBuilder::toRolesDTO)
                .collect(Collectors.toList());
    }

    public RolesDTO findRoleByID(Long id){
        Optional<Roles> rolesOptional = rolesRepository.findById(id);
        if ( !rolesOptional.isPresent() ){
            LOGGER.error("Role with id {} was not found in db", id);
            throw new ResourceNotFoundException(Roles.class.getSimpleName() + " with id: " + id);
        }
        return RolesBuilder.toRolesDTO(rolesOptional.get());
    }

    public Long insert(RolesDTO rolesDTO){
        Roles role = RolesBuilder.toEntity(rolesDTO);
        role = rolesRepository.save(role);
        LOGGER.debug("Role with id {} and name {} was inserted in db", role.getId(), role.getRoleName());
        return role.getId();
    }

    public void delete(Long id){
        rolesRepository.deleteById(id);
    }
}
