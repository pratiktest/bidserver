package com.bidproject.service.bidserver.project;

import com.bidproject.service.bidserver.bid.Bid;
import com.bidproject.service.bidserver.seller.Seller;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.util.Date;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    private String title;

    @ManyToOne(fetch= FetchType.LAZY)
    @JsonIgnore
    private Seller seller;

    @OneToMany(fetch= FetchType.LAZY, mappedBy="project")
    private List<Bid> bids;

    private Integer minBidder = -1;

    private Double minPrice = Double.MAX_VALUE;

    private Boolean winner = false;

    @CreationTimestamp
    @Future
    private Date expiry;

    @CreationTimestamp
    private Date createdTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public Integer getMinBidder() {
        return minBidder;
    }

    public void setMinBidder(Integer minBidder) {
        this.minBidder = minBidder;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Boolean getWinner() {
        return winner;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }
}
