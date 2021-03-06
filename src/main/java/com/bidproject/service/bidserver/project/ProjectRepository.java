package com.bidproject.service.bidserver.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Modifying
    @Transactional
    @Query(value = "update Project p set p.minPrice=?1, p.minBidder=?3, p.bidId=?4 where p.id=?2 and ?1<p.minPrice ")
    int updateMaxBid(Double price, Integer projectId, Integer bidderId, Integer bidId);

    @Modifying
    @Transactional
    @Query(value = "update Project p set p.minPrice=?1, p.minBidder=-1 where p.id=?2 and p.minBidder=?3 and p.bidId=?4 ")
    int deleteMaxBidder(Double price, Integer projectId, Integer bidderId, Integer bidId);

    @Modifying
    @Transactional
    @Query(value = "update Project p set p.winner=true where p.id=?1")
    int updateWinner(Integer projectId);

}
