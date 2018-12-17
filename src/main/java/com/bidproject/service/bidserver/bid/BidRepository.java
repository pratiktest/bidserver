package com.bidproject.service.bidserver.bid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {

    List<Bid> findByProjectId(Integer projectId);
    List<Bid> findByBidderIdAndProjectId(Integer bidderId, Integer projectId);
}
