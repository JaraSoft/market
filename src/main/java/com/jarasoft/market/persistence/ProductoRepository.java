package com.jarasoft.market.persistence;

import com.jarasoft.market.domain.Product;
import com.jarasoft.market.domain.repository.ProductRepository;
import com.jarasoft.market.persistence.crud.ProductoCrudRepository;
import com.jarasoft.market.persistence.entity.Producto;
import com.jarasoft.market.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository {
    private final ProductoCrudRepository productoCrudRepository;
    private final ProductMapper productMapper;

    public ProductoRepository(ProductoCrudRepository productoCrudRepository, ProductMapper productMapper) {
        this.productoCrudRepository = productoCrudRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> getAll() {
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        return productMapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of(productMapper.toProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScarceProducts(int quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        return productos.map(prods -> productMapper.toProducts(prods));
    }

    @Override
    public Optional<Product> getProduct(int productId) {

        return  productoCrudRepository.findById(productId).map(producto -> productMapper.toProduct(producto));
    }

    @Override
    public Product save(Product product) {
        Producto producto = productMapper.toProducto(product);
        return productMapper.toProduct(productoCrudRepository.save(producto));
    }

    public Optional<List<Producto>> getInactivos() {
        return productoCrudRepository.findDistinctByEstado(true);
    }

    public Optional<List<Producto>> getabundates(int cantidad) {
        return productoCrudRepository.findDistinctByCantidadStockGreaterThanEqualOrderByNombre(cantidad);
    }

    @Override
    public boolean delete(int productId) {
        productoCrudRepository.deleteById(productId);
        return true;
    }
}
