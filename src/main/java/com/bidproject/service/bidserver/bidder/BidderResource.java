package com.bidproject.service.bidserver.bidder;

import com.bidproject.service.bidserver.bid.Bid;
import com.bidproject.service.bidserver.bid.BidRepository;
import com.bidproject.service.bidserver.project.Project;
import com.bidproject.service.bidserver.project.ProjectRepository;
import com.bidproject.service.bidserver.seller.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

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

    @PostMapping("/bidder/{bidderId}/project/{projectId}/bid")
    public ResponseEntity<Object> placeBid(@PathVariable int bidderId, @PathVariable int projectId, @Valid @RequestBody Bid bid) throws NotFoundException {
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
        bid.setBidder(bidder);
        bid.setProject(project);
        bidRepository.save(bid);

        int rows = projectRepository.updateMaxBid(bid.getPrice(), projectId, bidderId);
        System.out.println(rows);

        //gets /sellers from the above uri and appends /{id} to it after which replaces the id template with stored seller id
        URI location = ServletUriComponentsBuilder.fromPath("/bid/{id}").buildAndExpand(bid.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


}
