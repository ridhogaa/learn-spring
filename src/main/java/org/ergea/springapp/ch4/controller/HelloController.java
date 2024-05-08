package org.ergea.springapp.ch4.controller;

import jakarta.persistence.criteria.Predicate;
import org.ergea.springapp.ch4.entity.Employee;
import org.ergea.springapp.ch4.service.EmployeeService;
import org.ergea.springapp.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class HelloController {

    @Autowired
    private EmployeeService service;

    @Autowired
    private Response response;

    @GetMapping("hello")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("{name}")
    public String helloName(@PathVariable String name) {
        return "Hello " + name;
    }


    @GetMapping("test")
    public ResponseEntity<?> employee() {
        return ResponseEntity.ok(service.getEmployee());
    }

    @PostMapping("test")
    public ResponseEntity<?> createEmployee() {
        return ResponseEntity.ok(service.createEmployee());
    }

    @GetMapping("employee")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("employee")
    public ResponseEntity<?> delete() {
        return ResponseEntity.ok(service.delete(null));
    }

    @PutMapping("employee")
    public ResponseEntity<?> edit(@RequestBody Employee employee) {
        return ResponseEntity.ok(service.edit(employee));
    }

    @PostMapping("employee")
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        return ResponseEntity.ok(service.save(employee));
    }

    @GetMapping("employee/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("employee/list")
    public ResponseEntity<?> findAllPaging(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(response.sukses(service.pagination(page, size)), HttpStatus.OK);
    }

    @GetMapping(value = {"response-integer", "/response-integer/"})
    public Integer exInteger() {
        return 1;
    }

    @GetMapping(value = {"response-string", "/response-string/"})
    public String exString() {
        return "12";
    }

    @PostMapping(value = {"response-object-emp", "/response-object-emp/"})
    public Employee exString(@RequestBody Employee req) {
        return req;
    }

    @GetMapping(value = {"/list-user", "/list-user/"})
    public ResponseEntity<Map> list(@RequestParam(required = false, name = "name") String name,
                                    @RequestParam(required = false, name = "address") String address,
                                    @RequestParam(required = false, name = "nik") String nik,
                                    @PageableDefault(page = 0, size = 10) Pageable pageable) {


        Specification<Employee> spec = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (address != null && !address.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + address.toLowerCase() + "%"));
            }
            if (nik != null && !nik.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("detailKaryawan").get("nik")), "%" + nik.toLowerCase() + "%"));
            }

//            Aaa = Aaa
            // AaaA=aAaA
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

//        Page<Employee> clientCompanyList = service.findAll(spec, pageable);
        return new ResponseEntity<Map>(response.sukses(""), HttpStatus.OK);
    }
}
