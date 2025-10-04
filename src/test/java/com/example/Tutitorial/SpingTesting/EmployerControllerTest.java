package com.example.Tutitorial.SpingTesting;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

@AutoConfigureWebClient
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(Docker.class)
public class EmployerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

     private  Employe employe;
     private  EmployeDto employeDto;

     @Autowired
     private  EmployeRepo employeRepo;

     @BeforeEach
     public  void setup()
     {
         employe = Employe.builder()

                 .name("vansh")
                 .email("vanshpal@gmail.com")
                 .build();
         employeDto = EmployeDto.builder()

                 .name("vansh")
                 .email("vanshpal@gmail.com")
                 .build();
     }

     @Test
    public  void testCreate()
     {
         webTestClient
                 .post()
                 .uri("/employe")
                 .bodyValue(employeDto)
                 .exchange()
                 .expectStatus().isCreated();

     }

     @Test
    public  void testGet()
     {
         Employe employe1 = employeRepo.save(employe);
         log.info(employe1.getId().toString());
         webTestClient
                 .get()
                 .uri("/employe/get/{id}",employe1.getId())
                 .exchange()
                 .expectStatus().isAccepted()
                 .expectBody()
                 .jsonPath("$.id").isEqualTo(employe1.getId());
     }

     @Test
    public  void testGet_ThanThrowException()
     {
         webTestClient
                 .get()
                 .uri("/employe/get/{id}",employe.getId())
                 .exchange()
                 .expectStatus().isNotFound();
     }

     @Test
    public  void update()
     {
         Employe employe1 = employeRepo.save(employe);
         EmployeDto employeDto1 = EmployeDto.builder()
                 .email("cdfdsg@gmail.com")
                 .build();
         webTestClient.put()
                 .uri("/employe/update/{id}",employe.getId())
                 .bodyValue(employeDto1)
                 .exchange()
                 .expectStatus().isAccepted()
                 .expectBody()
                 .jsonPath("$.email").isEqualTo("cdfdsg@gmail.com");
     }

}
