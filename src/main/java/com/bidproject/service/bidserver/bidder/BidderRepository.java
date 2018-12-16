package com.bidproject.service.bidserver.bidder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidderRepository extends JpaRepository<Bidder, Integer> {

    List<Bidder> findByName(String name);
}
