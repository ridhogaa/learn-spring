package org.ergea.springapp.ch4.service;

import org.ergea.springapp.ch4.entity.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    List<Object[]> getEmployee();

    String createEmployee();

    Map findAll();

    Map save(Employee employee);

    Map delete(Employee employee);

    Map edit(Employee employee);

    Map findById(Long id);

    Map pagination(int page, int size);

    Map paginationByName(int page, int size, String name);
}
