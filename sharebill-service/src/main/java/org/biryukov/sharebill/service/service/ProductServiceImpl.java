package org.biryukov.sharebill.service.service;

import org.biryukov.sharebill.service.jdbcrepo.ProductJdbcRepository;
import org.biryukov.sharebill.service.jparepo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductJdbcRepository productJdbcRepository;
    @Override
    @Transactional
    public void updatePlainProduct(Product product) {
        productJdbcRepository.updatePlainProduct(product);
    }
}
