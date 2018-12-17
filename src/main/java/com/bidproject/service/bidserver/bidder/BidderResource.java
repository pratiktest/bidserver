package com.bidproject.service.bidserver.bidder;

import com.bidproject.service.bidserver.bid.Bid;
import com.bidproject.service.bidserver.bid.BidRepository;
import com.bidproject.service.bidserver.project.Project;
import com.bidproject.service.bidserver.project.ProjectExpiredException;
import com.bidproject.service.bidserver.project.ProjectRepository;
import com.bidproject.service.bidserver.seller.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController
public class BidderResource {

    @Autowired
    private BidderRepository bidderRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BidRepository bidRepository;

    @PostMapping("/bidders")
    public ResponseEntity<Object> createBidder(@Valid @RequestBody Bidder bidder) {
        Bidder savedBidder = bidderRepository.save(bidder);
        //gets /sellers from the above uri and appends /{id} to it after which replaces the id template with stored seller id
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedBidder.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/bidder/{id}")
    public Bidder getBidder(@PathVariable int id) throws NotFoundException {
        Optional<Bidder> bidder =  bidderRepository.findById(id);
        if(!bidder.isPresent()){
            throw new NotFoundException("Bidder "+ id + " not found");
        }
        return bidder.get();
    }

    @GetMapping("/bidder/name/{name}")
    public Bidder getBidderByName(@PathVariable String name) throws NotFoundException {
        List<Bidder> bidders =  bidderRepository.findByName(name);
        return bidders.get(0);
    }

    @PostMapping("/bidder/{bidderId}/project/{projectId}/bid")
    public ResponseEntity<Object> placeBid(@PathVariable int bidderId, @PathVariable int projectId, @Valid @RequestBody Bid bid) throws NotFoundException, ProjectExpiredException {
        Optional<Bidder> bidderOptional =  bidderRepository.findById(bidderId);
        Optional<Project> projectOptional =  projectRepository.findById(projectId);
        if(!bidderOptional.isPresent()){
            throw new NotFoundException("Bidder "+ bidderId + " not found");
        }
        if(!projectOptional.isPresent()){
            throw new NotFoundException("Project "+ projectId + " not found");
        }
        Bidder bidder = bidderOptional.get();
        Project project = projectOptional.get();
        if(project.getExpiry().compareTo(new Date()) < 0){
            throw new ProjectExpiredException("Project "+ projectId + " not found");
        }
        bid.setBidder(bidder);
        bid.setProject(project);
        bidRepository.save(bid);

        int rows = projectRepository.updateMaxBid(bid.getPrice(), projectId, bidderId);
        System.out.println(rows);

        //gets /sellers from the above uri and appends /{id} to it after which replaces the id template with stored seller id
        URI location = ServletUriComponentsBuilder.fromPath("/bid/{id}").buildAndExpand(bid.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/bidder/{bidderId}/project/{projectId}")
    public ResponseEntity<Object> deleteBid(@PathVariable int bidderId, @PathVariable int projectId) throws NotFoundException, ProjectExpiredException {
        int updatedRows = projectRepository.deleteMaxBidder(Double.MAX_VALUE, projectId, bidderId);
        //send response and return do below in a kafka event consumer
        List<Bid> bids = bidRepository.findByBidderIdAndProjectId(bidderId, projectId);
        bidRepository.deleteInBatch(bids);
        List<Bid> projectbids = bidRepository.findByProjectId(projectId);
        projectbids.sort((Bid b1, Bid b2) -> {
            if(b1.getPrice()<b2.getPrice()){
                return -1;
            }else if(b1.getPrice()>b2.getPrice()){
                return 1;
            }else{
                int compareTime = b1.getPlacetime().compareTo(b2.getPlacetime());
                if(compareTime != 0){
                    return compareTime;
                }else {
                    return 0;
                }

            }
        });


        if(projectbids != null && projectbids.size()>0 && projectbids.get(0) != null){
            Integer bidder = projectbids.get(0).getBidder().getId();
            Double prPrice = projectbids.get(0).getPrice();
            projectRepository.updateMaxBid(prPrice, projectId , bidder);
        }

        //send kafka refresh events to sync bids with min bidder
        return ResponseEntity.ok().build();
    }


}
