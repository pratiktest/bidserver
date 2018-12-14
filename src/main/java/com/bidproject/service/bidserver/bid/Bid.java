package com.bidproject.service.bidserver.bid;

import com.bidproject.service.bidserver.bidder.Bidder;
import com.bidproject.service.bidserver.project.Project;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Bid {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JsonIgnore
    private Bidder bidder;

    @ManyToOne(fetch= FetchType.LAZY)
    @JsonIgnore
    private Project project;

    private Float price;

    @CreationTimestamp
    private Date placetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getPlacetime() {
        return placetime;
    }
}
