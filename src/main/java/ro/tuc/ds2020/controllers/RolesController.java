package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.RolesDTO;
import ro.tuc.ds2020.services.RolesServices;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/roles")
public class RolesController {
    private final RolesServices rolesService;

    @Autowired
    public RolesController(RolesServices rolesServices){
        this.rolesService = rolesServices;
    }

    @GetMapping()
    public ResponseEntity<List<RolesDTO>> getRoles(){
        List<RolesDTO> dtos = rolesService.findAllRoles();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> insertRole(@Valid @RequestBody RolesDTO rolesDTO){
        Long id = rolesService.insert(rolesDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RolesDTO> getRole(@PathVariable("id") Long roleID){
        RolesDTO rolesDTO = rolesService.findRoleByID(roleID);
        return new ResponseEntity<>(rolesDTO, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRole(@RequestBody Long id){
        rolesService.delete(id);
        return new ResponseEntity<>("Role deleted successfully", HttpStatus.OK);
    }
}
