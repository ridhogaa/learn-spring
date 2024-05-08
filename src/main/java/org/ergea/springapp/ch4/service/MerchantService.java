package org.ergea.springapp.ch4.service;

import org.ergea.springapp.ch4.entity.Merchant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;
import java.util.UUID;

public interface MerchantService {
    Map save(Merchant merchant);

    Map delete(UUID uuid);

    Map findById(UUID uuid);

    Map findAll(Pageable pageable, Specification specification);

    Map update(UUID uuid, Merchant request);

    Map testRestTemplate();
}
