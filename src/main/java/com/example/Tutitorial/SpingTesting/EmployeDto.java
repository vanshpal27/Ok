package com.example.Tutitorial.SpingTesting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeDto {

    private Long id;
    private String name;
    private String email;
}
