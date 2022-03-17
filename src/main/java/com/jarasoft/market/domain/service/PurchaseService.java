package com.jarasoft.market.domain.service;

import com.jarasoft.market.domain.Purchase;
import com.jarasoft.market.domain.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {
    private PurchaseRepository purchaseRepository;

    private PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }
    public List<Purchase> getAll(){
        return purchaseRepository.getAll();
    }
    public Optional<List<Purchase>> getByClient(int clientId){
        return purchaseRepository.getByClient(clientId);
    }
    public Purchase save(Purchase purchase){
        return purchaseRepository.save(purchase);
    }
}
