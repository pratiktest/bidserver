package com.bidproject.service.bidserver.bidder;

import com.bidproject.service.bidserver.bid.Bid;
import com.bidproject.service.bidserver.project.Project;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Bidder {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(mappedBy="bidder")
    private List<Bid> bids;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }
}
