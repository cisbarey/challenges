package com.forte.challenge.service.impl;

import com.forte.challenge.domain.Employee;
import com.forte.challenge.dto.request.EmployeeRequest;
import com.forte.challenge.dto.request.EmployeeSearchCriteriaRequest;
import com.forte.challenge.dto.response.EmployeeResponse;
import com.forte.challenge.exception.EmployeeNotFoundException;
import com.forte.challenge.helper.EmployeeHelper;
import com.forte.challenge.repository.IEmployeeRepository;
import com.forte.challenge.repository.specification.EmployeeSpecifications;
import com.forte.challenge.service.IEmployeeService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final IEmployeeRepository repository;
    private final EmployeeHelper helper;

    public EmployeeServiceImpl(IEmployeeRepository repository,
                               EmployeeHelper helper) {
        this.repository = repository;
        this.helper = helper;
    }

    @Override
    public EmployeeResponse saveEmployee(EmployeeRequest request) {
        Employee employee = this.helper.convertToEntity(request);
        return this.helper.convertToDTO(this.repository.save(employee));
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return this.repository.findAll().stream()
                .map(helper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        return this.repository.findById(id)
                .map(helper::convertToDTO)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Override
    public EmployeeResponse updateEmployee(EmployeeRequest request, Long id) {
        Employee existingEmployee = this.repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        if (request.getName() != null && !request.getName().isBlank())
            existingEmployee.setName(request.getName());
        if (request.getDepartment() != null && !request.getDepartment().isBlank())
            existingEmployee.setDepartment(request.getDepartment());
        if (request.getPosition() != null && !request.getPosition().isBlank())
            existingEmployee.setPosition(request.getPosition());
        if (request.getDateOfJoining() != null)
            existingEmployee.setDateOfJoining(request.getDateOfJoining());

        Employee updatedEmployee = this.repository.save(existingEmployee);
        return this.helper.convertToDTO(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = this.repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        this.repository.delete(employee);
    }

    @Override
    public List<EmployeeResponse> searchEmployees(EmployeeSearchCriteriaRequest criteria) {
        Specification<Employee> spec = Specification.where(
                        EmployeeSpecifications.hasName(criteria.getName()))
                .and(EmployeeSpecifications.hasPosition(criteria.getPosition()))
                .and(EmployeeSpecifications.hasDepartment(criteria.getDepartment()));

        return repository.findAll(spec).stream()
                .map(helper::convertToDTO)
                .collect(Collectors.toList());
    }

}
