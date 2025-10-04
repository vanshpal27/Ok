package com.example.Tutitorial.SpingTesting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Import(Docker.class)
public class EmployeServiceTest {

    @InjectMocks
    private EmployeService employeService;

    @Mock
    private EmployeRepo employeRepo;

    @Spy
    private ModelMapper modelMapper;

    private  Employe employe;
    private Long id;

    @BeforeEach
    void setUp()
    {
      employe = Employe.builder()
                .email("vansh@gmail.com")
                .name("vansh")
                .build();

      id = 1L;

    }


    @Test
    public  void testGetById_WhenEmployeIsPresent_ThenEmployeDto()
    {

        when(employeRepo.findById(anyLong())).thenReturn(Optional.of(employe));

        EmployeDto employeDto = employeService.getById(id);
        assertThat(employeDto.getName()).isEqualTo("vansh");
        Mockito.verify(employeRepo,Mockito.atLeast(1)).findById(id);
    }

    @Test
    public  void testGetById_WhenEmployeIsNotPresent_ThenReturnRuntime()
    {
        when(employeRepo.findById(anyLong())).thenReturn(Optional.empty());

      assertThatThrownBy(() ->employeService.getById(id)).isInstanceOf(RuntimeException.class);
    }




    @Test
    public  void testSave_ThenReturnEmployeDto()
    {
        when(employeRepo.save(any(Employe.class))).thenReturn(employe);

        EmployeDto employeDto = employeService.create(modelMapper.map(employe,EmployeDto.class));
        ArgumentCaptor<Employe> argumentCaptor = ArgumentCaptor.forClass(Employe.class);
        assertThat(employeDto.getName()).isEqualTo("vansh");
        verify(employeRepo,atLeast(1)).save(any(Employe.class));
        verify(employeRepo).save(argumentCaptor.capture());
        Employe employe1 = argumentCaptor.getValue();
        assertThat(employe1.getEmail()).isEqualTo("vansh@gmail.com");
    }

    @Test
    public  void testFindByEmail()
    {
        List<Employe> list = List.of(employe);
        when(employeRepo.findByEmail(anyString())).thenReturn(Optional.of(list));

        List<EmployeDto> list1 = employeService.findByEmail("sdfsd");

        assertThat(list1.get(0).getEmail()).isEqualTo(employe.getEmail());
        verify(employeRepo,atLeastOnce()).findByEmail(anyString());
    }

    @Test
    public  void testFindByEmail_ThanReturnException()
    {
        when(employeRepo.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(()-> employeService.findByEmail(anyString()))
                .isInstanceOf(RuntimeException.class);

    }

    @Test
    public  void  testUpdate()
    {
        when(employeRepo.findById(anyLong())).thenReturn(Optional.of(employe));
        employe.setName("ok");
        when(employeRepo.save(any(Employe.class))).thenReturn(employe);

        EmployeDto employeDto = employeService.update(1L,modelMapper.map(employe,EmployeDto.class));

        assertThat(employeDto.getName()).isEqualTo("ok");
    }

    @Test
    public  void  testUpdate_ThanException()
    {
       when(employeRepo.findById(anyLong())).thenReturn(Optional.empty());

       assertThatThrownBy(()-> employeService.update(1L,modelMapper.map(employe,EmployeDto.class)))
               .isInstanceOf(RuntimeException.class);
       verify(employeRepo,never()).save(any(Employe.class));

    }



}
