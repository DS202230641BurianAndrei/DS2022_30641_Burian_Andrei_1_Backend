package ro.tuc.ds2020.dtos;

import java.util.Objects;
import javax.validation.constraints.NotNull;


public class AccountsDTO{

    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String password;

    public AccountsDTO(){

    }

    public AccountsDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AccountsDTO(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode(){
        return Objects.hash(username, password);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountsDTO toBeCompared = (AccountsDTO) o;
        return Objects.equals(
                this.username, toBeCompared.getUsername()) && Objects.equals(toBeCompared.getPassword(), this.password);
    }
}
