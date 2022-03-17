package com.jarasoft.market.domain.repository;

import com.jarasoft.market.domain.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository {
    List<Purchase> getAll();
    Optional<List<Purchase>> getByClient(int clientId);
    Purchase save(Purchase purchase);
}
