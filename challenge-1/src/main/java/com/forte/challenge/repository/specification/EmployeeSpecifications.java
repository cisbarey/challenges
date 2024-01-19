package com.forte.challenge.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import com.forte.challenge.domain.Employee;

public class EmployeeSpecifications {

    public static Specification<Employee> hasName(String name) {
        return (employee, cq, cb) -> name == null ? cb.isTrue(cb.literal(true)) :
                cb.like(employee.get("name"), "%" + name + "%");
    }

    public static Specification<Employee> hasPosition(String position) {
        return (employee, cq, cb) -> position == null ? cb.isTrue(cb.literal(true)) :
                cb.like(employee.get("position"), "%" + position + "%");
    }

    public static Specification<Employee> hasDepartment(String department) {
        return (employee, cq, cb) -> department == null ? cb.isTrue(cb.literal(true)) :
                cb.like(employee.get("department"), "%" + department + "%");
    }
}
