package org.ergea.springapp.ch4.controller;

import jakarta.persistence.criteria.Predicate;
import org.ergea.springapp.ch4.entity.Merchant;
import org.ergea.springapp.ch4.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/merchants")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(merchantService.delete(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @RequestBody Merchant merchant) {
        return ResponseEntity.ok(merchantService.update(id, merchant));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Merchant merchant) {
        return ResponseEntity.ok(merchantService.save(merchant));
    }

    @GetMapping()
    public ResponseEntity<?> findAll(
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "location") String location,
            @RequestParam(required = false, name = "open") Boolean open,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Specification<Merchant> spec = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (location != null && !location.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("location")), "%" + location.toLowerCase() + "%"));
            }
            if (open != null) {
                predicates.add(criteriaBuilder.equal(root.get("open"), open));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        return ResponseEntity.ok(merchantService.findAll(pageable, spec));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(merchantService.findById(id));
    }

    @GetMapping("rest-template")
    public ResponseEntity<?> testRestTemplate() {
        return ResponseEntity.ok(merchantService.testRestTemplate());
    }

}
