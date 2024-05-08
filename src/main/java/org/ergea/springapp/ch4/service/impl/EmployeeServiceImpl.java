package org.ergea.springapp.ch4.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ergea.springapp.ch4.entity.Employee;
import org.ergea.springapp.ch4.repo.EmployeeRepo;
import org.ergea.springapp.ch4.service.EmployeeService;
import org.ergea.springapp.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepo repository;

    @Autowired
    private Response response;

    public List<Object[]> getEmployee() {
        return repository.getEmployeeList();
    }

    public String createEmployee() {
        repository.createEmployee();
        return "Success";
    }

    public Map findAll() {
        var employees = repository.findAll();
        if (employees.isEmpty()) {
            return response.error("data tidak ada", 404);
        }
        return response.sukses(employees);
    }

    @Override
    public Map save(Employee employee) {
        if (response.chekNull(employee.getName())) {
            return response.error("Name is required.", 402);
        }
        if (StringUtils.isEmpty(employee.getName())) {
            return response.error("Name is required.", 402);
        }

        return response.sukses(repository.save(employee));
    }

    @Override
    public Map delete(Employee employee) {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        var test = testRestTemplate.exchange("http://dev.farizdotid.com/api/daerahindonesia/provinsi", HttpMethod.GET, null, Object.class).getBody();
        return response.sukses(test);
    }

    @Override
    public Map edit(Employee employee) {
        try {
            if (response.chekNull(employee.getId())) {
                return response.error("Id is required.", 402);
            }

            Optional<Employee> getId = repository.findById(employee.getId());
            if (!getId.isPresent()) {
                return response.error("id not found", 404);
            }

            Employee edit = getId.get();
            edit.setAddress(employee.getAddress());
            edit.setName(employee.getName());
            edit.setDob(employee.getDob());


            return response.sukses(repository.save(edit));
        } catch (Exception e) {
            return response.error(e, 500);
        }
    }

    @Override
    public Map findById(Long id) {
        Optional<Employee> employee = repository.findById(id);
        if (!employee.isPresent()) {
            return response.error("id not found", 404);
        }
        return response.sukses(employee.get());
    }

    @Override
    public Map pagination(int page, int size) {
        return response.sukses(repository.getAllDataPage(PageRequest.of(page, size, Sort.by("id").descending())));
    }

    @Override
    public Map paginationByName(int page, int size, String name) {
        return response.sukses(repository.getAllDataByName(name, PageRequest.of(page, size, Sort.by("id").descending())));
    }
}
