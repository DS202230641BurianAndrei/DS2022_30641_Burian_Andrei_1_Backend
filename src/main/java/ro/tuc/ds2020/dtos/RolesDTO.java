package ro.tuc.ds2020.dtos;

import javax.validation.constraints.NotNull;

public class RolesDTO {

    private Long id;
    @NotNull
    private String roleName;

    public RolesDTO(){

    }

    public RolesDTO(String roleName) {
        this.roleName = roleName;
    }

    public RolesDTO(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
