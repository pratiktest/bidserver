package com.bidproject.service.bidserver.seller;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SellerDaoService {
    private static List<Seller> sellers =  new ArrayList<>();

    private static int count = 3;

    static {
        sellers.add(new Seller(1, "Adam"));
        sellers.add(new Seller(2, "Eve"));
        sellers.add(new Seller(3, "Jack"));
    }

    public List<Seller> findAll(){
        return sellers;
    }

    public Seller save(Seller seller){
        if(seller.getId() == null){
            seller.setId(++count);
        }
        sellers.add(seller);
        return seller;
    }

    public Seller findOne(int id){
        for(Seller seller: sellers){
            if(seller.getId() == id){
                return seller;
            }
        }
        return null;
    }
}
