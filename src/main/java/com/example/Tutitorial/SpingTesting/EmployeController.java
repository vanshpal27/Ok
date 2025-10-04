package com.example.Tutitorial.SpingTesting;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employe")
public class EmployeController {


    @Autowired
    private  EmployeService employeService;

    @PostMapping
    public ResponseEntity<EmployeDto> create(@RequestBody EmployeDto employeDto )
    {
        return  new ResponseEntity(employeService.create(employeDto), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public  ResponseEntity<EmployeDto> get(@PathVariable Long id)
    {
         return  new ResponseEntity<>(employeService.getById(id),HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/{id}")
    public  ResponseEntity<EmployeDto> update(@PathVariable Long id,@RequestBody EmployeDto employeDto)
    {
        return  new ResponseEntity<>(employeService.update(id,employeDto),HttpStatus.ACCEPTED);
    }

    @GetMapping("/getEmail/{email}")
    public  ResponseEntity<List<EmployeDto>> getByEmail(@PathVariable String email)
    {
        return  new ResponseEntity<>(employeService.findByEmail(email),HttpStatus.ACCEPTED);
    }


}
