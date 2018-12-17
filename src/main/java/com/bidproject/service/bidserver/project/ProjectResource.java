package com.bidproject.service.bidserver.project;

import com.bidproject.service.bidserver.bid.Bid;
import com.bidproject.service.bidserver.bid.BidRepository;
import com.bidproject.service.bidserver.bidder.BidderRepository;
import com.bidproject.service.bidserver.seller.NotFoundException;
import com.bidproject.service.bidserver.seller.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProjectResource {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BidRepository bidRepository;

    @GetMapping("/project/{id}")
    public Project getProject(@PathVariable int id) throws NotFoundException {
        Optional<Project> project =  projectRepository.findById(id);
        if(!project.isPresent()){
            throw new NotFoundException("Project "+ id + " not found");
        }
        return project.get();
    }

    @GetMapping("/projects")
    public List<Project> retrieveAllProjects(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value= "size", required=false) Integer size) {

        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        if(page == null || size == null){
            return projectRepository.findAll(sort);
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Project> thisPage = projectRepository.findAll(pageRequest);
        return thisPage.getContent();
    }

    @GetMapping("/project/{id}/bids")
    public List<Bid> getBids(@PathVariable int id) throws NotFoundException {
        Optional<Project> project =  projectRepository.findById(id);
        if(!project.isPresent()){
            throw new NotFoundException("Seller "+ id + " not found");
        }
        return project.get().getBids();
    }

    @GetMapping("/project/{id}/bidder/{bidderId}/bids")
    public List<Bid> getBidsForProjectOfBidder(@PathVariable int id, @PathVariable int bidderId) throws NotFoundException {
        return bidRepository.findByBidderIdAndProjectId(bidderId, id);
    }
}
