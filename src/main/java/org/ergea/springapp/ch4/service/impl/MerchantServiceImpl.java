package org.ergea.springapp.ch4.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ergea.springapp.ch4.entity.Merchant;
import org.ergea.springapp.ch4.repo.MerchantRepository;
import org.ergea.springapp.ch4.service.MerchantService;
import org.ergea.springapp.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private Response response;

    @Override
    public Map save(Merchant merchant) {
        return response.sukses(merchantRepository.save(merchant));
    }

    @Override
    public Map delete(UUID uuid) {
        Optional<Merchant> merchant = merchantRepository.findById(uuid);
        if (!merchant.isPresent()) {
            return response.error("Merchant ID Not Found", HttpStatus.NOT_FOUND);
        }
        merchantRepository.delete(merchant.get());
        return response.sukses(merchant.get());
    }

    @Override
    public Map findById(UUID uuid) {
        Optional<Merchant> merchant = merchantRepository.findById(uuid);
        if (!merchant.isPresent()) {
            return response.error("Merchant ID Not Found", HttpStatus.NOT_FOUND);
        }
        return response.sukses(merchant.get());
    }

    @Override
    public Map findAll(Pageable pageable, Specification specification) {
        return response.sukses(merchantRepository.findAll(specification, pageable).getContent());
    }

    @Override
    public Map update(UUID uuid, Merchant request) {
        Optional<Merchant> merchant = merchantRepository.findById(uuid);
        if (!merchant.isPresent()) {
            return response.error("Merchant ID Not Found", HttpStatus.NOT_FOUND);
        }

        merchant.get().setName(request.getName());
        merchant.get().setLocation(request.getLocation());
        merchant.get().setOpen(request.getOpen());

        return response.sukses(merchant.get());
    }

    @Override
    public Map testRestTemplate() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        var test = testRestTemplate.exchange("http://dev.farizdotid.com/api/daerahindonesia/provinsi", HttpMethod.GET, null, Object.class).getBody();
        return response.sukses(test);
    }
}
