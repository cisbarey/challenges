package com.forte.challenge.service;

import com.forte.challenge.dto.request.EmployeeRequest;
import com.forte.challenge.dto.request.EmployeeSearchCriteriaRequest;
import com.forte.challenge.dto.response.EmployeeResponse;

import java.util.List;

public interface IEmployeeService {
    EmployeeResponse saveEmployee(EmployeeRequest request);
    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse getEmployeeById(Long id);
    EmployeeResponse updateEmployee(EmployeeRequest request, Long id);
    void deleteEmployee(Long id);
    List<EmployeeResponse> searchEmployees(EmployeeSearchCriteriaRequest criteria);
}
