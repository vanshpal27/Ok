package com.example.Tutitorial.SpingTesting;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeRepo extends JpaRepository<Employe,Long> {
   Optional <List<Employe>> findByEmail(String email);
}
