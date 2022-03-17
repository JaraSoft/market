package com.jarasoft.market.domain.repository;

import com.jarasoft.market.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getAll();

    Optional<List<Product>> getByCategory(int categoryId);

    Optional<List<Product>> getScarceProducts(int quantity);

    Optional<Product> getProduct(int productId);

    Product save(Product product);

    boolean delete(int productId);
}
