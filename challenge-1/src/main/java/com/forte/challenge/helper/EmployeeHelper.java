package com.forte.challenge.helper;

import com.forte.challenge.domain.Employee;
import com.forte.challenge.dto.request.EmployeeRequest;
import com.forte.challenge.dto.response.EmployeeResponse;
import org.springframework.stereotype.Component;

@Component
public class EmployeeHelper {

    public EmployeeResponse convertToDTO(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .dateOfJoining(employee.getDateOfJoining())
                .department(employee.getDepartment())
                .position(employee.getPosition())
                .build();
    }

    public Employee convertToEntity(EmployeeRequest dto) {
        Employee entity = new Employee();
        entity.setName(dto.getName());
        entity.setDateOfJoining(dto.getDateOfJoining());
        entity.setDepartment(dto.getDepartment());
        entity.setPosition(dto.getPosition());
        return entity;
    }
}
