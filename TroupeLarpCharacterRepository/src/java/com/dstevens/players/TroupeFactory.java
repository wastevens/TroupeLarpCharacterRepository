package com.dstevens.players;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dstevens.suppliers.IdSupplier;

@Service
public class TroupeFactory {

    private IdSupplier idSupplier;

    @Autowired
    public TroupeFactory(IdSupplier idSupplier) {
        this.idSupplier = idSupplier;
    }
    
    public Troupe createTroupe(String name) {
        return new Troupe(idSupplier.get(), name);
    }
    
    
}
