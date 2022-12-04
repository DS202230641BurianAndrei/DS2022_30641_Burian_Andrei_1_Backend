package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ro.tuc.ds2020.dtos.DeviceAccountDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceUpdateDTO;
import ro.tuc.ds2020.services.DeviceService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService){
        this.deviceService = deviceService;
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS").allowedOrigins("*");
//            }
//        };
//    }

    @GetMapping()
    public ResponseEntity<List<DeviceDTO>> getAllDevices(){
        List<DeviceDTO> dtos = deviceService.findAllDevices();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable("id") Long id){
        DeviceDTO deviceDTO = deviceService.findDeviceById(id);
        return new ResponseEntity<>(deviceDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> insertDevice(@Valid @RequestBody DeviceDTO deviceDTO){
        Long id = deviceService.insert(deviceDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable("id") Long id){
        deviceService.delete(id);
        return new ResponseEntity<>("Device successfully deleted", HttpStatus.OK);
    }

//    @PutMapping
//    @RequestMapping("/updateDevices")
//    public ResponseEntity<?> pairDeviceAccount(@Valid @RequestBody DeviceAccountDTO dto){
//        deviceService.updateDeviceAccount(dto);
//        return new ResponseEntity<>("Successfully updated device's user", HttpStatus.OK);
//    }

    @PutMapping
    @RequestMapping("/updateDevice")
    public ResponseEntity<?> updateDevice(@Valid @RequestBody DeviceUpdateDTO dto){
        deviceService.update(dto);
        return new ResponseEntity<>("Device successfully updated", HttpStatus.OK);
    }
}
