package com.jarasoft.market.persistence;

import com.jarasoft.market.domain.Purchase;
import com.jarasoft.market.domain.repository.PurchaseRepository;
import com.jarasoft.market.persistence.crud.CompraCrudRepository;
import com.jarasoft.market.persistence.entity.Compra;
import com.jarasoft.market.persistence.mapper.PurchaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class CompraRepository implements PurchaseRepository {
    private CompraCrudRepository compraCrudRepository;
    private PurchaseMapper purchaseMapper;

    public CompraRepository(CompraCrudRepository compraCrudRepository, PurchaseMapper purchaseMapper) {
        this.compraCrudRepository = compraCrudRepository;
        this.purchaseMapper = purchaseMapper;
    }

    @Override
    public List<Purchase> getAll() {
        return purchaseMapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(int clientId) {
        return compraCrudRepository.findByIdCliente(clientId).map(compras -> purchaseMapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = purchaseMapper.toCompra(purchase);
        compra.getComprasProductos().forEach(producto -> producto.setCompra(compra));
        return purchaseMapper.toPurchase((compraCrudRepository.save(compra)));
    }
}
