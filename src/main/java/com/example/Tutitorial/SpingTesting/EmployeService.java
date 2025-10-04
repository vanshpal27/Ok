package com.example.Tutitorial.SpingTesting;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployeRepo employeRepo;

    public  EmployeDto create(EmployeDto employeDto)
    {
     return  modelMapper.map(employeRepo.save(modelMapper.map(employeDto,Employe.class)),EmployeDto.class);
    }

    public  EmployeDto getById(Long id)
    {
        Employe employe = employeRepo.findById(id).orElseThrow(() -> new RuntimeException());

        return modelMapper.map(employe,EmployeDto.class);
    }

    public List<EmployeDto> findByEmail(String email)
    {
        List<Employe> list = employeRepo.findByEmail(email).orElseThrow(()-> new RuntimeException());

        return list.stream().map((element) ->  modelMapper.map(element, EmployeDto.class)).collect(Collectors.toList());
    }

    public EmployeDto update(Long id,EmployeDto employeDto)
    {
        Employe employe = employeRepo.findById(id).orElseThrow(() -> new RuntimeException());
        modelMapper.map(employeDto,employe);
        employe.setId(id);
        return  modelMapper.map(employeRepo.save(modelMapper.map(employeDto,Employe.class)),EmployeDto.class);

    }

    public  Boolean delete(Long id)
    {
        Employe employe = employeRepo.findById(id).orElseThrow(() -> new RuntimeException());
        employeRepo.deleteById(id);
        return  true;
    }

}
