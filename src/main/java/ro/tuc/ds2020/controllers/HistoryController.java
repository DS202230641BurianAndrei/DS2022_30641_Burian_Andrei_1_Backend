package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.HistoryDTO;
import ro.tuc.ds2020.dtos.HistoryUpdateDTO;
import ro.tuc.ds2020.services.HistoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public ResponseEntity<List<HistoryDTO>> getEntireHistory(){
        List<HistoryDTO> dtos = historyService.findAllHistory();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{device_id}")
    public ResponseEntity<List<HistoryDTO>> getDeviceHistory(@PathVariable("device_id") Long deviceId){
        List<HistoryDTO> dtos = historyService.findDeviceHistory(deviceId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> insertHistory(@Valid @RequestBody HistoryDTO historyDTO){
        Long id = historyService.insert(historyDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHistory(@PathVariable("id") Long id){
        historyService.delete(id);
        return new ResponseEntity<>("Successfully deleted history tuple!", HttpStatus.OK);
    }

    @PutMapping
    @RequestMapping("/updateHistory")
    public ResponseEntity<?> updateHistory(@Valid @RequestBody HistoryUpdateDTO dto){
        historyService.update(dto);
        return new ResponseEntity<>("History was successfully updated", HttpStatus.OK);
    }
}
