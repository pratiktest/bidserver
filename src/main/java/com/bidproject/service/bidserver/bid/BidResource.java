package com.bidproject.service.bidserver.bid;

import com.bidproject.service.bidserver.seller.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class BidResource {

    @Autowired
    private BidRepository bidRepository;

    @GetMapping("/bid/{id}")
    public Bid getBid(@PathVariable int id) throws NotFoundException {
        Optional<Bid> bid =  bidRepository.findById(id);
        if(!bid.isPresent()){
            throw new NotFoundException("Bid "+ id + " not found");
        }
        return bid.get();
    }
}
