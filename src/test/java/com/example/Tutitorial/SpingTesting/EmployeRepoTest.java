package com.example.Tutitorial.SpingTesting;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@Import(Docker.class)
public class EmployeRepoTest {

    @Autowired
    private  EmployeRepo employeRepo;

    private  Employe employe;

    @BeforeEach
    void setUp()
    {
         employe = Employe.builder()
                .email("vansh@gmail.com")
                .name("vansh")
                .build();
    }






}
