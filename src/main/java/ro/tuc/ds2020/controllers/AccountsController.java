package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.AccountsDTO;
import ro.tuc.ds2020.dtos.AccountsUpdateDTO;
import ro.tuc.ds2020.services.AccountsService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class AccountsController {
    private final AccountsService accountsService;

    @Autowired
    public AccountsController(AccountsService accountsService){
        this.accountsService = accountsService;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS").allowedOrigins("*");
            }
        };
    }

    @GetMapping()
    @RequestMapping("/accounts")
    public ResponseEntity<List<AccountsDTO>> getAccounts(){
        List<AccountsDTO> dtos = accountsService.findAllAccounts();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<?> insertAccount(@Valid @RequestBody AccountsDTO accountsDTO){
        try{
            Long id = accountsService.insert(accountsDTO);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }catch (ResourceNotFoundException e){
            return new ResponseEntity<>("Account with this username already exists. Please choose another username",
                    HttpStatus.OK);
        }
    }

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<?> verifyLogin(@Valid @RequestBody AccountsDTO accountsDTO){
        try{
            accountsService.verifyAccount(accountsDTO);
            return new ResponseEntity<>("Account credentials are correct", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Username/password incorrect", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/accounts/{id}")
    public ResponseEntity<AccountsDTO> getAccount(@PathVariable("id") Long accountID){
        AccountsDTO accountsDTO = accountsService.findAccByID(accountID);
        return new ResponseEntity<>(accountsDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/accounts/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id){
        accountsService.delete(id);
        return new ResponseEntity<>("User was successfully deleted", HttpStatus.OK);
    }

    @PutMapping
    @RequestMapping("/updateAccount")
    public ResponseEntity<?> updateAccount(@Valid @RequestBody AccountsUpdateDTO dto){
        accountsService.update(dto);
        return new ResponseEntity<>("User was successfully updated", HttpStatus.OK);
    }
}
